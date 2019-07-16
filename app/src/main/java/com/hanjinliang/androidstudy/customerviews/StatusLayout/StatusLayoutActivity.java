package com.hanjinliang.androidstudy.customerviews.StatusLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by monkey on 2019-07-16 16:16.
 * Describe:
 */

public class StatusLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuslayout);
        final StatusLayout layout1=findViewById(R.id.layout1);
        final StatusLayout layout2=findViewById(R.id.layout2);
       final View rootView=findViewById(R.id.rootLayout);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setShowStroke(true);
                layout2.setShowStroke(false);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setShowStroke(true);
                layout1.setShowStroke(false);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2==0) {
                    layout1.setInnerBgColor(Color.parseColor("#2AA1C3"));
                    layout2.setInnerBgColor(Color.parseColor("#2AA1C3"));
                    rootView.setBackgroundColor(Color.parseColor("#1d98bd"));
                }else {
                    layout1.setInnerBgColor(Color.parseColor("#2A2A46"));
                    layout2.setInnerBgColor(Color.parseColor("#2A2A46"));
                    rootView.setBackgroundColor(Color.parseColor("#1c1e3b"));
                }
                count++;
            }
        });
    }

    private int count=1;
}
