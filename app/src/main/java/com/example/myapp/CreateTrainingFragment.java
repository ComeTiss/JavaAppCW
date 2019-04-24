package com.example.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CreateTrainingFragment extends Fragment {

    private ArrayList<String> categoriesNames = new ArrayList<>();
    private String defaultCategoryName = "Select a Category";

        @SuppressLint("NewApi")
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            // Brings up layout & identify widgets
            final View view = inflater.inflate(R.layout.fragment_create_training, container, false);
            final Button createBtn = (Button) view.findViewById(R.id.CreateTraining_button);
            final CheckBox FavoriteCheckBox = (CheckBox) view.findViewById(R.id.TrainingFavorite_checkBox);
            final EditText titleEditText = (EditText) view.findViewById(R.id.trainingTitle_editText);
            final EditText timeEditText = (EditText) view.findViewById(R.id.trainingTime_editText);
            final Spinner spinner = (Spinner) view.findViewById(R.id.CreateTraining_categorySpinner);

            // Retrieve categories from database
            loadData();

            /* Add categories to Dropdown list */
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoriesNames);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            createBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Retrieve input data
                    String title = titleEditText.getText().toString();
                    String category = spinner.getSelectedItem().toString();
                    String time = timeEditText.getText().toString();
                    Boolean isFavorite = false;
                    if (FavoriteCheckBox.isChecked()) {
                        isFavorite = true;
                    }

                    // check all fields are complete
                    if (! isInputValid(title, category, time)) {
                        return;
                    }

                    // Create Training instance & send new training to data storage
                    Training newTraining = new Training(title, category, time, isFavorite);
                    saveTraining(newTraining);
                }
            });

            return view;
    }

    private void loadData () {
        DBHandler db = new DBHandler(getContext());
        ArrayList<Category> categories = db.getAllCategoriesByType("Training");
        categoriesNames.add(defaultCategoryName);
        for (Category cat : categories) {
            categoriesNames.add(cat.get_name());
        }
    }

    private Boolean isInputValid (String title, String category, String time) {
        if (title.isEmpty() || time.isEmpty()) {
            Toast.makeText(getContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (category.equals(defaultCategoryName)) {
            Toast.makeText(getContext(), "Select a Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(getContext(), "Training created!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void saveTraining (Training t) {
        DBHandler db = new DBHandler(getContext());
        db.addNewTraining(t);
        // Switch to Home fragment
        Fragment homeFrag = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFrag).commit();
    }
}
