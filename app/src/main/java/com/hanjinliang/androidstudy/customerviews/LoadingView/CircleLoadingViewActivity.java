package com.hanjinliang.androidstudy.customerviews.LoadingView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;

public class CircleLoadingViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_loading_view);
        final CircleLoadingView circleLoadingView=findViewById(R.id.CircleLoadingView);
        findViewById(R.id.Start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleLoadingView.loading();
            }
        });
        findViewById(R.id.End).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleLoadingView.finish();
            }
        });
    }
}
