package net.rajit.restaurent.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class Order {
    @SerializedName("menu_id")
    private int menu_id;

    @SerializedName("quantity")
    private String quantity;

    private MenuItem menuItem;

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }



    public Order(int menu_id, String quantity) {
        this.menu_id = menu_id;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Order(int menu_id, String quantity, MenuItem menuItem) {
        this.menu_id = menu_id;
        this.quantity = quantity;
        this.menuItem = menuItem;

    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public String getQuantity() {
        return quantity;
    }
}
