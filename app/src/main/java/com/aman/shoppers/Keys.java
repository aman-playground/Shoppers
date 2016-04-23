package com.aman.shoppers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Aman Jain on 12/04/2016.
 */
public class Keys {
    private static Keys ourInstance = new Keys();

    public static Keys getInstance() {
        return ourInstance;
    }

    public static final int ROLE_ID_MANAGER = 1;
    public static final int ROLE_ID_SALESMAN = 2;
    public static final String KEY_ROLE = "role";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_OLD_PASSWORD = "oldpassword";
    public static final String KEY_NEW_PASSWORD = "newpassword";
    public static final String KEY_RENEW_PASSWORD = "repeatnewpassword";
    public static final String KEY_OWNER_NAME = "owner_name";
    public static final String KEY_SHOP_ID = "shop_id";
    public static final String KEY_SHOP_NAME = "shop_name";
    public static final String KEY_SALESMAN_ID = "salesman_id";
    public static final String KEY_SALESMAN_NAME = "salesman_name";
    public static final String LOGIN_URL = "login_user.php";
    public static final String SHOP_LIST_URL = "shop_list.php";
    public static final String SALESMAN_LIST_URL = "salesman_list.php";
    public static final String CHANGE_PASSWORD_URL = "change_password.php";
    public static final String SALESMAN_STATS_URL = "salesman_stats.php";
    public static final int STATUS_OK = 200;
    public static final String SHARED_USERNAME = "shared_username";
    public static final String RUPEE_SYMBOL = "₹";
    public static final int ANIMATION_TIME = 700;
    private Keys() {
    }

    public void logout(Activity activity, Context context) {
        Intent intent = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_USERNAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
