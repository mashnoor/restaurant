package net.rajit.restaurent.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.rajit.restaurent.R;
import net.rajit.restaurent.activities.Signin;

import org.json.JSONObject;

/**
 * Created by Nowfel Mashnoor on 11/21/2017.
 */

public class MyMessagingService extends FirebaseMessagingService {

    private void showNotification(String title, String msg)
    {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //Define sound URI
        //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.siren);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(msg)
                .setSound(soundUri)
                .setAutoCancel(true); //This sets the sound to play
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Signin.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

//Display notification
        notificationManager.notify(0,mBuilder.build());

    }




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        // ...


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("-------", "From: " + remoteMessage.getFrom());
        try {
            JSONObject result = new JSONObject(remoteMessage.getData().get("body"));
            String msg = "Order ID: " + result.getString("order_id") + " Table ID: " + result.getString("table_id");

            showNotification("Order Ready", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("-------", remoteMessage.getData().get("body"));
    }

}
