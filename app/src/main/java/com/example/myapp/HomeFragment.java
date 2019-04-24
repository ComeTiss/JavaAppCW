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
    /*** This class provides:

     * creates an instance HomeFragment using layout 'fragment_home'
     * Shows a list of the user's trainings
     * Options:
        - click 'Add' to create a new training/meal
        - click on training to see more about it

     ***/


    private ArrayList<Training> trainings = new ArrayList<>();

    // layout widgets variables
    private TextView EmptyDayMsgText;
    private ListView dataListView;
    private FloatingActionButton CreateItem_FaBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Brings up layout & identify widgets */
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        identifyWidgets(v);

        // Loading user data from database
        loadTrainings(v);

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
                PopUpCreateItemSelectType popup = new PopUpCreateItemSelectType();
                popup.show(getFragmentManager(), "CreateItemFragment");
            }
        });

        return v;
    }

    private void identifyWidgets (View v) {
        dataListView = (ListView) v.findViewById(R.id.HomeListView);
        CreateItem_FaBtn = (FloatingActionButton) v.findViewById(R.id.Add_floatingActionButton);
        EmptyDayMsgText = (TextView) v.findViewById(R.id.EmptyDayMsgText);
    }

    private void loadTrainings (View v) {
        DBHandler db = new DBHandler(getContext());
        this.trainings = db.getAllTrainings();
        if (trainings.size() == 0) {
            EmptyDayMsgText.setText("Nothing planned yet");
        }
    }

    private AdapterView.OnItemClickListener AdapterViewListener () {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment trainingFragment = new TrainingDetailsFragment();
                Bundle args = new Bundle();
                args.putInt ("index", position);
                trainingFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, trainingFragment).commit();
            }
        };
    }

}
