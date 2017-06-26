package com.hanjinliangi.androidstudy.systemwidget.appbarlayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;
import com.hanjinliangi.androidstudy.Common.BaseActivity;
import com.hanjinliangi.androidstudy.R;

/**
 * AppBarLayoutActivity学习
 * 链接http://www.jianshu.com/p/d159f0176576
 */
public class AppBarLayoutActivity extends BaseActivity {
    Toolbar mToolbar;
    AppBarLayout mAppBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);

        mToolbar= findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAppBarLayout= findView(R.id.AppBarLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset==0){//展开
                    setToolbarBg(0);
                }else if(Math.abs(verticalOffset) >=appBarLayout.getTotalScrollRange()){//关闭
                    setToolbarBg(255);
                }else{
                   int alpha= (int)((Math.abs(verticalOffset)*1.0f)/appBarLayout.getTotalScrollRange()*255);
                    setToolbarBg(alpha);
                }
            }
        });
    }

    /**
     * 设置toolBar背景透明度
     * @param alpha
     */
    private void setToolbarBg(int alpha){
        mToolbar.setBackgroundDrawable(new ColorDrawable(Color.argb(alpha,63,81,181)));
    }
}
