package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by alonz on 25/09/2017.
 */

public class EarthquakeWordAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeWordAdapter(Context context, ArrayList<Earthquake> words){
        super(context,0, words);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewList = convertView;
        if (convertView == null){
            viewList = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_temp,parent,false);
        }

        Earthquake currentWord =getItem(position);

        TextView textViewMag = (TextView)viewList.findViewById(R.id.mag);
        textViewMag.setText(Float.toString(currentWord.getMag()));

        TextView textViewLoc = (TextView)viewList.findViewById(R.id.location);
        TextView textViewDis = (TextView)viewList.findViewById(R.id.distance);

        //Set the proper background color on the magnitude circle.
        //Fetch the background from the TextView, which is a GradientDrawable
        GradientDrawable manitudeCircle = (GradientDrawable) textViewMag.getBackground();
        int magnitudeColor = getMagnitudeColor((int)currentWord.getMag());

        //Set the color on the magnitude circle
        manitudeCircle.setColor(magnitudeColor);


        String locSplit = currentWord.getLocation().toString();
        if (locSplit.contains("of")) {
            String[] output = locSplit.split("of", 2);
            textViewLoc.setText(output[1]);
            textViewDis.setText(output[0]+"of");
        }else {
            textViewLoc.setText(locSplit);
            textViewDis.setText("Near the");
        }

        TextView textViewDate = (TextView)viewList.findViewById(R.id.date);
        TextView textViewTime = (TextView)viewList.findViewById(R.id.time);
        Date dataObject = new Date(currentWord.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        String date = dateFormat.format(dataObject);
        textViewDate.setText(date);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String time = timeFormat.format(dataObject);
        textViewTime.setText(time);


        return viewList;
    }

    private int getMagnitudeColor(int mag){
        int color=0;
        switch (mag){
            case 0:
            case 1:
                color= R.color.magnitude0_2;
                break;
            case 2:
                color= R.color.magnitude2_3;
                break;
            case 3:
                color= R.color.magnitude3_4;
                break;
            case 4:
                color= R.color.magnitude4_5;
                break;
            case 5:
                color= R.color.magnitude5_6;
                break;
            case 6:
                color= R.color.magnitude6_7;
                break;
            case 7:
                color= R.color.magnitude7_8;
                break;
            case 8:
                color= R.color.magnitude8_9;
                break;
            case 9:
                color= R.color.magnitude9_10;
                break;
            default:
                color= R.color.magnitude10;
                break;
        }


        return ContextCompat.getColor(getContext(),color);
    }

}
