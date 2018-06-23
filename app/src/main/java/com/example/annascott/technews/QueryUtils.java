package com.example.annascott.technews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {

    }

    /**
     * Query the Guardian dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> collectionNews = extractFeatureFromJson(jsonResponse);
        Log.v("fetch method", "yes");

        // Return the list of {@link News}s
        return collectionNews;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJson(String newsJSON) {
        String title;
        String author;
        String date;
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;

        }

        List<News> collectionNews = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            //SONArray newsArray = baseJsonResponse.getJSONArray("results");

            Log.v("jsonarray", "newsArray");

            JSONObject baseJsonResponseResult = baseJsonResponse.getJSONObject("response");
            // Creat a json array object from "result"
            JSONArray currentNewsArray = baseJsonResponseResult.getJSONArray("results");

            // For each news in the newsArray, create an {@link News} object
            for (int i = 0; i < currentNewsArray.length(); i++) {
                JSONObject currentNews = currentNewsArray.getJSONObject(i);

                // Extract the value for the key called "webTitle"
                String titleandauthor = currentNews.getString("webTitle");

                Log.v("title and author", titleandauthor);

                String [] detailTitleAuthor = new String[2];
                if (titleandauthor.contains("|")) {
                    detailTitleAuthor = titleandauthor.split("\\|");
                    title = detailTitleAuthor[0];
                     author = detailTitleAuthor[1];
                }
                else {  title = titleandauthor;
                 author = "";}

                // Extract the value for the key called "webPublicationDate"
                String rawdate = currentNews.getString("webPublicationDate");
                Log.v("rawdate", rawdate);

                String [] dateTime = new String[2];
                dateTime = rawdate.split("T");
                date = dateTime[0];

                // Extract the value for the key called "pillarName"
                String category = currentNews.getString("pillarName");
                Log.v("category", category);
                // Extract the value for the key called "webUrl"
                String url = currentNews.getString("webUrl");
                Log.v("url", url);

                // Create a new {@link News} object
                // and url from the JSON response.
                News news = new News(title, author, date, category, url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                collectionNews.add(news);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of earthquakes
        return collectionNews;
    }

}
