package com.example.annascott.technews;

public class News {

    private String mArticleName;
    private String mArticleAuthor;
    private String mNewsDate;
    private String mCategory;
    private String murl;

    public News( String articleName, String articleAuthor, String newsDate, String category, String url){
        mArticleName = articleName;
        mArticleAuthor =articleAuthor;
        mNewsDate = newsDate;
        mCategory = category;

        murl = url;

    }

    public String getmArticleName() {
        return mArticleName;
    }

    public String getmArticleAuthor() {
        return mArticleAuthor;
    }

    public String getmNewsDate() { return mNewsDate; }

    public String getMurl() {
        return murl;
    }

    public String getmCategory() {
        return mCategory;
    }
}

