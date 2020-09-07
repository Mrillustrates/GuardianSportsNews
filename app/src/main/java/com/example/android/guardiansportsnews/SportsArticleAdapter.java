package com.example.android.guardiansportsnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SportsArticleAdapter extends ArrayAdapter<SportsArticle> {

    public SportsArticleAdapter(Context context, ArrayList<SportsArticle> sportsArticles) {
        super(context, 0, sportsArticles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.sportsnews_item, parent, false);

        }


        SportsArticle currentSportsArticleItem = getItem(position);


        TextView defaultArticleTitleView = (TextView) listItemView.findViewById(R.id.news_article_title);
        defaultArticleTitleView.setText(currentSportsArticleItem.getArticleTitle());

        return listItemView;
    }

}