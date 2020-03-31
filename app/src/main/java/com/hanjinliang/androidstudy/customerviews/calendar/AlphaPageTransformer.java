package com.hanjinliang.androidstudy.customerviews.calendar;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by monkey on 2019-03-25 13:07.
 * Describe:透明度渐变
 */

public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_ALPHA = 0.3f;


    public void transformPage(View view, float position) {
        if (position < -1) { // [-Infinity,-1)  移到最左边
            // This page is way off-screen to the left.
            view.setAlpha(MIN_ALPHA);
        } else if (position <= 0) { // [-1,0] 移出   Position变化 0--->-1     动画值 1---->min
            // Use the default slide transition when moving to the left page
            view.setAlpha(position*(1-MIN_ALPHA)+1);
        } else if (position <= 1) { // (0,1]  移进  Position变化  1------>0   动画值是 min--->1
            view.setAlpha((1-position)*(1-MIN_ALPHA)+MIN_ALPHA);
        } else { // (1,+Infinity] 移到最右边
            // This page is way off-screen to the right.
            view.setAlpha(MIN_ALPHA);
        }
    }

}