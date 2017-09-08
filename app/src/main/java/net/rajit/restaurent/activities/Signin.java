package net.rajit.restaurent.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Type;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Netcheker;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.READ_CONTACTS;

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
        if(!Datas.getWaiterName(this).equals("null"))
        {
           startActivity(new Intent(this, Home.class));
            finish();
        }


    }

    private void showToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnSignin)
    public void signin()
    {
        if(!Netcheker.isNetworkAvailable(this))
        {
            showSnackbar("No internet connection", Type.ERROR);
            return;
        }
        String id = waiterID.getText().toString();
        String passwoed = waiterPassword.getText().toString();
        if(TextUtils.isEmpty(id))
        {
            waiterID.setError("Enter your id");
            return;
        }

        if(TextUtils.isEmpty(passwoed))
        {
            waiterPassword.setError("Enter your password");
            return;
        }
        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", id);
        params.put("password", passwoed);
        client.post(URLS.LOGIN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
               dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               // Logger.d(new String(responseBody));
                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    int status = obj.getInt("status");
                    if(status==1)
                    {
                        String api_token = obj.getJSONObject("data").getString("api_token");
                        String name = obj.getJSONObject("data").getString("name");
                        Datas.setAuthorizationKey(Signin.this, api_token);
                        Datas.setWaiterName(Signin.this, name);
                        startActivity(new Intent(Signin.this, Home.class));
                        finish();

                    }
                    else
                    {
                        showSnackbar("Credentials didn't match", Type.ERROR);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Logger.d(new String(responseBody));

            }
        });


    }
    private void showSnackbar(String msg, Type t)
    {
        com.chootdev.csnackbar.Snackbar.with(Signin.this,null)
                .type(t)
                .message(msg)
                .duration(Duration.LONG)
                .show();
    }


}

