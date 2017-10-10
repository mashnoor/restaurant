package net.rajit.restaurent.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.view.BootstrapTextView;

import net.rajit.restaurent.R;
import net.rajit.restaurent.activities.Home;
import net.rajit.restaurent.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class OrderAdapter extends ArrayAdapter<Order> {

    private Activity activity;

    public OrderAdapter(Activity activity, List<Order> orders) {
        super(activity, 0, orders);
        this.activity = activity;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = activity.getLayoutInflater().inflate(R.layout.order_row, parent, false);
        }

        TextView foodName = (TextView) v.findViewById(R.id.txtfood);
        TextView orderInfo = (TextView) v.findViewById(R.id.txtOrderCosting);
        BootstrapButton btn = (BootstrapButton) v.findViewById(R.id.btnDel);
        final Order currentOrder = getItem(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.orders.remove(currentOrder);
                Home.adapter.notifyDataSetChanged();
            }
        });



        foodName.setText(currentOrder.getMenuItem().getName());
        double cost = Double.parseDouble(currentOrder.getMenuItem().getPrice()) * Double.parseDouble(currentOrder.getQuantity());
        orderInfo.setText(currentOrder.getQuantity() + " x " + currentOrder.getMenuItem().getPrice() + " TK = " + cost);


        return v;
    }
}
