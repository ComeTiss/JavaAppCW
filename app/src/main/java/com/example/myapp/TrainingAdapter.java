package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainingAdapter extends BaseAdapter {
    /*** This class provides:

     * Adapter for Training:
            - retrieve user's training
            - put them in ListView widget situated in home_fragment
            - Each training appears in a layout 'training_listview_details'

     ***/

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

        // identify layout widgets
        TextView titleTextView = (TextView) v.findViewById(R.id.Title_textView);
        TextView categoryTextView = (TextView) v.findViewById(R.id.CategoryTextView);
        TextView timeTextView = (TextView) v.findViewById(R.id.TimeTextView);

        // add training content (title, category, time)
        titleTextView.setText(this.trainings.get(position).get_title());
        categoryTextView.setText(this.trainings.get(position).get_category());
        timeTextView.setText(this.trainings.get(position).get_time());

        return v;
    }
}
