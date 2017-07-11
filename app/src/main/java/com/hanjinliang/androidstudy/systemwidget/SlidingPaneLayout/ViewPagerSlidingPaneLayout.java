package com.hanjinliang.androidstudy.systemwidget.SlidingPaneLayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-11.
 * 解决的ViewPager滑动冲突
 *
 */

public class ViewPagerSlidingPaneLayout extends SlidingPaneLayout {
//    public ViewPagerSlidingPaneLayout(Context context) {
//        this(context,null);
//    }
//
//    public ViewPagerSlidingPaneLayout(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public ViewPagerSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(ev.getY()< mViewPager.getHeight()&&ev.getX()>mViewPager.getWidth()/10&&mViewPager.getCurrentItem()!=0){//滑动范围是ViewPager并且不是第一页  不拦截事件交给子视图解决
//            return false;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//     ViewPager mViewPager;
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        mViewPager= (ViewPager) findViewById(R.id.viewpager);
//    }

    //下面一种方式更合理

    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mEdgeSlop;

    public ViewPagerSlidingPaneLayout(Context context) {
        this(context, null);
    }

    public ViewPagerSlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                // The user should always be able to "close" the pane, so we only check
                // for child scrollability if the pane is currently closed.
                if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                        Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

                    // How do we set super.mIsUnableToDrag = true?

                    // send the parent a cancel event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    boolean intercept= super.onInterceptTouchEvent(cancelEvent);
                    LogUtils.e("intercept=="+intercept);
                    return intercept;
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
