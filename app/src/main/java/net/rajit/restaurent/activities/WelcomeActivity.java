package net.rajit.restaurent.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.rajit.restaurent.R;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.models.Table;
import net.rajit.restaurent.utils.Datas;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(Datas.isAnyPendingOrder(this))
        {
            finish();
            startActivity(new Intent(this, OrdersActivity.class));
        }
    }

    public void goPlaceOrder(View v)
    {
        startActivity(new Intent(this, TableActivity.class));
    }
}
