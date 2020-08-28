package com.example.android.guardiansportsnews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class SportsArticleLoader extends AsyncTaskLoader<List<SportsArticle>>{


    /** Query URL */
    private String mUrl;

    public SportsArticleLoader(Context context, String url){
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<SportsArticle> loadInBackground(){


        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<SportsArticle> result = QueryUtils.fetchArticleData(mUrl);
        return result;
    }
}
