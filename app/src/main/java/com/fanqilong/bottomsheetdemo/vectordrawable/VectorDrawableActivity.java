package com.fanqilong.bottomsheetdemo.vectordrawable;

import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fanqilong.bottomsheetdemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class VectorDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);

        setTitle("VectorDrawable");



        ImageView imageview = (ImageView) findViewById(R.id.av1);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    ((AnimatedVectorDrawableCompat) imageview.getDrawable()).start();
                });
            }
        }, 2000);


    }
}
