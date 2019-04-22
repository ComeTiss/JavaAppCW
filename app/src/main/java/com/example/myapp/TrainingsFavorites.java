package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TrainingsFavorites extends Fragment {

    ArrayList<Training> trainings = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DBHandler db = new DBHandler(getContext());
        this.trainings = db.getFavoritesTrainings();

        View v = inflater.inflate(R.layout.tab_training_favorites, container, false);
        ListView trainingsListView = (ListView) v.findViewById(R.id.FavoritesTraining_ListView);
        TrainingAdapter trainingAdapter = new TrainingAdapter(getActivity(), this.trainings);
        trainingsListView.setAdapter(trainingAdapter);

        return v;
    }
}
