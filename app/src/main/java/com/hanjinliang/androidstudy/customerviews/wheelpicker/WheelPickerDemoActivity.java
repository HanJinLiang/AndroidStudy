package com.hanjinliang.androidstudy.customerviews.wheelpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import java.util.Arrays;

public class WheelPickerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_picker_demo);
        final WheelPicker wheelPicker=findViewById(R.id.WheelPicker);

        wheelPicker.setDataList(Arrays.asList("张一","张二","张三","张四","张五","张六","张七","张八","张九"));
        wheelPicker.setCyclic(false);
        wheelPicker.setShowCurtainBorder(false);
        wheelPicker.setIndicatorText("名字");
        wheelPicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                wheelPicker.setCurrentPosition(0);

            }
        },1000);
        wheelPicker.setTextGradual(true);


    }
}
