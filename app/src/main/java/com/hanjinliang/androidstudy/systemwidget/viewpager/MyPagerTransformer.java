package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by HanJinLiang on 2017-12-22.
 * 自定义 动画效果
 */

public class MyPagerTransformer implements ViewPager.PageTransformer{
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;


    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)  移到最左边
            // This page is way off-screen to the left.
            view.setAlpha(MIN_ALPHA);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 0) { // [-1,0] 移出   Position变化 0--->-1     动画值 1---->min
            // Use the default slide transition when moving to the left page
            view.setAlpha(position*(1-MIN_ALPHA)+1);

            view.setScaleY(position*(1-MIN_SCALE)+1);

        } else if (position <= 1) { // (0,1]  移进  Position变化  1------>0   动画值是 min--->1
            view.setAlpha((1-position)*(1-MIN_ALPHA)+MIN_ALPHA);

            view.setScaleY((1-position)*(1-MIN_SCALE)+MIN_SCALE);

        } else { // (1,+Infinity] 移到最右边
            // This page is way off-screen to the right.
            view.setAlpha(MIN_ALPHA);
            view.setScaleY(MIN_SCALE);
        }
    }

}
