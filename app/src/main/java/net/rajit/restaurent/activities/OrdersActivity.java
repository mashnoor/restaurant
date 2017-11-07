package net.rajit.restaurent.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.OrderAdapter;
import net.rajit.restaurent.models.Category;
import net.rajit.restaurent.models.MenuItem;
import net.rajit.restaurent.models.Order;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.ordersListLV)
    ListView ordersList;

    ProgressDialog dialog;


    public static OrderAdapter adapter;
    public static ArrayList<Order> allOrders;
    AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        client = new AsyncHttpClient();
        allOrders = Datas.getOrders(this);

        adapter = new OrderAdapter(this, allOrders);
        ordersList.setAdapter(adapter);
        ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final Order selectedOrder = allOrders.get(position);

                AlertDialog.Builder addQuantity = new AlertDialog.Builder(
                        OrdersActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                final View workingview = inflater.inflate(
                        R.layout.add_qty, null);

                addQuantity.setView(workingview);
                final EditText quantity = workingview.findViewById(R.id.etQty);
                quantity.requestFocus();
                quantity.setText(allOrders.get(position).getQuantity().replace(".00", ""));
                quantity.setSelection(quantity.getText().length());



                addQuantity.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(OrdersActivity.this, "Updated", Toast.LENGTH_LONG).show();
                        selectedOrder.setQuantity(quantity.getText().toString().trim());
                        Datas.deleteParticularOrder(OrdersActivity.this, position);

                        Datas.addToOrders(OrdersActivity.this, selectedOrder);
                        allOrders = Datas.getOrders(OrdersActivity.this);
                        adapter.notifyDataSetChanged();

                        dialogInterface.dismiss();
                    }
                });
                addQuantity.show();
            }
        });

    }

    public void goContinue(View v) {
        finish();
        startActivity(new Intent(this, CategoryActivity.class));
    }

    public void goCancel(View v) {
        Datas.clearOrders(this);
        Datas.deleteEditOrderId(this);
        Datas.setIsEdit(this, false);
        finish();
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    public void confirmOrder(View v) {
        if(Datas.isEdit(OrdersActivity.this))
        {
            editOrder();
            return;
        }
        Log.d("========+++++", Datas.getTableCode(OrdersActivity.this));
        ArrayList<Order> orders = Datas.getOrders(OrdersActivity.this);
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        RequestParams params = new RequestParams();
        params.put("table_code", Datas.getTableCode(OrdersActivity.this));

        for (int i = 0; i < orders.size(); i++) {
            Order currOrder = orders.get(i);
            params.put("items[" + i + "][menu_id]", currOrder.getMenu_id());
            params.put("items[" + i + "][quantity]", currOrder.getQuantity());

        }
        client.post(URLS.ORDER_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.setMessage("Submitting order...");
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Logger.d(new String(responseBody));
                String order_id = "-1";
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    order_id = obj.getJSONObject("data").getString("order_id");

                } catch (Exception e) {

                    e.printStackTrace();
                }

                dialog.dismiss();
                Intent i = new Intent(OrdersActivity.this, ConfirmActivity.class);
                i.putExtra("order_id", order_id);
                Datas.clearOrders(OrdersActivity.this);

                startActivity(i);
                finish();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Logger.d(new String(responseBody));
                dialog.dismiss();
            }
        });
    }

    private void editOrder()
    {
        Log.d("========+++++", Datas.getTableCode(OrdersActivity.this));
        ArrayList<Order> orders = Datas.getOrders(OrdersActivity.this);
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        RequestParams params = new RequestParams();
        params.put("table_code", Datas.getTableCode(OrdersActivity.this));

        for (int i = 0; i < orders.size(); i++) {
            Order currOrder = orders.get(i);
            params.put("items[" + i + "][menu_id]", currOrder.getMenu_id());
            params.put("items[" + i + "][quantity]", currOrder.getQuantity());

        }
        client.put(URLS.ORDER_URL + "/" + Datas.getEditOrderId(OrdersActivity.this), params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.setMessage("Submitting Edited order...");
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Logger.d(new String(responseBody));
                String order_id = "-1";
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    order_id = obj.getJSONObject("data").getString("order_id");

                } catch (Exception e) {

                    e.printStackTrace();
                }

                dialog.dismiss();
                Intent i = new Intent(OrdersActivity.this, ConfirmActivity.class);
                i.putExtra("order_id", order_id);
                Datas.clearOrders(OrdersActivity.this);
                Datas.deleteEditOrderId(OrdersActivity.this);
                Datas.setIsEdit(OrdersActivity.this, false);

                startActivity(i);
                finish();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Logger.d(new String(responseBody));
                dialog.dismiss();
            }
        });
    }

}
