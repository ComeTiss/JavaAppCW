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
import java.util.Date;

public class HomeFragment extends Fragment {
    /*** This class provides:

     * creates an instance HomeFragment using layout 'fragment_home'
     * Shows a list of the user's trainings
     * Options:
        - click 'Add' to create a new training/meal
        - click on training to see more about it

     ***/

    private ArrayList<Training> trainings = new ArrayList<>();

    // layout widgets variables
    private TextView dateTextView, EmptyDayMsgText;
    private ListView dataListView;
    private FloatingActionButton CreateItem_FaBtn, Calendar_FaBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Brings up layout & identify widgets */
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        identifyWidgets(v);

        // get home page day
        String date = ((MainActivity) getActivity()).getHomeDate();
        dateTextView.setText(date);

        // Loading user data from database
        loadTrainings(date);

        // display user's trainings
        final TrainingAdapter trainingAdapter = new TrainingAdapter(getActivity(), trainings);
        dataListView.setAdapter(trainingAdapter);

        // Change to training fragment if click on it
        dataListView.setClickable(true);
        dataListView.setOnItemClickListener(AdapterViewListener());

        // Brings up Popup when click 'add' button
        CreateItem_FaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpCreateTraining popup = new PopUpCreateTraining();
                popup.show(getFragmentManager(), "CreateItemFragment");
            }
        });

        Calendar_FaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchFragment(new CalendarFragment());
            }
        });

        return v;
    }

    private void identifyWidgets (View v) {
        dateTextView = (TextView) v.findViewById(R.id.homeDay_TitleView);
        dataListView = (ListView) v.findViewById(R.id.HomeListView);
        EmptyDayMsgText = (TextView) v.findViewById(R.id.EmptyDayMsgText);
        CreateItem_FaBtn = (FloatingActionButton) v.findViewById(R.id.Add_floatingActionButton);
        Calendar_FaBtn = (FloatingActionButton) v.findViewById(R.id.Calendar_floatingActionButton);
    }

    private void loadTrainings (String date) {
        DBHandler db = new DBHandler(getContext());
        ArrayList<Training> results = db.getAllTrainings();
        for (Training t : results) {
            String trainingDate = t.get_date().format(new Date());
            if (trainingDate.equals(date)) {
                this.trainings.add(t);
            }
        }
        if (trainings.size() == 0) {
            EmptyDayMsgText.setText("Nothing planned yet");
        }
    }
    // Displays Training page when click on it
    private AdapterView.OnItemClickListener AdapterViewListener () {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int trainingId = trainings.get(position).get_id();
                Fragment trainingFragment = new TrainingDetailsFragment();
                Bundle args = new Bundle();
                args.putInt ("index", trainingId);
                trainingFragment.setArguments(args);
                SwitchFragment(trainingFragment);
            }
        };
    }

    // Switch to new fragment
    private void SwitchFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}
