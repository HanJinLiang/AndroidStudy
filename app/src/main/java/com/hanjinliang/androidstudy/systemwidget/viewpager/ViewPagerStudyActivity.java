package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;

public class ViewPagerStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_study);
    }

    public void ViewPageBase(View view){
        startActivity(new Intent(this,ViewPagerBaseActivity.class));
    }

    public void ViewPageFragment(View view){
        startActivity(new Intent(this,ViewPagerFragmentActivity.class));
    }


}
