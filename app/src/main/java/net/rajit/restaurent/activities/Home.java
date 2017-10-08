package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.OrderAdapter;
import net.rajit.restaurent.models.Order;
import net.rajit.restaurent.models.Table;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Geson;
import net.rajit.restaurent.utils.URLS;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class Home extends AppCompatActivity {


    @BindView(R.id.ordersList)
    ListView ordersList;


    @BindView(R.id.spnrTable)
    Spinner tableSpinner;


    public static ArrayList<Order> orders;

    AsyncHttpClient client;
    public static OrderAdapter adapter;
    ProgressDialog dialog;


    private boolean isMenuInlist(String menuid) {
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            Logger.d(o.getMenu_id());
            if (o.getMenuItem().getCode().equals(menuid)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Datas.setAuthorizationKey(this, "null");
            Datas.setWaiterName(this, "null");
            startActivity(new Intent(Home.this, Signin.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        setTitle("Welcome, " + Datas.getWaiterName(this));
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        orders = new ArrayList<>();
        adapter = new OrderAdapter(this, orders);
        ordersList.setAdapter(adapter);
        client = new AsyncHttpClient();
        client.addHeader("authorization", "Bearer " + Datas.getAuthorizationKey(this));
        getTables();
    }

    /***
     public void addToOrder() {
     if (!Netcheker.isNetworkAvailable(this)) {
     showSnackbar("No internet connection!", Type.ERROR);
     return;
     }

     String menuid = txtMenuId.getText().toString().trim();
     final String quantity = txtQuantity.getText().toString().trim();
     if (TextUtils.isEmpty(menuid)) {
     txtMenuId.setError("Menu ID can't be blank!");
     return;
     }
     if (TextUtils.isEmpty(quantity)) {
     txtQuantity.setError("Quantity can't be blank!");
     return;
     }
     if (isMenuInlist(menuid)) {
     showSnackbar("Menu already exists!", Type.ERROR);
     return;
     }


     client.get(URLS.getMenuUrl(menuid), new AsyncHttpResponseHandler() {
    @Override public void onStart() {
    dialog.setMessage("Getting menu from server...");
    dialog.show();
    }

    @Override public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
    Logger.d(new String(responseBody));
    try {
    JSONObject obj = new JSONObject(new String(responseBody));
    int status = obj.getInt("status");
    if (status == 0) {
    showSnackbar(obj.getString("msg"), Type.ERROR);
    dialog.dismiss();
    return;
    }
    String menu_json = obj.getJSONObject("data").getString("item");

    net.rajit.restaurent.models.MenuItem menuItem = Geson.g().fromJson(menu_json, net.rajit.restaurent.models.MenuItem.class);
    Order order = new Order(menuItem.getMenu_id(), quantity, menuItem);

    orders.add(order);
    adapter.notifyDataSetChanged();
    dialog.dismiss();

    showSnackbar("Order added successfully", Type.SUCCESS);
    txtMenuId.setText("");
    txtQuantity.setText("");
    } catch (Exception e) {
    e.printStackTrace();
    }


    }

    @Override public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    Logger.d(new String(responseBody));
    dialog.dismiss();
    }
    });
     }
     ***/

    private void getTables() {
        client.get(this, URLS.GET_ALL_TABLES, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Table[] tables;
                tables = Geson.g().fromJson(response, Table[].class);
                List<String> tablesList = new ArrayList<String>();
                for (int i = 0; i < tables.length; i++) {
                    tablesList.add(tables[i].getCode());
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Home.this,
                        android.R.layout.simple_list_item_1,
                        tablesList
                );
                tableSpinner.setAdapter(spinnerAdapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



    private void getAndSetCategoriesSpinner(Spinner spnrCategories) {
        client.get(Home.this, URLS.ALL_CATEGORIES, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Table[] tables;
                tables = Geson.g().fromJson(response, Table[].class);
                List<String> tablesList = new ArrayList<String>();
                for (int i = 0; i < tables.length; i++) {
                    tablesList.add(tables[i].getCode());
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(Home.this,
                        android.R.layout.simple_list_item_1,
                        tablesList
                );
                tableSpinner.setAdapter(spinnerAdapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showToast(String msg)
    {
        Toast.makeText(Home.this, msg, Toast.LENGTH_LONG).show();
    }
    /***

     @OnClick(R.id.btnConfirm) public void confirmOrder() {
     if (orders.size() == 0) {
     showSnackbar("Add some orders!", Type.ERROR);
     return;
     }
     new AlertDialog.Builder(this)
     .setTitle("Submit Order(s)")
     .setMessage("Are you sure?")
     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
     @Override public void onClick(DialogInterface dialog, int which) {
     submitTheOrders();

     }
     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
     @Override public void onClick(DialogInterface dialog, int which) {
     dialog.dismiss();
     }
     }).show();
     //Prepare Client

     }
     ***/

    /***
     private void submitTheOrders() {
     if (!Netcheker.isNetworkAvailable(this)) {
     showSnackbar("No internet connection!", Type.ERROR);
     return;
     }
     String tablecode = txtTable.getText().toString();
     if (TextUtils.isEmpty(tablecode)) {
     txtTable.setError("Table code can't be empty!");
     return;
     }
     RequestParams params = new RequestParams();
     params.put("table_code", tablecode);

     for (int i = 0; i < orders.size(); i++) {
     Order currOrder = orders.get(i);
     params.put("items[" + i + "][menu_id]", currOrder.getMenu_id());
     params.put("items[" + i + "][quantity]", currOrder.getQuantity());

     }
     client.post(URLS.ORDER_URL, params, new AsyncHttpResponseHandler() {
    @Override public void onStart() {
    dialog.setMessage("Submitting order...");
    dialog.show();
    }

    @Override public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
    Logger.d(new String(responseBody));
    String order_id = "-1";
    try {
    JSONObject obj = new JSONObject(new String(responseBody));
    order_id = obj.getJSONObject("data").getString("order_id");

    } catch (Exception e) {

    e.printStackTrace();
    }

    dialog.dismiss();
    Intent i = new Intent(Home.this, ConfirmActivity.class);
    i.putExtra("order_id", order_id);
    orders.clear();
    adapter.notifyDataSetChanged();
    txtTable.setText("");
    startActivity(i);


    }

    @Override public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    Logger.d(new String(responseBody));
    dialog.dismiss();
    }
    });
     }
     ***/


}
