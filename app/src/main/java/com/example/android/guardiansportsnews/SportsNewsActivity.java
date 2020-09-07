package com.example.android.guardiansportsnews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class SportsNewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<SportsArticle>> {

    /** Tag for log messages */
    private static final String LOG_TAG =  SportsArticleLoader.class.getName();

    private static final int SPORTSARTICLE_LOADER_ID = 1;

    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=nba AND playoffs&api-key=test";

    private TextView emptyTextView;
    private ProgressBar loadingProgressView;
    private TextView noConnectionTextView;
    private SportsArticleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news);

        //Code to set up the adapters, empty textview if no data, loading spinner, and no connection view.
        ListView sportsArticleListView = (ListView) findViewById(R.id.list);

        adapter = new SportsArticleAdapter(this, new ArrayList<SportsArticle>());

        sportsArticleListView.setAdapter(adapter);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        sportsArticleListView.setEmptyView(emptyTextView);

        //Creating a loading view while loading data
        loadingProgressView = (ProgressBar) findViewById(R.id.loading_spinner);

        noConnectionTextView = (TextView) findViewById(R.id.no_internet_textview);
        sportsArticleListView.setEmptyView(noConnectionTextView);

        if(isNetworkConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(SPORTSARTICLE_LOADER_ID, null, this);

        }
        else{
            loadingProgressView.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);

        }

        sportsArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SportsArticle currentSportsArticle = adapter.getItem(position);

                Uri sportsArticleUri = Uri.parse(currentSportsArticle.getArticleURL());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, sportsArticleUri);

                startActivity(webIntent)
                ;
            }
        });

    }

    @Override
    public Loader<List<SportsArticle>> onCreateLoader(int id, Bundle bundle) {
        return new SportsArticleLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<SportsArticle>> loader, List<SportsArticle> data){
        loadingProgressView.setVisibility(View.GONE);

        adapter.clear();

        if(data != null && !data.isEmpty()){
            adapter.addAll(data);

        }
        else {
            emptyTextView.setText("No articles");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<SportsArticle>> loader){
        adapter.clear();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() !=null && cm.getActiveNetworkInfo().isConnected();
}
    }