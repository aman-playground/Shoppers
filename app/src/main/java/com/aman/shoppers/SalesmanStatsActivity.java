package com.aman.shoppers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SalesmanStatsActivity extends AppCompatActivity {

    private static Keys keys = Keys.getInstance();
    private ServerClient client  = new ServerClient();
    private Context context = SalesmanStatsActivity.this;
    private TextView monthTextView, yearTextView, totalTextView;
    private int monthSales, yearSales, totalSales;
    private PieChart pieChart;
    private ArrayList<Entry> entry = new ArrayList<>();
    private PieDataSet pieDataSet;
    private PieData pieData;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_stats);
        pieChart = (PieChart)findViewById(R.id.stat_piechart);
        monthTextView = (TextView)findViewById(R.id.month_textView);
        yearTextView = (TextView)findViewById(R.id.year_textView);
        totalTextView = (TextView)findViewById(R.id.total_textView);

        pieChart.setDescription("");    // Hide the description
        pieChart.getLegend().setEnabled(false);
        sharedPreferences = getSharedPreferences(keys.SHARED_USERNAME, Context.MODE_PRIVATE);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(keys.KEY_SALESMAN_ID);
        JSONObject params = new JSONObject();
        try {
            params.put(keys.KEY_SALESMAN_ID,id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        client.HTTPRequestGET(this, keys.SALESMAN_STATS_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    String message;
                    if (status == keys.STATUS_OK) {
//                        parsing JSON data
                        JSONObject data = response.getJSONObject("data");
                        yearSales = data.getInt("year_sales");
                        monthSales = data.getInt("month_sales");
                        totalSales = data.getInt("total_sales");
                        updateChart();
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

    private void updateChart() {
//        creating chart based on data parsed
        entry.add(new Entry(monthSales, 0));
        entry.add(new Entry(yearSales, 1));
        entry.add(new Entry(totalSales, 2));
        pieDataSet = new PieDataSet(entry,"");
        ArrayList<Integer> color = new ArrayList<>();
        color.add(ContextCompat.getColor(context, R.color.colorTile1));
        color.add(ContextCompat.getColor(context, R.color.colorTile2));
        color.add(ContextCompat.getColor(context, R.color.colorTile3));
        pieDataSet.setColors(color);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("");
        labels.add("");
        pieData = new PieData(labels,pieDataSet);
        pieData.setDrawValues(false);
        pieChart.setData(pieData);
        pieChart.animateXY(keys.ANIMATION_TIME, keys.ANIMATION_TIME, Easing.EasingOption.EaseInCirc, Easing.EasingOption.EaseInOutBack);
        updateTextViews();
    }

    private void updateTextViews() {
        monthTextView.setText(keys.RUPEE_SYMBOL+Integer.toString(monthSales));
        yearTextView.setText(keys.RUPEE_SYMBOL+Integer.toString(yearSales));
        totalTextView.setText(keys.RUPEE_SYMBOL+Integer.toString(totalSales));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_item:
                if (sharedPreferences.contains(keys.KEY_USERNAME)) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.logout_item:
                keys.logout(this, this);
                finish();
                return true;
            default:super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
