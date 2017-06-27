package com.hanjinliang.androidstudy.customerviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.CustomerProgressBar.CustomerProgressBarActivity;
import com.hanjinliang.androidstudy.customerviews.horizontalselectedview.HorizontalSelectedViewActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.AppBarLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.ToolBarActivity;
import com.hanjinliang.androidstudy.systemwidget.coordinatorlayout.CoordinatorLayoutActivity;

/**
 * 自定义view学习
 */
public class CustomerViewStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerview_study);
    }

    /**
     * HorizontalSelectedView学习
     * @param view
     */
    public void HorizontalSelectedView(View view){
        startActivity(new Intent(this, HorizontalSelectedViewActivity.class));
    }

    /**
     * CustomerProgressBar学习
     * @param view
     */
    public void CustomerProgressBar(View view){
        startActivity(new Intent(this, CustomerProgressBarActivity.class));
    }


}
