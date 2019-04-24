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

public class TrainingDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout & identify widgets
        View v = inflater.inflate(R.layout.fragment_training_details, container, false);
        Button deleteBtn = (Button) v.findViewById(R.id.TrainingDetails_deteleBtn);
        TextView TitleTextView = (TextView) v.findViewById(R.id.TrainingDetails_Title_TextView);
        TextView CategTextViex = (TextView) v.findViewById(R.id.TrainingDetails_Category_TextView);
        TextView TimeTextView = (TextView) v.findViewById(R.id.TrainingDetails_TimeValue_TextView);

        // Retrieve training from Database
        Bundle args = getArguments();
        int index = args.getInt("index", 0);
        final DBHandler db = new DBHandler(getContext());
        final Training training = db.getAllTrainings().get(index);

        // Puts Training info in Fragment
        TitleTextView.setText(training.get_title());
        CategTextViex.setText(training.get_category());
        TimeTextView.setText(training.get_time());

        // When click 'delete" button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete training from database
                db.deleteOneTraining(training.get_id());

                // Switch to Home fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });
        return v;
    }
}

