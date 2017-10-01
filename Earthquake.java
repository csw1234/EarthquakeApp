package com.example.android.quakereport;

/**
 * Created by alonz on 25/09/2017.
 */


public class Earthquake {

    private float mMag;
    private String mlocation;
    private long mLdate;
    private String mUrl;

    public Earthquake(float mag, String location, long date, String url) {
        mMag=mag;
        mLdate=date;
        mlocation=location;
        mUrl=url;
    }

    public float getMag(){
        return mMag;
    }
    public String getLocation(){
        return mlocation;
    }
    public long getDate(){
        return mLdate;
    }
    public String getUrl(){return mUrl;}

}
