package com.hanjinliang.androidstudy.customerviews.HealthIndexView;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

public class HealthIndexViewActivity extends AppCompatActivity {

    private String[] mHealthValues=new String[]{"10.0%","17.0%","21.0%","25.0%"};
    private int[] mIndexColors=new int[]{Color.parseColor("#cd5ebb"),Color.parseColor("#0acb08"),Color.parseColor("#c7e97c"),Color.parseColor("#ff0000"),Color.parseColor("#c10000")};
    private String[] mDescTexts=new String[]{"偏低","健康","警戒","偏胖","重度肥胖"};

    HealthIndexView mHealthIndexView1;
    HealthIndexView mHealthIndexView2;
    HealthIndexView mHealthIndexView3;
    HealthIndexView mHealthIndexView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_index_view);
        mHealthIndexView1= (HealthIndexView) findViewById(R.id.HealthIndexView1);
        mHealthIndexView2= (HealthIndexView) findViewById(R.id.HealthIndexView2);
        mHealthIndexView3= (HealthIndexView) findViewById(R.id.HealthIndexView3);
        mHealthIndexView4= (HealthIndexView) findViewById(R.id.HealthIndexView4);

        mHealthIndexView1.setData(new String[]{"10.0%","21.0%","25.0%"},new int[]{Color.parseColor("#cd5ebb"),Color.parseColor("#0acb08"),Color.parseColor("#c7e97c"),Color.parseColor("#ff0000")},new String[]{"偏低","健康","警戒","偏胖"});

        mHealthIndexView2.setData(new String[]{"1519"},new int[]{Color.parseColor("#ffc100"),Color.parseColor("#0acb08")},new String[]{"偏低","达标"});

        mHealthIndexView3.setData(new String[]{"10.0%", "25.0%"},new int[]{ Color.parseColor("#0acb08"),Color.parseColor("#c7e97c"),Color.parseColor("#ff0000")},new String[]{"偏低","健康", "偏胖"});

        mHealthIndexView4.setData(mHealthValues,mIndexColors,mDescTexts);
    }
}