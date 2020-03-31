package com.hanjinliang.androidstudy.customerviews.favorlayout;

import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import me.yifeiyuan.library.PeriscopeLayout;

/**
 * 点赞布局
 * http://www.jianshu.com/p/03fdcfd3ae9c
 */
public class FavorLayoutActivity extends BaseActivity {
    PeriscopeLayout mPeriscopeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_layout);
        mPeriscopeLayout=findView(R.id.periscope);
    }

    public void addFavor(View view){
        mPeriscopeLayout.addHeart();
    }
}
