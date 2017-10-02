package com.example.android.quakereport;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by alonz on 01/10/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>>{
    private String[] mUrl;
    public EarthquakeLoader(Context context, String...url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e("onStartLoading", "StartLoading-EarthquakeLoader");

    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e("loadInBackground", "loadInBackground-EarthquakeLoader");
        if (mUrl.length <= 0 || mUrl[0] == null) {
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        List<Earthquake> earthquaket = QueryUtils.extractEarthquakes(mUrl[0]);

        return earthquaket;

    }
}
