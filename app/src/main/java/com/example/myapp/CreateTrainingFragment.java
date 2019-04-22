package com.example.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CreateTrainingFragment extends Fragment {
    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout
        final View view = inflater.inflate(R.layout.fragment_create_training, container, false);
        Button createBtn = (Button) view.findViewById(R.id.CreateTraining_button);
        final CheckBox FavoriteCheckBox = (CheckBox) view.findViewById(R.id.TrainingFavorite_checkBox);

        /* Retrieve categories from database */
        DBHandler db = new DBHandler(getContext());
        ArrayList<Category> categories = db.getAllCategoriesByType("Training");
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (Category cat : categories) {
            categoriesNames.add(cat.get_name());
        }

        /* Add categories to Dropdown list */
        final Spinner spinner = (Spinner) view.findViewById(R.id.CreateTraining_categorySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoriesNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Identify layout text widgets
                EditText titleEditText = (EditText) view.findViewById(R.id.trainingTitle_editText);
                EditText timeEditText = (EditText) view.findViewById(R.id.trainingTime_editText);

                // Retrieve input data & create Training instance
                String title = titleEditText.getText().toString();
                String category = spinner.getSelectedItem().toString();
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
