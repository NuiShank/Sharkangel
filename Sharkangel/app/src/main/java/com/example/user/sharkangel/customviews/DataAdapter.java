package com.example.user.sharkangel.customviews;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sharkangle.R;
import com.example.user.sharkangel.tools.DataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by USER on 2016/4/23.
 */
public class DataAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataItem> dataItems;

    public DataAdapter(Context context, ArrayList<DataItem> dataItems){
        this.context = context;
        this.dataItems = dataItems;
    }



    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.datalist, null);
        }

        TextView itemname=(TextView)convertView.findViewById(R.id.itemName);
        itemname.setText(dataItems.get(position).getId()+"."+dataItems.get(position).getCtno());

        ImageView imageview=(ImageView)convertView.findViewById(R.id.icon);

        Picasso.with(context)
                .load(Uri.parse(dataItems.get(position).getIcon()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageview);

        return convertView;
    }

}

