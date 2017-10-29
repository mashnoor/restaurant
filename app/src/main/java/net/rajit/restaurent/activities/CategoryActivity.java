package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.CategoryAdapter;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Geson;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.categoryGridView)
    GridView categoryGridView;

    CategoryAdapter adapter;
    AsyncHttpClient client;
    Category[] categories;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Categories...");
        //Generate Dummy Category
        client = new AsyncHttpClient();
        getCategories();


    }

    private void getCategories() {
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        client.get(URLS.ALL_CATEGORIES, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                dialog.dismiss();
                String response = new String(responseBody);

                try {
                    JSONObject motherObj = new JSONObject(response);
                    Log.d("--------", motherObj.getJSONObject("data").getJSONArray("categories").toString());
                    categories = Geson.g().fromJson(motherObj.getJSONObject("data").getJSONArray("categories").toString(), Category[].class);
                    adapter = new CategoryAdapter(getApplicationContext(), categories);
                    categoryGridView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Category selectedCategory = categories[i];
                        Intent intent = new Intent(CategoryActivity.this, MenuActivity.class);
                        intent.putExtra("category_id", selectedCategory.getCategoryId());

                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();

            }
        });
    }

    public void goOrders(View v) {
        startActivity(new Intent(this, OrdersActivity.class));

    }

}
