package com.aman.shoppers;

/**
 * Created by Aman Jain on 12/04/2016.
 */
public class Keys {
    private static Keys ourInstance = new Keys();

    public static Keys getInstance() {
        return ourInstance;
    }

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_OWNER_NAME = "owner_name";
    public static final String KEY_SHOP_ID = "shop_id";
    public static final String KEY_SHOP_NAME = "shop_name";
    public static final String KEY_SALESMAN_ID = "salesman_id";
    public static final String KEY_SALESMAN_NAME = "salesman_name";
    public static final String LOGIN_URL = "login_user.php";
    public static final String SHOP_LIST_URL = "shop_list.php";
    public static final String SALESMAN_LIST_URL = "salesman_list.php";
    public static final int STATUS_OK = 200;
    public static final int STATUS_SERVER_ERROR = 404;
    public static final int STATUS_NO_DATA = 401;
    public static final String SHARED_USERNAME = "shared_username";
    private Keys() {
    }
}
