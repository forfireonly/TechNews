package com.example.annascott.technews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = NewsActivity.class.getName();

    /** URL for news data from Guardian dataset */
    private static final String Guard_REQUEST_URL =
            "https://content.guardianapis.com/search?q=%22tech%20news%22&api-key=f3a1cedd-032a-45ab-b1d4-d7ba62274a9c";
    private NewsAdapter mAdapter;
    private static final int NEWS_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    RelativeLayout emptyRelativeLayout;
    RelativeLayout noInternetConnection;

    ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find a reference to the {@link ListView} in the layout
        newsListView = (ListView) findViewById(R.id.list);

        emptyRelativeLayout =(RelativeLayout) findViewById(R.id.no_news);
        noInternetConnection =(RelativeLayout) findViewById(R.id.no_internet);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current earthquake that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getMurl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
        if (isNetworkConnectionAvailable() == true) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            newsListView.setEmptyView(noInternetConnection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, Guard_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> collectionNews) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        if (isNetworkConnectionAvailable() == false){
            newsListView.setEmptyView(noInternetConnection);
        }
        else{
            newsListView.setEmptyView(emptyRelativeLayout);}

        // Clear the adapter of previous data
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (isNetworkConnectionAvailable() == true){
            noInternetConnection.setVisibility(View.INVISIBLE);
        if (collectionNews != null && !collectionNews.isEmpty()) {
            noInternetConnection.setVisibility(View.INVISIBLE);
        }
            noInternetConnection.setVisibility(View.INVISIBLE);
            mAdapter.addAll(collectionNews);
            Log.v("Loader finished", "yes");
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.v("Loader reset", "yes");
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            //checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
}}
