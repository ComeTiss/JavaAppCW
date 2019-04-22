package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateMealFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up Layout
        final View view = inflater.inflate(R.layout.fragment_create_meal, container, false);
        Button createBtn = (Button) view.findViewById(R.id.CreateMeal_button);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Identify layout text widgets
                EditText titleEditText = (EditText) view.findViewById(R.id.mealTitle_editText);
                EditText categoryEditText = (EditText) view.findViewById(R.id.mealCategory_editText);
                EditText timeEditText = (EditText) view.findViewById(R.id.mealTime_editText);

                // Retrieve input data & create Training instance
                String title = titleEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String time = timeEditText.getText().toString();
                final Meal meal = new Meal(title, category, time);

                // Send new meal to data storage
                // Switch to Home fragment
                Fragment homeFrag = new HomeFragment();
                ((HomeFragment) homeFrag).addMeal(meal);// Later: add to database instead
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFrag).commit();
            }
        });




        return view;
    }
}
