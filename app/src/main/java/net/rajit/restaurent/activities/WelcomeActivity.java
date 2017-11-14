package net.rajit.restaurent.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class WelcomeActivity extends Activity {

    @BindView(R.id.tvTotalamount)
    TextView tvtotalAmount;
    @BindView(R.id.tvTotalServed)
    TextView tvtotalServed;
    AsyncHttpClient client;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        if (Datas.isAnyPendingOrder(this)) {
            finish();
            startActivity(new Intent(this, OrdersActivity.class));
        }
        else
        {
            client = new AsyncHttpClient();
            client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
            client.get(URLS.GET_SUMMARy, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    dialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String r = new String(responseBody);
                    Log.d("----res", r);
                    try {
                        JSONObject motherObj = new JSONObject(r);
                        String totalSale = motherObj.getJSONObject("data").getString("order_count");
                        String totalAmount = motherObj.getJSONObject("data").getString("order_value");
                        Log.d("---------Sale", totalSale);
                        tvtotalServed.setText("Total Served today: " + totalSale);
                        tvtotalAmount.setText("Total amount: " + totalAmount + " TK");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();

                    }
                    dialog.dismiss();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    dialog.dismiss();
                }
            });
        }
    }

    public void goPlaceOrder(View v) {
        startActivity(new Intent(this, TableActivity.class));
    }
    public void goPreviousOrders(View v)
    {
        startActivity(new Intent(this, PreviousOrders.class));
    }
}
