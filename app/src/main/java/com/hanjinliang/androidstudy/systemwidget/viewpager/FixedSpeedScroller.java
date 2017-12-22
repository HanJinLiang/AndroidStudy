package com.hanjinliang.androidstudy.systemwidget.viewpager;


import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by HanJinLiang on 2017-12-22.
 * ViewPager滑动时间设置
 */
public class FixedSpeedScroller extends Scroller {

    private int mAnimDuration = 1000;//滑动时间

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mAnimDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mAnimDuration);
    }

    public int getAnimDuration() {
        return mAnimDuration;
    }

    public void setAnimDuration(int animDuration) {
        mAnimDuration = animDuration;
    }
}
