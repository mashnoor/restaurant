package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.PreviousOrdersAdapter;
import net.rajit.restaurent.models.MenuItem;
import net.rajit.restaurent.models.Order;
import net.rajit.restaurent.models.PreviousOrder;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Geson;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PreviousOrders extends AppCompatActivity {

    AsyncHttpClient client;
    ProgressDialog dialog;
    PreviousOrder[] previousOrders;
    PreviousOrdersAdapter adapter;

    @BindView(R.id.lvPreviousOrders)
    ListView previousOrderslv;

    @BindView(R.id.layoutSwipe)
    SwipeRefreshLayout layoutSwipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("Getting data from server...");
        getOrders();
        registerSwipeListener();
        previousOrderslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!previousOrders[i].isEditable()) {
                    Toast.makeText(PreviousOrders.this, "This order is not editable!", Toast.LENGTH_LONG).show();
                    return;
                }
                getOrderDetails(previousOrders[i].getId());
                Datas.setEditOrderId(PreviousOrders.this, previousOrders[i].getId());
            }


        });
    }

    private void registerSwipeListener() {
        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrders();
                layoutSwipe.setRefreshing(false);
            }
        });
    }

    private void getOrderDetails(String orderId) {
        client.get(URLS.getOrderDetailUrl(orderId), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                String response = new String(responseBody);
                Datas.clearOrders(PreviousOrders.this);

                try {
                    JSONObject motherObj = new JSONObject(response);
                    JSONArray ordersArray = motherObj.getJSONObject("data").getJSONArray("orderlines");
                    String tableCode = motherObj.getJSONObject("data").getJSONObject("summary").getString("table_code");
                    Datas.setTableCode(PreviousOrders.this, tableCode);

                    for (int i = 0; i < ordersArray.length(); i++) {
                        JSONObject obj = ordersArray.getJSONObject(i);
                        int menuId = obj.getInt("menu_id");
                        String code = obj.getString("code");
                        String quantity = obj.getString("quantity");
                        String price = obj.getString("price");
                        String image = obj.getString("image");
                        String name = obj.getString("name");
                        MenuItem item = new MenuItem(menuId, code, name, "black", price, image, 1);

                        Order order = new Order(menuId, quantity, item);
                        Datas.addToOrders(PreviousOrders.this, order);
                    }
                    Datas.setIsEdit(PreviousOrders.this, true);
                    startActivity(new Intent(PreviousOrders.this, OrdersActivity.class));

                } catch (Exception e) {
                    showToast("Error! Pull down to refresh");
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();

                showToast("Error! Pull down to refresh");
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void getOrders() {

        client.get(URLS.GET_PREVIOUS_ORDERS, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                try {
                    String response = new String(responseBody);
                    JSONObject motherObj = new JSONObject(response);
                    previousOrders = Geson.g().fromJson(motherObj.getJSONArray("data").toString(), PreviousOrder[].class);
                    adapter = new PreviousOrdersAdapter(getApplicationContext(), previousOrders);
                    previousOrderslv.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("------------", "Failed");
                dialog.dismiss();

            }
        });

    }
}
