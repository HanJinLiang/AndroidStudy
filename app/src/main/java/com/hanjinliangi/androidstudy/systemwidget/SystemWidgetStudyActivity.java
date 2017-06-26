package com.hanjinliangi.androidstudy.systemwidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hanjinliangi.androidstudy.R;
import com.hanjinliangi.androidstudy.systemwidget.appbarlayout.AppBarLayoutActivity;
import com.hanjinliangi.androidstudy.systemwidget.appbarlayout.ToolBarActivity;
import com.hanjinliangi.androidstudy.systemwidget.coordinatorlayout.CoordinatorLayoutActivity;

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
