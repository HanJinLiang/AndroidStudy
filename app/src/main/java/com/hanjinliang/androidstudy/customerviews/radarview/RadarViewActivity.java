package com.hanjinliang.androidstudy.customerviews.radarview;

import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

/**
 * 雷达
 */
public class RadarViewActivity extends BaseActivity {
    RadarView mRadarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);
        mRadarView=findView(R.id.RadarView);
    }

    public void startScan(View view){
        mRadarView.startScan();
    }

    public void stopScan(View view){
        mRadarView.stopScan();
    }
}
