package com.aman.shoppers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.ShopAdapter;

public class EmployeeListActivity extends AppCompatActivity {

    private ListView employeeListView;
    private ArrayList<String> employeeName;
    private ArrayList<String> employeeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_list);
        employeeListView = (ListView) findViewById(R.id.shop_listView);
        getShopData();
    }

    private void getShopData() {

        setShopData();
    }

    private void setShopData() {
        ShopAdapter adapter = new ShopAdapter(this, employeeName, employeeId);
        employeeListView.setAdapter(adapter);
    }

    public void didSelectRowAtPosition(int position) {

    }
}
