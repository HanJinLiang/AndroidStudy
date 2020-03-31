package com.hanjinliang.androidstudy.customerviews.timeselect;

import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class DateTimePickerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker_demo);
    }

    public void doubleDate(View view){
         new DateTimePickerTools(this, "", new DateTimePickerTools.DoubleDateTimePickerListener() {
            @Override
            public void doubleDateTimePicker(int fyear, int fmonth, int fday, int firstHour, int firstMin, int syear, int smonth, int sfday, int secondHour, int secondMin) {

            }
        });
    }
}
