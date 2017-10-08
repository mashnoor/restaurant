package net.rajit.restaurent.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.rajit.restaurent.R;

public class TableActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
    }
    public void goCategory(View v)
    {
        startActivity(new Intent(this, CategoryActivity.class));
    }
}
