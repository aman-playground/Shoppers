package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aman.shoppers.R;
import com.aman.shoppers.ShopsListActivity;

import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList shopName = new ArrayList<>();
    private ArrayList shopId = new ArrayList<>();
    private static LayoutInflater inflater = null;

    public ShopAdapter(Activity a, ArrayList name, ArrayList id) {
        activity = a;
        shopId = id;
        shopName = name;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (shopName.isEmpty())
            return 0;
        return shopName.size();
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
            cell = inflater.inflate(R.layout.shop_table_cell, null);
            holder = new ViewHolder();
            holder.name = (TextView)cell.findViewById(R.id.shop_name);
            cell.setTag(holder);
        } else {
            holder = (ViewHolder)cell.getTag();
        }

        if (!shopName.isEmpty()) {
            holder.name.setText((String)shopName.get(position));
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
            ShopsListActivity shopsListActivity = (ShopsListActivity)activity;
            shopsListActivity.didSelectRowAtPosition(mPosition);
        }
    }
}
