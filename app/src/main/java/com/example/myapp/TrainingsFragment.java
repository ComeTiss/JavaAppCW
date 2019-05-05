package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


public class TrainingsFragment extends Fragment {

    /*** This class provides:
     * Displays 2 tabs at top of the layout: Favourites & Category
     * Allow user to switch from one to the other by clicking on the tab
     * Each tab has its own content.

     ***/

    // Widgets variables
    FrameLayout CategoryFrameLayout, FavouritesFrameLayout;
    View viewCategory, viewFavourites;
    TextView categoryTextView, favouritesTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout & identify widgets
        View v = inflater.inflate(R.layout.fragment_trainings, container, false);
        identifyWidgets(v);
        // Shows Category tab by default
        SwitchFragment(new TrainingsCategory());
        categoryTextView.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
        // Handles clicks on tabs
        CategoryFrameLayout.setOnClickListener(clik);
        FavouritesFrameLayout.setOnClickListener(clik);

        return v;
    }

    // On click Listener
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                // Load Category tab
                case R.id.training_categ:
                    SwitchFragment(new TrainingsCategory());
                    SwitchView(viewFavourites, viewCategory);
                    break;
                // Load Favourites tab
                case R.id.training_fav:
                    SwitchFragment(new TrainingsFavourites());
                    SwitchView(viewCategory, viewFavourites);
                    break;
            }
        }
    };

    private void identifyWidgets(View v){
        CategoryFrameLayout = v.findViewById(R.id.training_categ);
        FavouritesFrameLayout = v.findViewById(R.id.training_fav);
        viewCategory = v.findViewById(R.id.viewCategory);
        viewFavourites = v.findViewById(R.id.viewFavourites);
        categoryTextView = v.findViewById(R.id.TrainingsCateg_TextView);
        favouritesTextView = v.findViewById(R.id.TrainingsFav_TextView);
    }

    // Switch to new fragment
    private void SwitchFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerpage, fragment).commit();
    }

    // When change tab, hide old view and show new view
    private void SwitchView (View ViewToHide, View ViewToShow) {
        ViewToHide.setVisibility(View.INVISIBLE);
        ViewToShow.setVisibility(View.VISIBLE);
    }
}
