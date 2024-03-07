package com.hanjinliang.androidstudy.customerviews.signView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hanjinliang.androidstudy.R;

public class SignViewActivity extends AppCompatActivity {
    SignView signView;
    ConstraintLayout contentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_view);

        contentLayout = findViewById(R.id.contentLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentLayout.getLayoutParams();
        params.width = ScreenUtils.getScreenHeight() - BarUtils.getStatusBarHeight() - SizeUtils.dp2px(50);
        params.height = ScreenUtils.getScreenWidth();
        params.topMargin = params.width/2 - params.height/2;
        contentLayout.setLayoutParams(params);

        contentLayout.setRotation(90);



        signView = findViewById(R.id.signView);
        signView.setSignListener(new SignView.SignListener() {
            @Override
            public void startSign() {
                findViewById(R.id.signCenterHint).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.signReset).setOnClickListener(v -> signView.clearSign());
        findViewById(R.id.signConfirm).setOnClickListener(v -> signView.saveSign());
    }
}
