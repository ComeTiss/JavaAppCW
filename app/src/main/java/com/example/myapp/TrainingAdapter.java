package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainingAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Training> trainings = new ArrayList<>();

    /* Constructor */
    TrainingAdapter(Context c, ArrayList<Training> trainings) {
        this.trainings = trainings;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /* Get Methods */
    @Override
    public int getCount() {
        return trainings.size();
    }

    @Override
    public Object getItem(int position) {
        return trainings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.trainings_listview_details, null);

        TextView titleTextView = (TextView) v.findViewById(R.id.color);
        TextView categoryTextView = (TextView) v.findViewById(R.id.CategoryTextView);
        TextView timeTextView = (TextView) v.findViewById(R.id.TimeTextView);

        titleTextView.setText(this.trainings.get(position).get_title());
        categoryTextView.setText(this.trainings.get(position).get_category());
        timeTextView.setText(this.trainings.get(position).get_time());

        return v;
    }
}
