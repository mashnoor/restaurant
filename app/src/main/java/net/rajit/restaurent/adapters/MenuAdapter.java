package net.rajit.restaurent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.rajit.restaurent.R;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.models.MenuItem;

/**
 * Created by Nowfel Mashnoor on 10/8/2017.
 */

public class MenuAdapter extends BaseAdapter {
    private final Context context;
    private final MenuItem[] menus;

    // 1
    public MenuAdapter(Context context, MenuItem[] menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus.length;
    }

    @Override
    public MenuItem getItem(int i) {
        return menus[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            v = layoutInflater.inflate(R.layout.grid_row_menu, null);
        }
        TextView menuName = v.findViewById(R.id.menuName);
        TextView menuPrice = v.findViewById(R.id.menuPrice);
        ImageView menuImage = v.findViewById(R.id.menuImage);



        MenuItem curr_menu = getItem(i);
        Glide.with(context).load(curr_menu.getImage()).into(menuImage);

        menuName.setText(curr_menu.getName());
        menuPrice.setText(curr_menu.getPrice());


        return v;
    }
}
