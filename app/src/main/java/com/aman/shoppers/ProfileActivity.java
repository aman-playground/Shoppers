package com.aman.shoppers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private static Keys keys = Keys.getInstance();
    private ServerClient client  = new ServerClient();
    private Context context = ProfileActivity.this;
    private TextView nameTextView, usernameTextView;
    private EditText oldEditText, newEditText, renewEditText;
    private Button changePasswordButton, doneButton;
    private LinearLayout passwordView;
    private String userName, ownerName, oldPass, newPass, renewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Assigning UI elements to vaiables
        nameTextView = (TextView)findViewById(R.id.name_textView);
        usernameTextView = (TextView)findViewById(R.id.user_name_textView);
        oldEditText = (EditText)findViewById(R.id.old_editText);
        newEditText = (EditText)findViewById(R.id.new_editText);
        renewEditText = (EditText)findViewById(R.id.renew_editText);
        changePasswordButton = (Button)findViewById(R.id.change_button);
        doneButton = (Button)findViewById(R.id.done_button);
        passwordView = (LinearLayout)findViewById(R.id.password_view);
        onClickListeners();
        passwordView.setVisibility(View.GONE);
    }

    private void onClickListeners() {
        SharedPreferences sharedPreferences = getSharedPreferences(keys.SHARED_USERNAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(keys.KEY_USERNAME)) {
            userName = sharedPreferences.getString(keys.KEY_USERNAME, null);
            ownerName = sharedPreferences.getString(keys.KEY_OWNER_NAME, null);
            nameTextView.setText(ownerName);
            usernameTextView.setText(userName);
        }
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordView.setVisibility(View.VISIBLE);
                changePasswordButton.setVisibility(View.INVISIBLE);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }
//  Called when done button is pressed
    private void changePassword() {
        oldPass = oldEditText.getText().toString();
        newPass = newEditText.getText().toString();
        renewPass = renewEditText.getText().toString();
        if (!validTextFields())
            return;
        JSONObject params = new JSONObject();
        try {
            params.put(keys.KEY_USERNAME,userName);
            params.put(keys.KEY_OLD_PASSWORD,oldPass);
            params.put(keys.KEY_NEW_PASSWORD,newPass);
            params.put(keys.KEY_RENEW_PASSWORD,renewPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        client.HTTPRequestGET(this, keys.CHANGE_PASSWORD_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    String message;
                    if (status == keys.STATUS_OK) {
                        message = response.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        passwordView.setVisibility(View.GONE);
                        changePasswordButton.setVisibility(View.VISIBLE);
                        keys.logout(ProfileActivity.this, ProfileActivity.this);
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

    private boolean validTextFields() {
        if (!(oldPass.equals("") && newPass.equals("") && renewPass.equals(""))) {
            if (newPass.equals(renewPass)) {
                return true;
            } else {
                Toast.makeText(context, "Passwords Mismatch", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(context, "Fill All Fields", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
