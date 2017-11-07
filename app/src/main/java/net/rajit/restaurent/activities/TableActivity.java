package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TableActivity extends AppCompatActivity {


    @BindView(R.id.tableList)
    Spinner tableList;
    AsyncHttpClient client;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        getTables();
    }

    private void getTables() {
        client.get(URLS.GET_TABLES, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject motherObj = new JSONObject(response);
                    JSONArray arr = motherObj.getJSONArray("data");

                    ArrayList<String> tables = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject currObj = arr.getJSONObject(i);
                        if (!currObj.getString("status").equals("occupied")) {
                            tables.add(currObj.getString("code"));
                        }
                    }
                    String[] tablesArray = new String[tables.size()];
                    tablesArray = tables.toArray(tablesArray);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                            (TableActivity.this, android.R.layout.simple_spinner_item,
                                    tablesArray); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    tableList.setAdapter(spinnerArrayAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("---------", "Error");
                dialog.dismiss();
            }
        });
    }

    public void goCategory(View v) {
        String tableCode = tableList.getSelectedItem().toString();
        Datas.setTableCode(this, tableCode);
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
    }

}
