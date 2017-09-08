package net.rajit.restaurent;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class Loader extends Application {
    private String token;

    public String getToken() {
        return token;
    }
    public void setToken(String tkn)
    {
        this.token = tkn;
    }
    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
