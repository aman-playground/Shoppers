package com.aman.shoppers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.EmployeeAdapter;
import Adapters.ShopAdapter;

public class EmployeeListActivity extends AppCompatActivity {

    private ListView employeeListView;
    private static Keys keys = Keys.getInstance();
    private ServerClient client  = new ServerClient();
    private Context context = EmployeeListActivity.this;
    private ArrayList<String> employeeName = new ArrayList<String>();
    private ArrayList<String> employeeId = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        employeeListView = (ListView) findViewById(R.id.employee_listView);
        employeeListView.setSelector(R.drawable.cell_selector);
        getEmployeeData();
    }

    private void getEmployeeData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(keys.KEY_SHOP_ID);
        JSONObject params = new JSONObject();
        try {
            params.put(keys.KEY_SHOP_ID,id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        client.HTTPRequestGET(this, keys.SALESMAN_LIST_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    String message;
                    if (status == keys.STATUS_OK) {
                        JSONObject data = response.getJSONObject("data");
                        JSONArray list = data.getJSONArray("salesman_list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject detail = list.getJSONObject(i);
                            String id = detail.getString(keys.KEY_SALESMAN_ID);
                            String name = detail.getString(keys.KEY_SALESMAN_NAME);
                            employeeId.add(id);
                            employeeName.add(name);
                        }
                        setEmployeeData();
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

    private void setEmployeeData() {
        EmployeeAdapter adapter = new EmployeeAdapter(this, employeeName, employeeId);
        employeeListView.setAdapter(adapter);
    }

    public void didSelectRowAtPosition(int position) {

    }
}
