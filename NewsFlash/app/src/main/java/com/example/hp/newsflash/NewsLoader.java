package com.example.hp.newsflash;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {


    /** Query URL */

    private String mURL;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param Url to load data from
     */

    public NewsLoader(Context context, String Url) {
        super(context);
        this.mURL = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mURL == null) {
            return null;
        }
// Perform the network request, parse the response, and extract a list of TOP News.
        List<News> newsList = QueryUtils.fetchNewsData(mURL);
        return newsList;
    }
}