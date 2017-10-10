package net.rajit.restaurent.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.OrderAdapter;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.utils.Datas;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.ordersListLV)
    ListView ordersList;

    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        adapter = new OrderAdapter(this, Datas.getOrders(this));
        ordersList.setAdapter(adapter);

    }
    public void goContinue(View v)
    {
        finish();
        startActivity(new Intent(this, CategoryActivity.class));
    }
    public void goCancel(View v)
    {
        Datas.clearOrders(this);
        finish();
        startActivity(new Intent(this, WelcomeActivity.class));
    }
}
