package com.hanjinliang.androidstudy.customerviews.CustomerRefreshLayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.CustomerRefreshLayout.test.CustomSwipeRefreshLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by monkey on 2019-07-09 14:41.
 * Describe:
 */

public class CustomerRefreshActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        SmartRefreshLayout refreshLayout=findViewById(R.id.customerRefreshLayout);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new TCRefreshHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final @NonNull RefreshLayout refreshLayout) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefresh();
                            }
                        });
                    }
                },5000);

            }
        });

    }
}
