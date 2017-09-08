package net.rajit.restaurent.models;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class Order {
    private int menu_id;
    private String quantity;
    private MenuItem menuItem;

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

    public int getMenu_id() {
        return menu_id;
    }

    public String getQuantity() {
        return quantity;
    }
}
