package net.rajit.restaurent.utils;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class URLS {
    final private static String BASE_URL = "http://rajitdemos.net/restaurant/public/api/";
    final public static String LOGIN_URL = BASE_URL + "login";
    public static String getMenuUrl(String menuId)
    {
        return BASE_URL + "menus/" + menuId;
    }
    final public static String ORDER_URL = BASE_URL + "orders";
}
