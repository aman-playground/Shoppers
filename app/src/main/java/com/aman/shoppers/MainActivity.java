package com.aman.shoppers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ServerClient client  = new ServerClient();
    private String user_name;
    private String password;
    private EditText username_field,password_field;
    private static Keys keys = Keys.getInstance();
    private Context context = MainActivity.this;
    SharedPreferences sharedPreferences;
    Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_field = (EditText) findViewById(R.id.user_name_textfield);
        password_field = (EditText) findViewById(R.id.password_textfield);
        login_button = (Button) findViewById(R.id.login_button);
        sharedPreferences = getSharedPreferences(keys.SHARED_USERNAME, Context.MODE_PRIVATE);
        buttonListener();
    }

    private void buttonListener() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        if (!validTextFields())
            return;
        JSONObject params = new JSONObject();
        try {
            params.put(keys.KEY_USERNAME,user_name);
            params.put(keys.KEY_PASSWORD,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        client.HTTPRequestGET(this, keys.LOGIN_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    String message;
                    if (status == keys.STATUS_OK) {
                        JSONObject data = response.getJSONObject("data");
                        String owner = data.getString(keys.KEY_OWNER_NAME);
                        message = "Welcome " + owner;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(keys.KEY_USERNAME, user_name);
                        editor.commit();
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        nextScreen();
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

    private void nextScreen() {
        Intent intent = new Intent(context,ShopsListActivity.class);
        startActivity(intent);
    }

    private boolean validTextFields() {
        user_name = username_field.getText().toString();
        password = password_field.getText().toString();
        return (user_name.length() > 0 && password.length() > 0);
    }
}
