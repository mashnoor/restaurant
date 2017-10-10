package net.rajit.restaurent.utils;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Nowfel Mashnoor on 10/10/2017.
 */

public class ClientGetter {
    private static AsyncHttpClient client = null;
    private ClientGetter(){}
    public static AsyncHttpClient getClient()
    {
        if(client == null)
        {
            client = new AsyncHttpClient();
        }
        return client;
    }
}
