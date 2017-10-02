/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.android.quakereport.R.id.progressBar;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private EarthquakeWordAdapter adapter;
    private ListView earthquakeListView;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static List<Earthquake> earthquakeList;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        textView = (TextView) findViewById(R.id.empty);
        // Create a fake list of earthquake locations.
        //earthquakes.add(new Earthquake(7.2f, "San Francisco", "Feb 2 ,2016"));
        // earthquakes.add(new Earthquake(6.1f, "London", "July 20 ,2015"));
        //  earthquakes.add(new Earthquake(3.9f, "Tokyo", "Nov 10 ,2014"));
        //   earthquakes.add(new Earthquake(5.4f, "Mexico City", "May 3 ,2014"));
        //   earthquakes.add(new Earthquake(2.8f, "Moscow", "Jan 31 ,2013"));
        //   earthquakes.add(new Earthquake(4.9f, "Rio de Janeiro", "Aug 19 ,2012"));
        //  earthquakes.add(new Earthquake(1.6f, "Paris", "Oct 30 ,2011"));
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetowrk = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetowrk != null && activeNetowrk.isConnectedOrConnecting();

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeWordAdapter(this, new ArrayList<Earthquake>());
        //earthquakeListView.setEmptyView(findViewById(R.id.empty));

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent url = new Intent(Intent.ACTION_VIEW);
                Earthquake earthquake = earthquakeList.get(i);
                url.setData(Uri.parse(earthquake.getUrl()));
                startActivity(url);
            }
        });
        if (isConnected==true){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1, null, this);
        Log.e("loaderManager", "InitLoader");}
        else{
            textView.setText("No Internet Connectivity");
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(GONE);

        }
    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e("onCreateLoader", "createLoader");
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            earthquakeList = data;
        } else {
            earthquakeListView.setEmptyView(textView);
            textView.setText("No Earthquakes Found");
            Log.e("onLoadFinished", "LoadFinished");

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        adapter.clear();
        adapter.addAll(new ArrayList<Earthquake>());
        Log.e("onLoaderReset", "LoaderReset");

    }

}
