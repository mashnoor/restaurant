package net.rajit.restaurent.models;

/**
 * Created by rajit on 9/24/17.
 */

public class Table {

    private String id;
    private String code;
    private String capacity;
    private String created_at;
    private String updated_at;


    public String getCode() {
        return code;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
