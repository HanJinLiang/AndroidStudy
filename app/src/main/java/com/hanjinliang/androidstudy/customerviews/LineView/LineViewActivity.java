package com.hanjinliang.androidstudy.customerviews.LineView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.LineView.simple.SimpleLineData;
import com.hanjinliang.androidstudy.customerviews.LineView.simple.SimpleLineView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LineViewActivity extends AppCompatActivity {
    LineView mLineView;

    SimpleLineView simpleLineView;

    private ArrayList<WeightData> mData=new ArrayList<WeightData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_view);
        mLineView= (LineView) findViewById(R.id.mLineView);
        simpleLineView=findViewById(R.id.simpleLineView);
        initSimpleLineView();
        for(int i=0;i<1000;i++){
            mData.add(new WeightData("12:00", (float) (Math.random()*100)));
        }
        mLineView.setData(mData);

        mLineView.setOnPointClickListener(new LineView.OnLineViewListener() {
            @Override
            public void onPointClickListener(int index) {
                Toast.makeText(getApplicationContext(),"点击"+index,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrollMaxRight() {
                Toast.makeText(getApplicationContext(),"滑动到最右边",Toast.LENGTH_SHORT).show();
                mLineView.addMoreData(mData);
            }

            @Override
            public void onScrollMaxLeft() {
                Toast.makeText(getApplicationContext(),"滑动到最左边",Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineView.removeData(mLineView.getSelectedPoint());
            }
        });
    }

    private void initSimpleLineView() {
        ArrayList<SimpleLineData> datas=new ArrayList<>();
        for(int i=1;i<13;i++){
            datas.add(new SimpleLineData("第"+i+"期",8+i*0.1f,i==1||i==6||i==12));
        }
        simpleLineView.setData(datas);
    }

}
