package Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aman.shoppers.EmployeeListActivity;
import com.aman.shoppers.R;
import com.aman.shoppers.ShopsListActivity;

import java.util.ArrayList;

public class EmployeeAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList employeeName;
    private ArrayList employeeId;
    private static LayoutInflater inflater = null;

    public EmployeeAdapter(Activity a, ArrayList name, ArrayList id) {
        activity = a;
        employeeId = id;
        employeeName = name;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (employeeName.isEmpty())
            return 0;
        return employeeName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        public TextView name;
//        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        ViewHolder holder;
        if (convertView == null) {
            cell = inflater.inflate(R.layout.employee_table_cell, null);
            holder = new ViewHolder();
            holder.name = (TextView)cell.findViewById(R.id.employee_name);
            cell.setTag(holder);
        } else {
            holder = (ViewHolder)cell.getTag();
        }

        if (!employeeName.isEmpty()) {
            holder.name.setText((String) employeeName.get(position));
            cell.setOnClickListener(new OnItemClickListener(position));
        }
        return cell;
    }


    @Override
    public void onClick(View v) {

    }

    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            EmployeeListActivity employeeListActivity = (EmployeeListActivity)activity;
            employeeListActivity.didSelectRowAtPosition(mPosition);
        }
    }
}
