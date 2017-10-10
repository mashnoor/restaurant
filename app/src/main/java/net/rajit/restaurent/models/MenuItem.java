package net.rajit.restaurent.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class MenuItem {
    @SerializedName("menu_id")
    private int menu_id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private String price;
    @SerializedName("image")
    private String image;
    @SerializedName("available")
    private int available;

    public MenuItem(int menu_id, String code, String name, String description, String price, String image, int available) {
        this.menu_id = menu_id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.available = available;
    }

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
