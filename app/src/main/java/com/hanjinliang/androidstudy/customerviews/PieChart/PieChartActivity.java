package com.hanjinliang.androidstudy.customerviews.PieChart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {
    PieChartView mPieChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        mPieChartView= (PieChartView) findViewById(R.id.PieChart);

        ArrayList<PieBeans> datas=new ArrayList<>();
        datas.add(new PieBeans(2,"java", Color.RED));
        datas.add(new PieBeans(28,"Android", Color.GREEN));
        datas.add(new PieBeans(30,"IOS", Color.BLUE));
        datas.add(new PieBeans(5,"C++", Color.CYAN));
        datas.add(new PieBeans(35,"C#", Color.DKGRAY));
        mPieChartView.setDatas(datas);
    }
}
