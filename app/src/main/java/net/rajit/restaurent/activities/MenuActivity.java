package net.rajit.restaurent.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.CategoryAdapter;
import net.rajit.restaurent.adapters.MenuAdapter;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.models.MenuItem;
import net.rajit.restaurent.models.Order;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Geson;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity {


    @BindView(R.id.menuGridView)
    GridView menuGrid;

    MenuAdapter adapter;

    AsyncHttpClient client;
    MenuItem[] menuItems;
    ProgressDialog dialog;

    private void showToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Menus...");
        client = new AsyncHttpClient();
        getMenus();




    }

    private void getMenus()
    {
        final String category_id = getIntent().getStringExtra("category_id");
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        Log.d("=====++++++", URLS.getMenuByCategoryUrl(category_id));
        client.get(URLS.getMenuByCategoryUrl(category_id), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();


                String response = new String(responseBody);
                Log.d("---------", response);
                Log.d("-----category_id" , category_id);

                try {
                    JSONObject motherObj = new JSONObject(response);
                    Log.d("--------", motherObj.getJSONObject("data").getJSONArray("items").toString());
                    menuItems = Geson.g().fromJson(motherObj.getJSONObject("data").getJSONArray("items").toString(), MenuItem[].class);
                    adapter = new MenuAdapter(getApplicationContext(), menuItems);
                    menuGrid.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter = new MenuAdapter(getApplicationContext(), menuItems);
                menuGrid.setAdapter(adapter);
                menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int itemIndex, long l) {
                        AlertDialog.Builder addQuantity = new AlertDialog.Builder(
                                MenuActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

                        final View workingview = inflater.inflate(
                                R.layout.add_qty, null);
                        final EditText quantity = workingview.findViewById(R.id.etQty);
                        addQuantity.setView(workingview);
                        addQuantity.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showToast("Added to list");
                                MenuItem selectedMenu = menuItems[itemIndex];
                                Order order = new Order(selectedMenu.getMenu_id(), quantity.getText().toString() , selectedMenu);
                                Datas.addToOrders(MenuActivity.this, order);
                                dialogInterface.dismiss();
                                startActivity(new Intent(MenuActivity.this, CategoryActivity.class));
                                finish();
                            }
                        });
                        addQuantity.show();
                    }
                });







            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();

            }
        });
    }

}
