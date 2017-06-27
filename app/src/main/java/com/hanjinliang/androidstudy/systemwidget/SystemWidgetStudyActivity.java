package com.hanjinliang.androidstudy.systemwidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.AppBarLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.ToolBarActivity;
import com.hanjinliang.androidstudy.systemwidget.coordinatorlayout.CoordinatorLayoutActivity;

/**
 * 系统组件
 */
public class SystemWidgetStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_widget_study);
    }

    /**
     * CoordinatorLayout学习
     * @param view
     */
    public void CoordinatorLayout(View view){
        startActivity(new Intent(this, CoordinatorLayoutActivity.class));
    }

    /**
     * Toolbar学习
     * @param view
     */
    public void Toolbar(View view){
        startActivity(new Intent(this, ToolBarActivity.class));
    }

    /**
     * AppBarLayout学习
     * @param view
     */
    public void AppBarLayout(View view){
        startActivity(new Intent(this, AppBarLayoutActivity.class));
    }

}
