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
    FrameLayout seller, buyer;
    View view1, view2;
    TextView tvseller, tvbuyer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_three = inflater.inflate(R.layout.fragment_trainings, container, false);
        //INIT VIEWS
        init(fragment_three);
        //SET TABS ONCLICK
        seller.setOnClickListener(clik);
        buyer.setOnClickListener(clik);
        //LOAD PAGE FOR FIRST
        loadPage(new TrainingsCategory());
        tvseller.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
        return fragment_three;
    }
    public void init(View v){
        seller = v.findViewById(R.id.training_categ);
        buyer = v.findViewById(R.id.training_fav);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        tvseller = v.findViewById(R.id.tvTrainingsCateg);
        tvbuyer = v.findViewById(R.id.tvTrainingsFav);
    }
    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.training_categ:
                    //ONSELLER CLICK
                    //LOAD SELLER FRAGMENT CLASS
                    loadPage(new TrainingsCategory());
                    //WHEN CLICK TEXT COLOR CHANGED
                    tvseller.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
                    tvbuyer.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    break;
                case R.id.training_fav:
                    //ONBUYER CLICK
                    //LOAD BUYER FRAGMENT CLASS
                    loadPage(new TrainingsFavorites());

                    //WHEN CLICK TEXT COLOR CHANGED
                    tvseller.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
                    tvbuyer.setTextColor(getActivity().getResources().getColor(R.color.design_default_color_primary_dark));
                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
