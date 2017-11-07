package net.rajit.restaurent.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import net.rajit.restaurent.R;
import net.rajit.restaurent.activities.WelcomeActivity;
import net.rajit.restaurent.models.PreviousOrder;
import net.rajit.restaurent.utils.Datas;
import net.rajit.restaurent.utils.Geson;
import net.rajit.restaurent.utils.URLS;

import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class OrderNotifService extends Service {
    public OrderNotifService() {
    }

    private void getOrders(SyncHttpClient client) {
        client.addHeader("Authorization", "Bearer " + Datas.getAuthorizationKey(getApplication()));

        client.get(URLS.GET_PREVIOUS_ORDERS, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    PreviousOrder[] previousOrders;
                    String response = new String(responseBody);
                    JSONObject motherObj = new JSONObject(response);
                    previousOrders = Geson.g().fromJson(motherObj.getJSONArray("data").toString(), PreviousOrder[].class);
                    for (int i = 0; i < previousOrders.length; i++) {
                        PreviousOrder currOrder = previousOrders[i];
                        if (currOrder.getStatus().equals("In Progress")) {
                            Random random = new Random();
                            showNotification("Order ID - " + currOrder.getId(), "In Progress", random.nextInt());
                        } else {
                            Log.d("--------", "No order process");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("------------", "Failed " + new String(responseBody));


            }
        });

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SyncHttpClient client;
                client = new SyncHttpClient();

                while (true) {

                    getOrders(client);


                    Log.d("----Restaurabnt", "Working");
                    try {
                        Thread.sleep(30000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showNotification(String title, String message, int id) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }

}
