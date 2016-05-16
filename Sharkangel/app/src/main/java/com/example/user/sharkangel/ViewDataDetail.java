package com.example.user.sharkangel;

/**
 * Created by USER on 2016/4/23.
 */
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.sharkangle.R;
import com.example.user.sharkangel.tools.AppController;
import com.example.user.sharkangel.tools.Progressdialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewDataDetail extends Activity {

    private Progressdialog progressdialog;

    ImageView fishImg;

    TextView ctno,species,sex,length,weight,longitude,latitude,time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdatadetail);

        progressdialog=new Progressdialog(this);

        fishImg = (ImageView)findViewById(R.id.fishImg);
        ctno = (TextView)findViewById(R.id.ctno);
        species = (TextView)findViewById(R.id.species);
        sex = (TextView)findViewById(R.id.sex);
        length = (TextView)findViewById(R.id.length);
        weight = (TextView)findViewById(R.id.weight);
        longitude = (TextView)findViewById(R.id.longitude);
        latitude = (TextView)findViewById(R.id.latitude);
        time = (TextView)findViewById(R.id.time);

        load(getIntent().getStringExtra("id"));

    }

    private void load(final String id ) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        progressdialog.msg("Upload ...");
        progressdialog.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://192.168.137.1/index.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

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

                            Picasso.with(getApplication())
                                    .load(Uri.parse(icon))
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher)
                                    .into(fishImg);

                            ctno.setText(jsonData.getString("CTNO"));
                            species.setText(jsonData.getString("Species"));
                            sex.setText(jsonData.getString("Sex"));
                            length.setText(jsonData.getString("Length"));
                            weight .setText(jsonData.getString("Weight"));
                            longitude .setText(jsonData.getString("Longitude"));
                            latitude.setText(jsonData.getString("Latitude"));
                            time .setText(jsonData.getString("Date"));
                            progressdialog.hideDialog();
                        }


                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                        progressdialog.hideDialog();
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
                params.put("tag", "detail");
                params.put("id", id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}

