package net.rajit.restaurent.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajit on 9/24/17.
 */

public class Category {
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public Category(String categoryId, String image, String categoryName) {
        this.categoryId = categoryId;
        this.image = image;
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
