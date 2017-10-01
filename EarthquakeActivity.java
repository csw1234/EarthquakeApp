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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;

public class EarthquakeActivity extends AppCompatActivity {
    private  EarthquakeWordAdapter adapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static List<Earthquake> earthquakeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        //earthquakes.add(new Earthquake(7.2f, "San Francisco", "Feb 2 ,2016"));
       // earthquakes.add(new Earthquake(6.1f, "London", "July 20 ,2015"));
      //  earthquakes.add(new Earthquake(3.9f, "Tokyo", "Nov 10 ,2014"));
     //   earthquakes.add(new Earthquake(5.4f, "Mexico City", "May 3 ,2014"));
     //   earthquakes.add(new Earthquake(2.8f, "Moscow", "Jan 31 ,2013"));
     //   earthquakes.add(new Earthquake(4.9f, "Rio de Janeiro", "Aug 19 ,2012"));
      //  earthquakes.add(new Earthquake(1.6f, "Paris", "Oct 30 ,2011"));
        EarthquakeAsyncTask earthquakeAsync= new EarthquakeAsyncTask();
        earthquakeAsync.execute(USGS_REQUEST_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeWordAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent url= new Intent(Intent.ACTION_VIEW);
                Earthquake earthquake = earthquakeList.get(i);
                url.setData(Uri.parse(earthquake.getUrl()));
                startActivity(url);
            }
        });

    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length <= 0 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            List<Earthquake> earthquaket = QueryUtils.extractEarthquakes(urls[0]);
            return earthquaket;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            super.onPostExecute(earthquakes);
            adapter.clear();
            if (earthquakes != null && !earthquakes.isEmpty()){
                adapter.addAll(earthquakes);
                earthquakeList=earthquakes;
            }
        }
    }

}
