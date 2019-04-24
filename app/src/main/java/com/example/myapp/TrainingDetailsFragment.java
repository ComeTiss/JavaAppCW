package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingDetailsFragment extends Fragment {
    /*** This class provides:

     * Fragment shows up containing all details about a training (title, category, time, description)
     * appears when click on a training from the listView in 'fragment_home'
     * 2 buttons available: either delete or update the training

     ***/

    private Button deleteBtn;
    private Button updateBtn;
    private TextView TitleTextView;
    private TextView CategTextView;
    private TextView TimeTextView;
    private TextView DescriptionTextView;

    private DBHandler db;
    private Training training;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout & identify widgets
        View v = inflater.inflate(R.layout.fragment_training_details, container, false);
        identifyWidgets(v);
        // Retrieve training from Database
        loadTraining();
        // Shows training info on the fragment
        diplayTraining();

        // When click 'update' button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, newTrainingFragment()).commit();
            }
        });
        // When click 'delete' button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete training from database
                db.deleteOneTraining(training.get_id());
                Toast.makeText(getContext(), "Training Deleted!", Toast.LENGTH_SHORT).show();
                // Switch to Home fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });
        return v;
    }

    private void identifyWidgets (View v) {
        deleteBtn = (Button) v.findViewById(R.id.TrainingDetails_deteleBtn);
        updateBtn = (Button) v.findViewById(R.id.TrainingDetails_updateBtn);
        TitleTextView = (TextView) v.findViewById(R.id.TrainingDetails_Title_TextView);
        CategTextView = (TextView) v.findViewById(R.id.TrainingDetails_Category_TextView);
        TimeTextView = (TextView) v.findViewById(R.id.TrainingDetails_TimeValue_TextView);
        DescriptionTextView = (TextView) v.findViewById(R.id.TrainingDetails_DescriptionContent_TextView);
    }

    private void loadTraining () {
        Bundle args = getArguments();
        int index = args.getInt("index", 0);
        db = new DBHandler(getContext());
        training = db.getAllTrainings().get(index);
    }

    private void diplayTraining () {
        TitleTextView.setText(training.get_title());
        CategTextView.setText(training.get_category());
        TimeTextView.setText(training.get_time());
        DescriptionTextView.setText(training.get_description());
    }

    private Fragment newTrainingFragment () {
        Fragment UpdateFragment = new UpdateTrainingFragment();
        Bundle args = new Bundle();
        args.putInt("id", training.get_id());
        UpdateFragment.setArguments(args);
        return UpdateFragment;
    }
}

