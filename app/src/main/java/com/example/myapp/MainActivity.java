package com.example.myapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    // USER DATA
    ArrayList<Training> trainings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Add Bottom navigation menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Set default view to 'Home'
        // Load user data
        Fragment initialFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, initialFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_meals:
                            selectedFragment = new MealsFragment();
                            break;
                        case R.id.nav_trainings:
                            selectedFragment = new TrainingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
    };


    /******** USER DATA HANDLING **********/


}

