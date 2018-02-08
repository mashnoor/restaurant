package net.rajit.restaurent.utils;

/**
 * Created by Mashnoor on 6/19/17.
 */

public class URLS {
    final private static String BASE_URL = "http://192.168.1.28/api/";
    //final private static String BASE_URL = "http://rajitdemos.net/restaurant/res/restaurant/public/api/";
    //final private static String BASE_URL = "http://02d6989f.ngrok.io/api/";
    //final private static String BASE_URL = "http://192.168.1.27/restaurant/public/api/";
    final public static String LOGIN_URL = BASE_URL + "login";

    final public static String ORDER_URL = BASE_URL + "orders";
    final public static String ALL_CATEGORIES = BASE_URL + "categories";
    final public static String GET_ALL_TABLES = BASE_URL + "getalltables";
    final public static String GET_TABLES = BASE_URL + "tables";

    public static String getMenuByCategoryUrl(String id) {
        return BASE_URL + "menus/categorywise/" + id;
    }

    public static String getMenuUrl(String code) {
        return BASE_URL + "menus/" + code;
    }

    public static String getOrderDetailUrl(String orderId) {
        return ORDER_URL + "/" + orderId;
    }

    final public static String GET_PREVIOUS_ORDERS = BASE_URL + "orders";
    final public static String GET_SUMMARy = BASE_URL + "orders/summary";
}
