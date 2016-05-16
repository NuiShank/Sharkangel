package com.example.user.sharkangel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.sharkangel.customviews.DataAdapter;
import com.example.user.sharkangel.tools.DataItem;
import com.example.user.sharkangel.tools.Progressdialog;
import com.example.user.sharkangle.R;
import com.example.user.sharkangel.tools.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewDataList extends Activity {
    private ListView listView;
    private ArrayList<DataItem> item;
    private DataAdapter adapter;
    private Progressdialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdatalist);
        progressdialog=new Progressdialog(this);
        load();
        listView = (ListView) findViewById(R.id.listView);
        item=new ArrayList<>();

        adapter=new DataAdapter(getApplicationContext(),item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ViewDataList.this,ViewDataDetail.class);
                intent.putExtra("id",item.get(position).getId());
                startActivity(intent);
                finish();
            }
        });
    }


    private void load( ) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        progressdialog.msg("Upload ...");
        progressdialog.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://192.168.137.1/index.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressdialog.hideDialog();
                Log.d("111", "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.i("error",String.valueOf(error));
                    JSONObject jsonData;
                    if (!error) {
                        JSONArray jsonArray=new JSONArray(jObj.getString("shark"));
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            jsonData=jsonArray.getJSONObject(i);
                            String icon="http://192.168.137.1/uploads/"+jsonData.getString("Picture");
                            item.add(new DataItem(jsonData.getString("id"),icon,jsonData.getString("Species")));
                            adapter.notifyDataSetChanged();
                        }


                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("123", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "連線失敗", Toast.LENGTH_SHORT).show();
                progressdialog.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "load");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
