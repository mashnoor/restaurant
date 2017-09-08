package net.rajit.restaurent.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class Datas {
    private static final String MY_PREFS_NAME = "rajit_restaurent";

    public static void setWaiterName(Activity activity, String waiterName) {

        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("waiter_name", waiterName);

        editor.apply();
    }

    public static String getWaiterName(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        return prefs.getString("waiter_name", "null");//"No name defined" is the default value.


    }
    public static void setAuthorizationKey(Activity activity, String authKey) {

        SharedPreferences.Editor editor = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("auth_key", authKey);

        editor.apply();
    }

    public static String getAuthorizationKey(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        return prefs.getString("auth_key", "null");//"No name defined" is the default value.


    }
}
