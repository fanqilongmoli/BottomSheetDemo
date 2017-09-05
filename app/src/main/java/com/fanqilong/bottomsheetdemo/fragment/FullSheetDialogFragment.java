package com.fanqilong.bottomsheetdemo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.fanqilong.bottomsheetdemo.R;

/**
 * Created by fanqilong on 2017/9/5.
 */

public class FullSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private BottomSheetBehavior<View> mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bottom_sheet, null);
        view.findViewById(R.id.tv_close).setOnClickListener(this);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    @Override
    public void onClick(View view) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
