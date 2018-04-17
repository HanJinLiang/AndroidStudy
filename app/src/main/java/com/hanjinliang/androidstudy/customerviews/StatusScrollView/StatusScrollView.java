package com.hanjinliang.androidstudy.customerviews.StatusScrollView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;


import com.blankj.utilcode.util.LogUtils;

import static com.hanjinliang.androidstudy.customerviews.StatusScrollView.StatusScrollView.ScrollStatus.IDLE;
import static com.hanjinliang.androidstudy.customerviews.StatusScrollView.StatusScrollView.ScrollStatus.TOUCH_SCROLL;


/**
 * Created by HanJinLiang on 2018-04-16.
 */

public class StatusScrollView extends ScrollView {

    enum ScrollStatus{IDLE,TOUCH_SCROLL,FLING}

    private ScrollStatus mStatus=IDLE;



    public StatusScrollView(Context context) {
        this(context,null);
    }

    public StatusScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatusScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这个类移除就是有自带监听状态的ScrollView
        setOnScrollViewStatusChanged(new  OnScrollViewStatusChanged() {
            @Override
            public void onStatusChanged(ScrollView view, StatusScrollView.ScrollStatus newStatus, StatusScrollView.ScrollStatus oldStatus) {
                LogUtils.e(newStatus);
                if (newStatus == StatusScrollView.ScrollStatus.IDLE) {
                    ViewGroup scrollLayout=(ViewGroup)getChildAt(0);
                    if (getScrollY() >scrollLayout.getChildAt(scrollLayout.getChildCount()-1).getTop() - getMeasuredHeight()) {
                        smoothToPosition( getScrollY(), scrollLayout.getChildAt(scrollLayout.getChildCount()-1).getTop() - getMeasuredHeight());
                    }
                }else{
                    cancelAnim();
                }
            }

            ValueAnimator valueAnimator;

            /**
             * 平滑滚到到
             * @param start
             * @param end
             */
            private void smoothToPosition(int start,int end){
                cancelAnim();
                valueAnimator=ValueAnimator.ofInt(start,end);
                valueAnimator.setDuration(600);//动画时间
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());//插值器
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value= (int) animation.getAnimatedValue();
                        scrollTo(0,value);
                    }
                });
                valueAnimator.start();
            }

            private void cancelAnim(){
                if(valueAnimator!=null&&valueAnimator.isRunning()){
                    valueAnimator.cancel();
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                setStatus(TOUCH_SCROLL);
                mHandler.removeCallbacks(mRunnable);
                break;
            case MotionEvent.ACTION_UP:
                mHandler.post(mRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    Handler mHandler=new Handler(Looper.getMainLooper());
    /**
     * 滚动监听间隔
     */
    private int scrollDelay = 50;

    /**
     * 记录当前滚动的距离
     */
    private int currentY =Integer.MIN_VALUE;
    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if(getScrollY()==currentY){
                //滚动停止  取消监听线程
                setStatus(IDLE);
                mHandler.removeCallbacks(this);
                return;
            }else{
                //手指离开屏幕    view还在滚动的时候
                setStatus(ScrollStatus.FLING);
                mHandler.postDelayed(this, scrollDelay);
            }
            currentY = getScrollY();
        }
    };

    private void setStatus(ScrollStatus status){
        if(mStatus==status){
            return;
        }
        if(mOnScrollViewStatusChanged!=null){
            mOnScrollViewStatusChanged.onStatusChanged(this,status,mStatus);
        }
        mStatus=status;
    }
    OnScrollViewStatusChanged mOnScrollViewStatusChanged;

    public void setOnScrollViewStatusChanged(OnScrollViewStatusChanged onScrollViewStatusChanged) {
        mOnScrollViewStatusChanged = onScrollViewStatusChanged;
    }

    interface OnScrollViewStatusChanged{
        void onStatusChanged(ScrollView view,ScrollStatus newStatus,ScrollStatus oldStatus);
    }
}
