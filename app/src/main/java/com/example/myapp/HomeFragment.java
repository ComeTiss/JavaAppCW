package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Training> trainings = new ArrayList<>();
    ArrayList<Meal> meals = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Brings up layout & identify widgets */
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ListView dataListView =(ListView) v.findViewById(R.id.HomeListView);
        FloatingActionButton CreateItem_FaBtn = (FloatingActionButton) v.findViewById(R.id.Add_floatingActionButton);

        // Loading user data from database
        DBHandler db = new DBHandler(getContext());
        this.trainings = db.getAllTrainings();
        if (trainings.size() == 0) {
            setText(v, "Nothing planned yet");
        }
        // display user's trainings
        final TrainingAdapter trainingAdapter = new TrainingAdapter(getActivity(), trainings);
        dataListView.setAdapter(trainingAdapter);

        // Change to training fragment if click on it
        dataListView.setClickable(true);
        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment trainingFragment = new TrainingDetailsFragment();
                Bundle args = new Bundle();
                args.putInt ("index", position);
                trainingFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, trainingFragment).commit();
            }
        });

        // Button 'Add' a training
        CreateItem_FaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpCreateItemSelectType popup = new PopUpCreateItemSelectType();
                popup.show(getFragmentManager(), "CreateItemFragment");
            }
        });

        return v;
    }
    public void setText (View v, String text) {
        TextView EmptydayMsgText = (TextView) v.findViewById(R.id.EmptyDayMsgText);
        EmptydayMsgText.setText(text);
    }

    public void addMeal (Meal m) {
        this.meals.add(m);
    }
}
