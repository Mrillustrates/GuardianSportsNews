package com.example.android.guardiansportsnews;

public class SportsArticle {

    private String articleTitle;
    private String articleURL;


    public SportsArticle(String queryArticle, String queryURL){
        this.articleTitle = queryArticle;
        this.articleURL = queryURL;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }
}
