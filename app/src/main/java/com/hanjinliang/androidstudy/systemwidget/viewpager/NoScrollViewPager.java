package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by HanJinLiang on 2017-12-21.
 * 不可以滑动，但是可以setCurrentItem的ViewPager。
 *
 */

public class NoScrollViewPager extends ViewPager {


    public NoScrollViewPager(Context context) {
        this(context,null);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
