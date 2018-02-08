package net.rajit.restaurent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.rajit.restaurent.R;
import net.rajit.restaurent.models.PreviousOrder;

import org.w3c.dom.Text;

/**
 * Created by Nowfel Mashnoor on 10/31/2017.
 */

public class PreviousOrdersAdapter extends BaseAdapter {
    private final Context context;
    private final PreviousOrder[] previousOrders;

    public PreviousOrdersAdapter(Context context, PreviousOrder[] previousOrders) {
        this.context = context;
        this.previousOrders = previousOrders;
    }

    @Override
    public int getCount() {
        return previousOrders.length;
    }

    @Override
    public PreviousOrder getItem(int i) {
        return previousOrders[i];
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
            v = layoutInflater.inflate(R.layout.previous_order_row, null);
        }
        PreviousOrder currOrder = getItem(i);
        TextView tvOrderId = v.findViewById(R.id.tvOrderId);
        TextView tvTableCode = v.findViewById(R.id.tvTableCode);
        TextView tvStatus = v.findViewById(R.id.tvStatus);
        TextView tvTime = v.findViewById(R.id.tvTime);

        tvTime.setText(currOrder.getDate());
        tvOrderId.setText("ID:" + currOrder.getId());
        tvTableCode.setText("Table: " + currOrder.getTable().getCode());

        tvStatus.setText(currOrder.getStatus());
        tvStatus.setTextColor(currOrder.getColor());

        return v;
    }
}
