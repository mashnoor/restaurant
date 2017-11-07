package net.rajit.restaurent.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;

import net.rajit.restaurent.R;
import net.rajit.restaurent.activities.OrdersActivity;
import net.rajit.restaurent.models.Order;
import net.rajit.restaurent.utils.Datas;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = activity.getLayoutInflater().inflate(R.layout.order_row, parent, false);
        }

        TextView foodName = v.findViewById(R.id.txtfood);
        TextView orderInfo = v.findViewById(R.id.txtOrderCosting);
        BootstrapButton btn = v.findViewById(R.id.btnDel);
        CircleImageView foodimage = v.findViewById(R.id.foodImage);
        final Order currentOrder = getItem(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersActivity.allOrders.remove(currentOrder);
                Datas.deleteParticularOrder(activity, position);
                OrdersActivity.adapter.notifyDataSetChanged();
            }
        });
        Glide.with(activity).load(currentOrder.getMenuItem().getImage()).into(foodimage);



        foodName.setText(currentOrder.getMenuItem().getName());
        double cost = Double.parseDouble(currentOrder.getMenuItem().getPrice()) * Double.parseDouble(currentOrder.getQuantity());
        orderInfo.setText(currentOrder.getQuantity() + " x " + currentOrder.getMenuItem().getPrice() + " TK = " + cost);


        return v;
    }
}
