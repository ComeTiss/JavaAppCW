package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    /*** This class provides:

     * Adapter for Categories:
            - retrieve user's categories
            - put them in ListView widget situated in 'tab_training_categories'
            - Each Category appears in a layout 'category_listview_details'

     ***/


    private LayoutInflater inflater;
    private final ArrayList<Category> categories;
    private final Context context;

    /* Constructor */
    CategoryAdapter(Context c, ArrayList<Category> categories) {
        this.context =c;
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = inflater.inflate(R.layout.category_listview_details, null);
        ImageButton deleteCategBtn = (ImageButton) v.findViewById(R.id.deleteTrainingCategory_button);
        TextView nameTextView = (TextView) v.findViewById(R.id.TrainingCategoryTitle_titletext);

        // Display category title
        final Category category = this.categories.get(position);
        nameTextView.setText(category.get_name());

        // Delete Category button
        deleteCategBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(v.getContext());
                db.deleteOneCategory(category.get_id());
                Toast.makeText(context, "Category Deleted!", Toast.LENGTH_SHORT).show();
                ListView CategListView = (ListView) parent.findViewById(R.id.TrainingCategories_ListView);
                CategoryAdapter categoryAdapter = new CategoryAdapter(parent.getContext(), db.getAllCategoriesByType("1"));
                CategListView.setAdapter(categoryAdapter);
            }
        });

        return v;
    }
}
