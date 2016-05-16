package com.example.user.sharkangel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.sharkangel.customviews.BanAdapter;
import com.example.user.sharkangle.R;

import java.util.ArrayList;


public class ProhibitFish extends AppCompatActivity {



    private ListView prohibitList;

    private BanAdapter adapter;
    private ArrayList<String> item;

//    private String[] pacificOcean = {"Silky shark", "Oceanic whitetip shark"};
//
//    private String[] atlantic = {"Silky shark", "Oceanic whitetip shark", "Pelagic thresher shark", "Bigeye thresher shark", "Common thresher shark"
//    , "Smooth hammerhead", "Scalloped hammerhead", "Great hammerhaed", "Bloch's hammerhead"};
//
//    private String[] indianOcean = {"Silky shark", "Oceanic whitetip shark", "Pelagic thresher shark", "Bigeye thresher shark", "Common thresher shark"};

    private String[][] ban={{"Silky shark", "Oceanic whitetip shark"},
            {"Silky shark", "Oceanic whitetip shark", "Alopiidae","Pelagic thresher shark", "Bigeye thresher shark", "Common thresher shark", "Sphyrnidae","Smooth hammerhead", "Scalloped hammerhead", "Great hammerhaed", "Bloch's hammerhead"},
            {"Silky shark", "Oceanic whitetip shark", "Alopiidae","Pelagic thresher shark", "Bigeye thresher shark", "Common thresher shark"}};




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.prohibitfish);

        item=new ArrayList<>();
//        location = (TextView)findViewById(R.id.location);
        prohibitList = (ListView) findViewById(R.id.prohibitFish);
        adapter=new BanAdapter(getApplication(),item);

        prohibitList.setAdapter(adapter);

//        gps = new GPSTracker(ProhibitFish.this);

        final Spinner speciesDropdown = (Spinner)findViewById(R.id.Ocean);
        String[] oceanItems = new String[]{"Pacific Ocean", "Atlantic Ocean", "Indian Ocean"};
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, oceanItems);
        speciesDropdown.setAdapter(speciesAdapter);
        speciesDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               item.clear();
                for(int i=0;i<ban[position].length;i++)
                {
                    item.add(ban[position][i]);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ((TextView)findViewById(R.id.More_Information)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://cites.org/eng/prog/shark/legality.php");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });

    }


}
