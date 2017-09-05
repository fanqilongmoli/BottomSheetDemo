package com.fanqilong.bottomsheetdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fanqilong.bottomsheetdemo.fragment.FullSheetDialogFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    BottomSheetBehavior behavior;
    private View bottom_sheet;
    //解决状态栏  变黑得为题
    //http://www.jianshu.com/p/8d43c222b551

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottom_sheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
                Log.d(TAG, "这里是bottomSheet状态的改变");
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
                Log.d(TAG, "slideOffset可以做一些动画");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //展示bottomSheet
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.btn2:
                //展示bottomSheetDialog
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(view1);
                mBottomSheetDialog.show();
                break;
            case R.id.btn3:
                //展示bottomSheetDialogFragment
                new FullSheetDialogFragment().show(getSupportFragmentManager(), "dialog");
                break;
        }

    }
}
