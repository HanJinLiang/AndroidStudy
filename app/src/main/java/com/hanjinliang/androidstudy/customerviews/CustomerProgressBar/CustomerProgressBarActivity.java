package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;


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
    }
}
