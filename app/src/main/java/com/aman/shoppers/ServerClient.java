package com.aman.shoppers;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerClient {
    private String baseURL = "http://192.168.56.1/shoppers/";

    public JsonObjectRequest HTTPRequestGET(String url, JSONObject params, Response.Listener<JSONObject> listner, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,getAbsoluteURL(url), params, listner, errorListener);
        return jsonRequest;
    }

    public JsonObjectRequest HTTPRequestPOST(String url, JSONObject params, Response.Listener<JSONObject> listner, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,getAbsoluteURL(url), params, listner, errorListener);
        return jsonRequest;
    }

    private String getAbsoluteURL(String url) {
        String finalURL = baseURL + url;
        return finalURL;
    }


}
