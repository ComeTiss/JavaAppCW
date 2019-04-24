package com.example.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PopUpCreateCategory extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_create_categ, null))
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editTextCategory = (EditText) getDialog().findViewById(R.id.newCategory_editText);
                        String name = editTextCategory.getText().toString();

                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "Couldn't create Category", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        saveCategory(name);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PopUpCreateCategory.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void saveCategory (String name) {
        // If correct input, add category to database
        DBHandler db = new DBHandler(getContext());
        db.addNewCategory(new Category(name, "Training"));

        // refresh category list
        ListView categoryListView = (ListView) getActivity().findViewById(R.id.TrainingCategories_ListView);
        CategoryAdapter categAdapter = new CategoryAdapter(getActivity(), db.getAllCategoriesByType("Training"));
        categoryListView .setAdapter(categAdapter);
        Toast.makeText(getContext(), "Category added!", Toast.LENGTH_SHORT).show();
    }
}
