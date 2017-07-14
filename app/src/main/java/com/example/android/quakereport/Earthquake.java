package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Lun on 29/06/2017.
 */

public class Earthquake {

    private double magnitude;
    private String location;
    private long date;

    public Earthquake(double magnitude, String location, long date){
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;

    }

    public double getMagnitude(){
        return magnitude;
    }

    public String getLocation(){
        return location;
    }

    public long getDate(){
        return date;
    }
}
