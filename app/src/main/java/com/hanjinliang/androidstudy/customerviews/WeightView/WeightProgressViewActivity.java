package com.hanjinliang.androidstudy.customerviews.WeightView;

import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class WeightProgressViewActivity extends AppCompatActivity {
    WeightProgressView mWeightProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_progress_view);
        mWeightProgressView= (WeightProgressView) findViewById(R.id.WeightProgressView);
    }

    public void btn_stop(View view){
        mWeightProgressView.stopRotate();
    }

    public void btn_start(View view){
        mWeightProgressView.startRotate();
    }
}
