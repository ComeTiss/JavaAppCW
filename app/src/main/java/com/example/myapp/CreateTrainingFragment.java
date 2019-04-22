package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreateTrainingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout
        final View view = inflater.inflate(R.layout.fragment_create_training, container, false);
        Button createBtn = (Button) view.findViewById(R.id.CreateTraining_button);
        final CheckBox FavoriteCheckBox = (CheckBox) view.findViewById(R.id.TrainingFavorite_checkBox);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Identify layout text widgets
                EditText titleEditText = (EditText) view.findViewById(R.id.trainingTitle_editText);
                EditText categoryEditText = (EditText) view.findViewById(R.id.trainingCategory_editText);
                EditText timeEditText = (EditText) view.findViewById(R.id.trainingTime_editText);

                // Retrieve input data & create Training instance
                String title = titleEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String time = timeEditText.getText().toString();
                Boolean isFavorite = false;
                if (FavoriteCheckBox.isChecked()) {
                    System.out.println("Added to Favorites!");
                    isFavorite = true;
                }
                // Send new training to data storage
                DBHandler db = new DBHandler(getContext());
                db.addNewTraining(new Training(title, category, time, isFavorite));
                // Switch to Home fragment
                Fragment homeFrag = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFrag).commit();
            }
        });

        return view;
    }
}
