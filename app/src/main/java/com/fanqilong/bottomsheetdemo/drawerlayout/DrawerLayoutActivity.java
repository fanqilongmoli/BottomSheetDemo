package com.fanqilong.bottomsheetdemo.drawerlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fanqilong.bottomsheetdemo.R;

public class DrawerLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        findViewById(R.id.btn1).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawerLayoutBelowToolbarActivity.class));
        });
        findViewById(R.id.btn2).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawerLayoutOneActivity.class));
        });
        findViewById(R.id.btn3).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawerLayoutOtherActivity.class));
        });
    }
}
