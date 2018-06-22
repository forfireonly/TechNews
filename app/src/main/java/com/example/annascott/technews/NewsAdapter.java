package com.example.annascott.technews;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

public NewsAdapter(Activity context, ArrayList<News> collectionNews) {
        super(context, 0, collectionNews);}

@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
        listItemView = LayoutInflater.from(getContext()).inflate(
        R.layout.news_line, parent, false);
        }

        News currentNews = (News) getItem(position);

        TextView categoryView = (TextView) listItemView.findViewById(R.id.category);
        String category = currentNews.getmCategory();
        categoryView.setText(category);

        TextView titleView = (TextView) listItemView.findViewById(R.id.article);
        String article = currentNews.getmArticleName();
        titleView.setText(article);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        String author = currentNews.getmArticleAuthor();
        authorView.setText(author);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String date = currentNews.getmNewsDate();
        dateView.setText(date);

        return listItemView;

        }
        }

