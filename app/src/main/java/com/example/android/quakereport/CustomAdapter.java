package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android.quakereport.R.id.date;

/**
 * Created by Lun on 29/06/2017.
 */

public class CustomAdapter extends ArrayAdapter<Earthquake> {

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
        magnitude.setText(Double.toString(earthquake.getMagnitude()));

        TextView location = (TextView) listview.findViewById(R.id.location);
        location.setText(earthquake.getLocation());

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
        return timeFormat.format(date);
    }
}
