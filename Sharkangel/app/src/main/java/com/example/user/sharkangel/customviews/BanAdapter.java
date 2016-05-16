package com.example.user.sharkangel.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sharkangle.R;

import java.util.ArrayList;

/**
 * Created by USER on 2016/4/23.
 */
public class BanAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> ban;

    public BanAdapter(Context context,  ArrayList<String> ban){
        this.context = context;
        this.ban = ban;
    }



    @Override
    public int getCount() {
        return ban.size();
    }

    @Override
    public Object getItem(int position) {
        return ban.get(position);
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
            convertView = mInflater.inflate(R.layout.txetview, null);
        }

        TextView itemname=(TextView)convertView.findViewById(R.id.textView1);

        if(ban.get(position).equals("Alopiidae"))
        {
            itemname.setText("Alopiidae");
            itemname.setTextSize(30);
            itemname.setTextColor(Color.MAGENTA);
            itemname.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else if(ban.get(position).equals("Sphyrnidae"))
        {
            itemname.setText("Sphyrnidae");
            itemname.setTextSize(30);
            itemname.setTextColor(Color.MAGENTA);
            itemname.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else
        {
            itemname.setText(ban.get(position));
            itemname.setTextSize(27);
            itemname.setTextColor(Color.DKGRAY);
        }



        return convertView;
    }

}

