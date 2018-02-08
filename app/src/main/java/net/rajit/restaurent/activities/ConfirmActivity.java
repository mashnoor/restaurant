package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class ConfirmActivity extends AppCompatActivity {


    @BindView(R.id.txtTotalBill)
    TextView txtTotalBill;
    @BindView(R.id.txtOrderId)
    TextView txtOrderId;
    AsyncHttpClient client;
    ProgressDialog dialog;
    String orderId;

    @BindView(R.id.layoutSwipe)
    SwipeRefreshLayout layoutSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ButterKnife.bind(this);
        registerSwipeListener();
        getSummary();




    }

    private void getSummary()
    {
        orderId = getIntent().getExtras().getString("order_id");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Generating Summary...");
        client = new AsyncHttpClient();
        client.addHeader("authorization", "Bearer " + Datas.getAuthorizationKey(this));
        client.get(URLS.ORDER_URL + "/" + orderId, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String reponse = new String(responseBody);
                try {
                    JSONObject summaryObj = new JSONObject(reponse).getJSONObject("data").getJSONObject("summary");
                    txtTotalBill.setText("Total Bill : " + summaryObj.getString("net_total"));
                    txtOrderId.setText("Order ID : " + orderId);
                } catch (Exception e) {
                    showToast("Something went wrong");
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong");
                finish();

            }
        });

    }

    private void registerSwipeListener()
    {
        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSummary();
                layoutSwipe.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.btnOk)
    public void okbtn() {

        finish();
        startActivity(new Intent(ConfirmActivity.this, WelcomeActivity.class));
    }

    private void showToast(String msg) {
        Toast.makeText(ConfirmActivity.this, msg, Toast.LENGTH_LONG).show();
    }

}
