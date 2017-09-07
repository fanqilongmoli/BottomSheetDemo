package com.fanqilong.bottomsheetdemo.payview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.fanqilong.bottomsheetdemo.R;

/**
 * 仿支付宝支付状态得view
 */
public class PayStatusActivity extends AppCompatActivity {

    private PayStatusView payStatusView;
    private Button btn_success;
    private Button btn_fail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_status);

        payStatusView = ((PayStatusView) findViewById(R.id.PayStatusView));

        btn_success = ((Button) findViewById(R.id.btn_success));

        btn_fail = ((Button) findViewById(R.id.btn_fail));

        payStatusView.loadLoading();

        btn_success.setOnClickListener(v -> payStatusView.loadSuccess());

        btn_fail.setOnClickListener(v -> payStatusView.loadFailure());
    }
}
