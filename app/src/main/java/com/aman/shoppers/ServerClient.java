package com.aman.shoppers;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerClient {
    private String EmulatorBaseURL = "http://192.168.56.1/android/";
    private String LocalBaseURL = "http://localhost/android/";
    private String baseURL = EmulatorBaseURL;

    public void HTTPRequestGET(Activity activity, String url, JSONObject params, Response.Listener<JSONObject> listner, Response.ErrorListener errorListener) {
        String GETUrl = urlForGETRequest(url, params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, GETUrl, null, listner, errorListener);
        Volley.newRequestQueue(activity).add(jsonRequest);
    }

    public void HTTPRequestPOST(Activity activity, String url, JSONObject params, Response.Listener<JSONObject> listner, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,getAbsoluteURL(url), params, listner, errorListener);
        Volley.newRequestQueue(activity).add(jsonRequest);
    }

    private String getAbsoluteURL(String url) {
        String finalURL = baseURL + url;
        return finalURL;
    }

    private String urlForGETRequest(String url, JSONObject param) {
        String finalUrl = getAbsoluteURL(url) + "?";
        Iterator<?> keys = param.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            try {
                String value = param.getString(key);
                finalUrl += key + "=" + value + "&";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return finalUrl.substring(0, finalUrl.length() - 1);
    }


}
