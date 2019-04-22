package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Category> categories = new ArrayList<>();

    /* Constructor */
    CategoryAdapter(Context c, ArrayList<Category> categories) {
        this.categories = categories;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /* Get Methods */
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.category_listview_details, null);
        final Category category = this.categories.get(position);

        TextView nameTextView = (TextView) v.findViewById(R.id.TrainingCategoryTitle_titletext);
        nameTextView.setText(category.get_name());

        // Delete Category
        ImageButton deleteCategBtn = (ImageButton) v.findViewById(R.id.deleteTrainingCategory_button);
        deleteCategBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(v.getContext());
                db.deleteOneCategory(category.get_id());
            }
        });

        return v;
    }
}
