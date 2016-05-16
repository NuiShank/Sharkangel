package com.example.user.sharkangel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.user.sharkangle.R;

public class MainActivity extends AppCompatActivity {

    Button btnUploadData,btnViewData,btnProhibitFish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        btnUploadData = (Button)findViewById(R.id.btnUploadData);
        btnViewData = (Button)findViewById(R.id.btnViewData);
        btnProhibitFish = (Button)findViewById(R.id.btnProhibitFish);

        btnUploadData.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TakePicture.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnViewData.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ViewDataList.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnProhibitFish.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProhibitFish.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}


