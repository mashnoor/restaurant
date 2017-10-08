package net.rajit.restaurent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Netcheker;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class Signin extends AppCompatActivity {


    @BindView(R.id.txtID)
    EditText waiterID;
    @BindView(R.id.txtPassword)
    EditText waiterPassword;
    AsyncHttpClient client;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Logger.addLogAdapter(new AndroidLogAdapter());
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing In...");
        if (!Datas.getWaiterName(this).equals("null")) {
            startActivity(new Intent(this, Home.class));
            finish();
        }


    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnSignin)
    public void signin() {
        Intent i = new Intent(Signin.this, WelcomeActivity.class);
        startActivity(i);

        /***
         if (!Netcheker.isNetworkAvailable(this)) {
         showToast("No internet connection");
         return;
         }
         String id = waiterID.getText().toString();
         String passwoed = waiterPassword.getText().toString();
         if (TextUtils.isEmpty(id)) {
         waiterID.setError("Enter your id");
         return;
         }

         if (TextUtils.isEmpty(passwoed)) {
         waiterPassword.setError("Enter your password");
         return;
         }
         client = new AsyncHttpClient();
         RequestParams params = new RequestParams();
         params.put("username", id);
         params.put("password", passwoed);
         client.post(URLS.LOGIN_URL, params, new AsyncHttpResponseHandler() {
        @Override public void onStart() {
        dialog.show();
        }

        @Override public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        // Logger.d(new String(responseBody));
        dialog.dismiss();
        try {
        JSONObject obj = new JSONObject(new String(responseBody));
        int status = obj.getInt("status");
        if (status == 1) {
        String api_token = obj.getJSONObject("data").getString("api_token");
        String name = obj.getJSONObject("data").getString("name");
        Datas.setAuthorizationKey(Signin.this, api_token);
        Datas.setWaiterName(Signin.this, name);
        startActivity(new Intent(Signin.this, Home.class));
        finish();

        } else {
        showToast("Credentials didn't match");
        }
        } catch (Exception e) {
        e.printStackTrace();
        }


        }

        @Override public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Logger.d(new String(responseBody));

        }
        });
         ***/


    }


}

