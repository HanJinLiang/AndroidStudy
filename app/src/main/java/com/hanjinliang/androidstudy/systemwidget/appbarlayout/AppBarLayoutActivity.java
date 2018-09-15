package com.hanjinliang.androidstudy.systemwidget.appbarlayout;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

/**
 * AppBarLayoutActivity学习
 * 链接http://www.jianshu.com/p/d159f0176576
 */
public class AppBarLayoutActivity extends BaseActivity implements View.OnClickListener {
    Toolbar mToolbar;
    AppBarLayout mAppBarLayout;

    TextView leftLabel;
    TextView tv_title;
    TextView tv_yesterday_hint;
    TextView tv_yesterday;


    LinearLayout layout_title;
    LinearLayout layout_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);

        mToolbar= findView(R.id.toolbar);
        leftLabel=findView(R.id.leftLabel);
        layout_title=findView(R.id.layout_title);
        layout_total=findView(R.id.layout_total);
        tv_title=findView(R.id.tv_title);
        tv_yesterday_hint=findView(R.id.tv_yesterday_hint);
        tv_yesterday=findView(R.id.tv_yesterday);

        findView(R.id.tv_1).setOnClickListener(this);
        findView(R.id.tv_2).setOnClickListener(this);
        findView(R.id.tv_3).setOnClickListener(this);
        findView(R.id.tv_4).setOnClickListener(this);
        findView(R.id.tv_5).setOnClickListener(this);
        findView(R.id.tv_6).setOnClickListener(this);
        tv_title.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        mAppBarLayout= findView(R.id.AppBarLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
               // LogUtils.e("appBarLayout.getTotalScrollRange()=="+appBarLayout.getTotalScrollRange()+"-------verticalOffset=="+verticalOffset);
                if(verticalOffset==0){//展开

                }else if(Math.abs(verticalOffset) >=appBarLayout.getTotalScrollRange()){//关闭

                }
                float ratio = (Math.abs(verticalOffset) * 1.0f) / appBarLayout.getTotalScrollRange();

                LogUtils.e("ratio=="+ratio);
                leftLabel.setAlpha(ratio);
                layout_total.setAlpha(1-ratio);
                tv_yesterday_hint.setAlpha(1-ratio);

                //标题  中间-》右上角
                tv_title.setTranslationX(ratio*(ScreenUtils.getScreenWidth()/2- SizeUtils.dp2px(10)-tv_title.getWidth()/2));

                //今日  中间下-》左上角
                tv_yesterday.setTranslationX(-ratio*(ScreenUtils.getScreenWidth()/2-leftLabel.getWidth()-tv_yesterday.getWidth()/2));
                tv_yesterday.setTranslationY(-ratio*SizeUtils.dp2px(100));


                tv_yesterday_hint.setTranslationY(verticalOffset);
                layout_total.setTranslationY(verticalOffset * 1.2f);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                ToastUtils.showLong("tv_1");
                break;
            case R.id.tv_2:
                ToastUtils.showLong("tv_2");
                break;
            case R.id.tv_3:
                ToastUtils.showLong("tv_3");
                break;
            case R.id.tv_4:
                ToastUtils.showLong("tv_4");
                break;
            case R.id.tv_5:
                ToastUtils.showLong("tv_5");
                break;
            case R.id.tv_6:
                ToastUtils.showLong("tv_6");
                break;
            case R.id.tv_title:
                ToastUtils.showLong("tv_title");
                break;

        }
    }
}
