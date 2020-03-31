package com.hanjinliang.androidstudy.customerviews.PieChart;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PieChartActivity extends AppCompatActivity {
    PieChartView mPieChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        mPieChartView= (PieChartView) findViewById(R.id.PieChart);

        ArrayList<PieBeans> datas=new ArrayList<>();
        datas.add(new PieBeans(10,"java", Color.RED));
        datas.add(new PieBeans(20,"Android", Color.GREEN));
        datas.add(new PieBeans(30,"IOS", Color.BLUE));
        datas.add(new PieBeans(5,"C++", Color.CYAN));
        datas.add(new PieBeans(35,"C#", Color.DKGRAY));
        mPieChartView.setDatas(datas);

        mPieChartView.setOnPieItemClickListener(new PieChartView.OnPieItemClickListener() {
            @Override
            public void OnPieItemClickListener(int index, PieBeans pieBeans) {
                ToastUtils.showShort(pieBeans.getText()+"被点击");
            }
        });
    }
}
