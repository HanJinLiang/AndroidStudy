package com.hanjinliang.androidstudy.customerviews.LineView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LineViewActivity extends AppCompatActivity {
    LineView mLineView;
    LineView mLineView2;

    private ArrayList<WeightData> mData=new ArrayList<WeightData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_view);
        mLineView= (LineView) findViewById(R.id.mLineView);
        mLineView2= (LineView) findViewById(R.id.mLineView2);
        mData.add(new WeightData("12:00",60));
        mData.add(new WeightData("13:00",20));
        mData.add(new WeightData("14:00",20));
        mData.add(new WeightData("15:00",80));
        mData.add(new WeightData("16:00",20));
        mData.add(new WeightData("17:00",90));
        mLineView.setData(mData);

        mLineView.setOnPointClickListener(new LineView.OnPointClickListener() {
            @Override
            public void onPointClickListener(int index) {
                Toast.makeText(getApplicationContext(),"点击"+index,Toast.LENGTH_SHORT).show();
            }
        });
        final ArrayList<WeightData> mData2=new ArrayList<WeightData>();
        mData2.add(new WeightData("12:00",60));
        mData2.add(new WeightData("13:00",20));
        mData2.add(new WeightData("14:00",20));
        mData2.add(new WeightData("15:00",80));
        mData2.add(new WeightData("16:00",20));
        mData2.add(new WeightData("17:00",90));
        mData2.add(new WeightData("18:00",80));
        mData2.add(new WeightData("19:00",20));
        mData2.add(new WeightData("20:00",90));
        mData2.add(new WeightData("21:00",80));
        mData2.add(new WeightData("22:00",20));
        mData2.add(new WeightData("23:00",90));
        mLineView2.setData(mData2);

        mLineView2.setOnPointClickListener(new LineView.OnPointClickListener() {
            @Override
            public void onPointClickListener(int index) {
                Toast.makeText(getApplicationContext(),"点击"+index,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
