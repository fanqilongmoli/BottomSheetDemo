package com.fanqilong.bottomsheetdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fanqilong.bottomsheetdemo.bottomSheet.BottomSheetActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(view -> {
            Intent intent = new Intent(this, BottomSheetActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn2).setOnClickListener(view -> {

        });
        findViewById(R.id.btn3).setOnClickListener(view -> {

        });

    }


}
