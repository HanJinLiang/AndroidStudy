package com.hanjinliang.androidstudy.systemwidget.appbarlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.SnackbarUtils;
import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-06-26.
 * Toolbar学习
 */

public class ToolBarActivity extends AppCompatActivity   {
    Toolbar mToolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_toolbar);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("标题");
        mToolbar.setSubtitle("二级子标题");
        mToolbar.setNavigationIcon(R.drawable.ic_menu_manage);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_more){
                    SnackbarUtils.with(mToolbar).setMessage("更多消息").show();
                }
                return true;
            }
        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }




}
