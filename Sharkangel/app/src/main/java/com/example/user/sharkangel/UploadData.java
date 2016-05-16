package com.example.user.sharkangel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.sharkangel.tools.Config;
import com.example.user.sharkangle.R;
import com.example.user.sharkangel.tools.AndroidMultiPartEntity;
import com.example.user.sharkangel.tools.AppController;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadData extends AppCompatActivity {

    private GPSTracker gps;

    private TextView latitude,longitude,dateTime;

    private EditText length,scale,CTNO,Weight;

    private String pciturename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.uploaddata);


		File imgFile = new  File(getIntent().getStringExtra("Path"));
		if(imgFile.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            pciturename=imgFile.getName();



			((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);



		}



		final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        CTNO = (EditText)findViewById(R.id.CTNO);
		length = (EditText)findViewById(R.id.length);
		scale = (EditText)findViewById(R.id.scale);
        Weight= (EditText)findViewById(R.id.Weight);

		scale.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
					Double h=1650* Double.parseDouble(s.toString())/Double.parseDouble(getIntent().getStringExtra("Long"));
					length.setText(String.valueOf(h));}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		final Spinner speciesDropdown = (Spinner)findViewById(R.id.species);
		String[] speciesItems = new String[]{"Silky shark", "Oceanic whitetip shark", "Tiger shark", "Blue shark", "Bloch's hammerhead", "Scalloped hammerhead", "Great hammerhaed", "Smooth hammerhead", "Pelagic thresher shark"
				, "Bigeye thresher shark", "Common thresher shark", "Great white shark", "Shortfin mako shark", "Longfin mako shark", "Whale Shark", "Basking shark", "Megamouth  shark", "Other sharks"};
		ArrayAdapter<String> speciesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, speciesItems);
		speciesDropdown.setAdapter(speciesAdapter);



		final Spinner sexDropdown = (Spinner)findViewById(R.id.sex);
        String[] sexItems = new String[]{"F", "M"};
		ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexItems);
		sexDropdown.setAdapter(sexAdapter);

		latitude = (TextView)findViewById(R.id.latitude);
		longitude = (TextView)findViewById(R.id.longitude);
		dateTime = (TextView)findViewById(R.id.dateTime);

		dateTime.setText(currentDateTimeString);

		gps = new GPSTracker(UploadData.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {


			String gpsLatitude = String.valueOf(gps.getLatitude());
			String gpsLongitude = String.valueOf(gps.getLongitude());
			latitude.setText(gpsLatitude);
			longitude.setText(gpsLongitude);


			// \n is for new line
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

        ((Button)findViewById(R.id.btnUpload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upadte(CTNO.getText().toString(),currentDateTimeString,latitude.getText().toString(),longitude.getText().toString(),
                      speciesDropdown.getSelectedItem().toString(),sexDropdown.getSelectedItem().toString(),length.getText().toString(),Weight.getText().toString(),pciturename);

                new UploadFileToServer().execute();
                startActivity(new Intent(UploadData.this,MainActivity.class));
                finish();
            }
        });

    }

    private void upadte( final String CTNO,final String Date, final String Latitude,final String Longitude,
                         final String Species,final String Sex,final String Length,final String Weight,final String Picture ) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, "http://192.168.137.1/index.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("111", "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

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
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "upload");
                params.put("CTNO", CTNO);
                params.put("Date", Date);
                params.put("Latitude", Latitude);
                params.put("Longitude", Longitude);
                params.put("Species", Species);
                params.put("Sex", Sex);
                params.put("Length", Length);
                params.put("Weight", Weight);
                params.put("Picture",Picture);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
//            // setting progress bar to zero
//            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
//            // Making progress bar visible
//            progressBar.setVisibility(View.VISIBLE);
//
//            // updating progress bar value
//            progressBar.setProgress(progress[0]);
//
//            // updating percentage value
//            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
//                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(getIntent().getStringExtra("Path"));

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

//                // Extra parameters if you want to pass to server
//                entity.addPart("website",
//                        new StringBody("www.androidhive.info"));
//                entity.addPart("email", new StringBody("abc@gmail.com"));

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("123", "Response from server: " + result);


            super.onPostExecute(result);
        }

    }

}