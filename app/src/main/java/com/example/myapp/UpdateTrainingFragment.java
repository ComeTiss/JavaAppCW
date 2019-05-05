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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateTrainingFragment extends Fragment {

    private ArrayList<String> categoriesNames = new ArrayList<>();
    private final String defaultCategoryName = "Select a Category";
    private DBHandler db;
    private int trainingId;
    private Training initialTr;

    private TextView FragmentTitle, descriptionEditText;
    private Button createBtn;
    private EditText titleEditText, timeEditText;
    private Spinner spinner;
    private CheckBox FavoriteCheckBox;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            // Brings up layout & identify widgets
            final View view = inflater.inflate(R.layout.fragment_create_training, container, false);
            identifyWidgets(view);

            /* Retrieve training data */
            this.db = new DBHandler(getContext());
            this.trainingId = getArguments().getInt("id");
            loadInitialTraining();

            /* Retrieve categories from database & add to dropdown list */
            loadCategories();
            setDropdown();

            createBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Retrieve input data
                    Training updatedTraining = newTraining();
                    // check all fields are complete
                    if (! isInputValid(updatedTraining)) {
                        return;
                    }
                    //  Add training to database & switch to home fragment
                    saveTraining(updatedTraining);
                }
            });
            return view;
    }

    private void identifyWidgets (View view) {
        FragmentTitle = (TextView) view.findViewById(R.id.InputTraining_TitleTextView);
        FragmentTitle.setText("Update Training");
        createBtn = (Button) view.findViewById(R.id.CreateTraining_button);
        titleEditText = (EditText) view.findViewById(R.id.trainingTitle_editText);
        timeEditText = (EditText) view.findViewById(R.id.trainingTime_editText);
        spinner = (Spinner) view.findViewById(R.id.CreateTraining_categorySpinner);
        FavoriteCheckBox = (CheckBox) view.findViewById(R.id.TrainingFavorite_checkBox);
        descriptionEditText = (TextView) view.findViewById(R.id.TrainingDescription_editText);
    }

    private void loadInitialTraining () {
        /*
            Get initial training data from database
            Initialize fields content with it
        */
        initialTr = db.getOneTraining(trainingId);
        titleEditText.setText(initialTr.get_title());
        timeEditText.setText(initialTr.get_time());
        descriptionEditText.setText(initialTr.get_description());
        FavoriteCheckBox.setChecked(initialTr.get_isFavorites());
    }

    private void loadCategories () {
        /*
            Load Category objects from database
            Create a new ArrayList containing only the category name
         */
        DBHandler db = new DBHandler(getContext());
        ArrayList<Category> categories = db.getAllCategoriesByType("1");
        categoriesNames.add(defaultCategoryName);
        for (Category cat : categories) {
            categoriesNames.add(cat.get_name());
        }
    }

    private void setDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private Training newTraining () {
        /*
            called once user clicked 'Submit' button
            Retrieve input fields
            Create & return a new Training object
         */
        String title = titleEditText.getText().toString();
        String category = spinner.getSelectedItem().toString();
        String time = timeEditText.getText().toString();
        Boolean isFavorite = FavoriteCheckBox.isChecked();
        String description = descriptionEditText.getText().toString();
        SimpleDateFormat date = initialTr.get_date();

        Training newTraining = new Training(title, category, time, isFavorite, description, date);
        return  newTraining;
    }

    private Boolean isInputValid (Training t) {
        /*
            Checks input fields aren't empty
            Checks a Category has been selected
            If all conditions respected, displays a Toast popup at bottom of screen
         */
        if (t.get_title().isEmpty() || t.get_time().isEmpty() || t.get_description().isEmpty()) {
            displayNotification("Complete all fields");
            return false;
        }
        if (t.get_category().equals(defaultCategoryName)) {
            displayNotification("Select a Category");
            return false;
        }
        displayNotification("Training Created!");
        return true;
    }

    private void saveTraining (Training t) {
        /*
            Sends updated training to database
            Switch to Home fragment
         */
        db.updateOneTraining(trainingId, t);
        // Switch to Home fragment
        Fragment homeFrag = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFrag).commit();
    }

    private void displayNotification (String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
