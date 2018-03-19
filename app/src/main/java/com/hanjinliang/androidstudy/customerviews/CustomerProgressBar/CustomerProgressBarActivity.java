package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.graphics.Color;
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


        MyProgressView myProgressView1=findView(R.id.MyProgressView1);
        myProgressView1.setColor(Color.RED);
        myProgressView1.setPercent(88);
        MyProgressView myProgressView2=findView(R.id.MyProgressView2);
        myProgressView2.setColor(Color.RED);
        myProgressView2.setPercent(67);
        MyProgressView myProgressView3=findView(R.id.MyProgressView3);
        myProgressView3.setColor(Color.RED);
        myProgressView3.setPercent(48);
        MyProgressView myProgressView4=findView(R.id.MyProgressView4);
        myProgressView4.setColor(Color.RED);
        myProgressView4.setPercent(0);

        MyProgressDetailView myProgressDetailView1=findView(R.id.MyProgressDetailView1);
        myProgressDetailView1.setPercent(0);

        MyProgressDetailView myProgressDetailView2=findView(R.id.MyProgressDetailView2);
        myProgressDetailView2.setPercent(60);
        myProgressDetailView2.setColor(Color.GREEN);

        MyProgressDetailView myProgressDetailView3=findView(R.id.MyProgressDetailView3);
        myProgressDetailView3.setPercent(98);

    }
}
