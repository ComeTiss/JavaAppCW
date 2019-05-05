package com.example.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class PopUpCreateTraining extends DialogFragment {
    /*** This class provides:

     * generates PopUp window with 2 buttons
     * purpose: ask user to choose between creating a meal or a training
     * It will redirect to a 'create page' (either meal/training)

     */


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Add a new Training on this day?")

                .setPositiveButton(R.string.positive_popup, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateTrainingFragment()).commit();
                    }
                })
                .setNegativeButton(R.string.negative_popup, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PopUpCreateTraining.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}