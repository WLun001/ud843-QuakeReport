package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lun on 29/06/2017.
 */

public class CustomAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public CustomAdapter(Activity context, ArrayList<Earthquake> earthquake) {
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listview = convertView;
        if(listview == null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_layout, parent, false);
        }
        Earthquake earthquake = getItem(position);

        TextView magnitude = (TextView) listview.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        magnitude.setText(formatMagnitude(earthquake.getMagnitude()));

        String offsetLoc = "", primaryLoc = "";

        if(earthquake.getLocation().contains(LOCATION_SEPARATOR)){
            String[] parts = earthquake.getLocation().split(LOCATION_SEPARATOR);
            offsetLoc = parts[0] + LOCATION_SEPARATOR;
            primaryLoc = parts[1];
        }
        else {
            offsetLoc = getContext().getResources().getString(R.string.near_the) ;
            primaryLoc = earthquake.getLocation();
        }

        TextView offsetLocation = (TextView) listview.findViewById(R.id.offset_location);
        offsetLocation.setText(offsetLoc);

        TextView primaryLocation = (TextView) listview.findViewById(R.id.primary_location);
        primaryLocation.setText(primaryLoc);

        Date date = new Date(earthquake.getDate());
        TextView textDate = (TextView) listview.findViewById(R.id.date);
        textDate.setText(formatDate(date));

        TextView textTime = (TextView) listview.findViewById(R.id.time);
        textTime.setText(formatTime(date));

        return listview;
    }

    public String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        return dateFormat.format(date);
    }

    public String formatTime(Date time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(time);
    }

    public String formatMagnitude(double mag){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mag);
    }

    public int getMagnitudeColor(double mag){
        int resourceId;
        int magnitude = (int) Math.floor(mag);
        switch (magnitude){
            case 0:
            case 1: resourceId = R.color.magnitude1;
                break;
            case 2: resourceId = R.color.magnitude2;
                break;
            case 3: resourceId = R.color.magnitude3;
                break;
            case 4: resourceId = R.color.magnitude4;
                break;
            case 5: resourceId = R.color.magnitude5;
                break;
            case 6: resourceId = R.color.magnitude6;
                break;
            case 7: resourceId = R.color.magnitude7;
                break;
            case 8: resourceId = R.color.magnitude8;
                break;
            case 9: resourceId = R.color.magnitude9;
                break;
            default: resourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), resourceId);
    }

}
