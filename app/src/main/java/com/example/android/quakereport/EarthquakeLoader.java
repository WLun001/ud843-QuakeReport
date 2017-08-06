package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Lun on 06/08/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String urls;

    public EarthquakeLoader(Context context, String urls) {
        super(context);
        this.urls = urls;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (urls == null)
            return null;
        return QueryUtils.fetchDate(urls);
    }
}
