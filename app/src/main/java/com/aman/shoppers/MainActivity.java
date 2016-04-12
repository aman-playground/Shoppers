package com.aman.shoppers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ServerClient client  = new ServerClient();
    private String user_name;
    private String password;
    private EditText username_field,password_field;
    Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_field = (EditText) findViewById(R.id.user_name_textfield);
        password_field = (EditText) findViewById(R.id.password_textfield);
        login_button = (Button) findViewById(R.id.login_button);
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
        JSONObject param = new JSONObject();
        try {
            param.put(Keys.getInstance().USERNAME,user_name);
            param.put(Keys.getInstance().PASSWORD,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        client.HTTPRequestGET("", param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Pass",response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.getLocalizedMessage());
            }
        });
    }

    private boolean validTextFields() {
        user_name = username_field.getText().toString();
        password = password_field.getText().toString();
        return (user_name.length() > 0 && password.length() > 0);
    }
}
