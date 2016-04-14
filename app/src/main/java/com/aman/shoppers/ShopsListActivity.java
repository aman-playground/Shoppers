package com.aman.shoppers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.ShopAdapter;

public class ShopsListActivity extends AppCompatActivity {

    private ListView shopListView;
    private ArrayList<String> shopName;
    private ArrayList<String> shopId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_list);
        shopListView = (ListView) findViewById(R.id.shop_listView);
        getShopData();
    }

    private void getShopData() {
        setShopData();
    }

    private void setShopData() {
        ShopAdapter adapter = new ShopAdapter(this, shopName, shopId);
        shopListView.setAdapter(adapter);
    }

    public void didSelectRowAtPosition(int position) {

    }
}
