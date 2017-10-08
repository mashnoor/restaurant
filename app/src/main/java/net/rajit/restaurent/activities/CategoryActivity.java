package net.rajit.restaurent.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.CategoryAdapter;
import net.rajit.restaurent.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.categoryGridView)
    GridView categoryGridView;

    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        //Generate Dummy Category
        Category cat1 = new Category("1", "0", "Burger");
        Category cat2 = new Category("1", "0", "Beverage");
        Category cat3 = new Category("1", "0", "Icecream");
        Category cat4 = new Category("1", "0", "Soup");
        Category[] cats = {cat1, cat2, cat3, cat4};

        adapter = new CategoryAdapter(getApplicationContext(), cats);
        categoryGridView.setAdapter(adapter);
        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(CategoryActivity.this, MenuActivity.class));
            }
        });


    }

}
