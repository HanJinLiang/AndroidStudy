package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.Random;


public class CustomerProgressBarActivity extends BaseActivity {
    CustomerLoadingView mCustomerLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_progress_bar);

        mCustomerLoadingView=findView(R.id.CustomerLoadingView);
        mCustomerLoadingView.setClickListener(new CustomerLoadingView.ClickListener() {
            @Override
            public void onClick() {
                mCustomerLoadingView.startAnim();
            }

            @Override
            public void onAnimFinish() {
                ToastUtils.showLong("动画结束");
            }
        });

        findView(R.id.CircleBarBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                CircleBar circleBar=findView(R.id.CircleBar);
                circleBar.update(new Random().nextInt(100),true);
            }
        });
    }
}
