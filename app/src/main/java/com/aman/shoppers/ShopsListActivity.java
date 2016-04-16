package com.aman.shoppers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.ShopAdapter;

public class ShopsListActivity extends AppCompatActivity {

    private ListView shopListView;
    private static Keys keys = Keys.getInstance();
    private ServerClient client  = new ServerClient();
    private Context context = ShopsListActivity.this;
    private ArrayList<String> shopName = new ArrayList<String>();
    private ArrayList<String> shopId = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_list);
        shopListView = (ListView) findViewById(R.id.shop_listView);
        shopListView.setSelector(R.drawable.cell_selector);
        getShopData();
    }

    private void getShopData() {
        SharedPreferences sharedPreferences = getSharedPreferences(keys.SHARED_USERNAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(keys.KEY_USERNAME)) {
            String username = sharedPreferences.getString(keys.KEY_USERNAME, null);
            JSONObject params = new JSONObject();
            try {
                params.put(keys.KEY_USERNAME,username);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            client.HTTPRequestGET(this, keys.SHOP_LIST_URL, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int status = response.getInt("status");
                        String message;
                        if (status == keys.STATUS_OK) {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray list = data.getJSONArray("shop_list");
                            for(int i = 0; i < list.length(); i++) {
                                JSONObject detail = list.getJSONObject(i);
                                String id = detail.getString(keys.KEY_SHOP_ID);
                                String name = detail.getString(keys.KEY_SHOP_NAME);
                                shopId.add(id);
                                shopName.add(name);
                            }
                            setShopData();
                        } else {
                            message = response.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setShopData() {
        ShopAdapter adapter = new ShopAdapter(this, shopName, shopId);
        shopListView.setAdapter(adapter);
    }

    public void didSelectRowAtPosition(int position) {
        String id = shopId.get(position);
        Intent intent = new Intent(context, EmployeeListActivity.class);
        intent.putExtra(keys.KEY_SHOP_ID, id);
        startActivity(intent);
    }
}
