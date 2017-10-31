package net.rajit.restaurent.models;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nowfel Mashnoor on 10/31/2017.
 */

public class PreviousOrder {

    /***
     * 1 -> Pending
     * 2 -> Processing
     * 3 -> Serve
     * 4 -> Invoice Print
     * 5 -> Cash Received
     */
    @SerializedName("id")
    private String id;
    @SerializedName("table_id")
    private String table_id;
    @SerializedName("status")
    private String status;



    public String getId() {
        return id;
    }

    public String getTable_id() {
        return table_id;
    }
    public int getColor()
    {
        if(status.equals("1"))
        {
            return Color.RED;
        }
        else if (status.equals("2"))
        {
            return Color.BLUE;
        }
        else
        {
            return Color.GREEN;
        }
    }

    public boolean isEditable()
    {
        if(status.equals("1") || status.equals("2"))
            return true;
        return false;
    }

    public String getStatus() {
       if(status.equals("1"))
       {
           return "Pending";
       }
       else if (status.equals("2"))
       {
           return "In Progress";
       }
       else
       {
           return "Completed";
       }
    }
}
