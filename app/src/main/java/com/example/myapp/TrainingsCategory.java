package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TrainingsCategory extends Fragment {

    private ArrayList<Category> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Initialize layout */
        View v = inflater.inflate(R.layout.tab_training_categories, container, false);

        /* Load data from database */
        DBHandler db = new DBHandler(getContext());
        this.categories = db.getAllCategoriesByType("Training");

        /* Set List View adapter */
        ListView categListView = (ListView) v.findViewById(R.id.TrainingCategories_ListView);
        CategoryAdapter categAdapter = new CategoryAdapter(getActivity(), this.categories);
        categListView.setAdapter(categAdapter);

        /* Create a Category */
        FloatingActionButton AddCateg_faBtn = (FloatingActionButton) v.findViewById(R.id.AddTrainingCategory_floatingActionButton);
        AddCateg_faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpCreateCategory createCategoryWindow = new PopUpCreateCategory();
                createCategoryWindow.show(getFragmentManager(), "CreateCategoryPopUp");
            }
        });

        return v;
    }
}
