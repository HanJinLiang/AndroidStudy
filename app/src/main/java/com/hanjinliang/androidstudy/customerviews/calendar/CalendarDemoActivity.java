package com.hanjinliang.androidstudy.customerviews.calendar;

import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_demo);
        new SelectDateDialog(this).show();
    }
}
