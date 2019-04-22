package com.example.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


public class PopUpCreateItemSelectType extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("What would you like to create ?")

                .setPositiveButton("Meal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateMealFragment()).commit();
                    }
                })
                .setNegativeButton("Training", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateTrainingFragment()).commit();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}