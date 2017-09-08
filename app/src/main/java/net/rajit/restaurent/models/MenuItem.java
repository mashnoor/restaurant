package net.rajit.restaurent.models;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class MenuItem {
    private int menu_id;
    private String code;
    private String name;
    private String description;
    private String price;
    private String image;
    private int available;

    public int getMenu_id() {
        return menu_id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getAvailable() {
        return available;
    }
}
