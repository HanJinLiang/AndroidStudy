package com.hanjinliang.androidstudy.customerviews.wheelpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.util.Arrays;

public class WheelPickerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_picker_demo);
        final WheelPicker wheelPicker=findViewById(R.id.WheelPicker);

        wheelPicker.setDataList(Arrays.asList("张一","张二","张三","张四","张五","张六","张七","张八","张九"));
        wheelPicker.setCyclic(true);
        wheelPicker.setShowCurtainBorder(false);
        wheelPicker.setIndicatorText("名字");
        wheelPicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                wheelPicker.setCurrentPosition(0);

            }
        },1000);
        wheelPicker.setTextGradual(true);


        final MyWheelPicker myWheelPicker=findViewById(R.id.MyWheelPicker);

        myWheelPicker.setDataList(Arrays.asList("张一","张二","张三","张四","张五","张六","张七","张八","张九"));
        myWheelPicker.setIndicatorText("名字");
        myWheelPicker.setCyclic(false);
        myWheelPicker.setOnItemSelectedListener(new MyWheelPicker.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String data) {
                LogUtils.e("position="+position+"------data="+data);
            }
        });

        myWheelPicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                myWheelPicker.setCurrentPosition(10);
            }
        },2000);
    }
}
