package com.hanjinliang.androidstudy.customerviews.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

public class CalendarDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_demo);
        new SelectDateDialog(this).show();
    }
}
