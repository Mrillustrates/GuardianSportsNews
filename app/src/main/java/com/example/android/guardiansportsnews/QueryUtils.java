package com.example.android.guardiansportsnews;

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

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    /**
     *  Create  URL object first with null value to protect from nullpointer exception then create
     *  a new URL that takes String input.
     */

    public static List<SportsArticle> fetchArticleData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<SportsArticle> sportArticle = extractFeatureFromJson(jsonResponse);

        return sportArticle;
    }

    public static URL createURL(String stringURL){
        URL url = null;
        try {
            url = new URL(stringURL);
        }
        catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating URL", exception);
        }
        return url;
    }

    /**
     * Make HttpRequest based on documentation to the API
     */

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == urlConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            }
            else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                // newly added /
                return null;
            }


        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results", e);


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
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


    private static List<SportsArticle> extractFeatureFromJson(String articleJSON){
        if(TextUtils.isEmpty(articleJSON)){
            return null;
        }
        List<SportsArticle> sportsArticles = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONArray articleArray = baseJsonResponse.getJSONObject("response").getJSONArray("results");

            for(int i = 0; i < articleArray.length(); i++){
                JSONObject currentArticle = articleArray.getJSONObject(i);

                String articleTitle = currentArticle.getString("webTitle");
                String articleURL = currentArticle.getString("webURL");

                SportsArticle addedArticle = new SportsArticle(articleTitle, articleURL);
                sportsArticles.add(addedArticle);
            }


    } catch (JSONException e) {
        Log.e(LOG_TAG, "Problem parsing the book results", e);
    }
    //Return the list of earthquakes
        return sportsArticles;

}

        }






