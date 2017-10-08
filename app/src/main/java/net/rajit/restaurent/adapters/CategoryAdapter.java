package net.rajit.restaurent.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.rajit.restaurent.R;
import net.rajit.restaurent.models.Category;

/**
 * Created by Nowfel Mashnoor on 10/8/2017.
 */

public class CategoryAdapter extends BaseAdapter {
    private final Context context;
    private final Category[] categories;

    // 1
    public CategoryAdapter(Context context, Category[] categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Category getItem(int i) {
        return categories[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null)
        {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            v = layoutInflater.inflate(R.layout.grid_row_category, null);
        }
        Category curr_category = getItem(i);
        ImageView categoryImage = v.findViewById(R.id.categoryPicture);
        TextView categoryName = v.findViewById(R.id.categoryName);
        categoryName.setText(curr_category.getCategoryName());
        return v;
    }
}
