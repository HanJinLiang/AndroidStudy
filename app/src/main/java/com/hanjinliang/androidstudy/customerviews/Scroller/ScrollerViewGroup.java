package com.hanjinliang.androidstudy.customerviews.Scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by HanJinLiang on 2018-03-28.
 */

public class ScrollerViewGroup extends ViewGroup {
    /**
     * 是否是手动停止滚动
     */
    private boolean mIsAbortScroller;

    public ScrollerViewGroup(Context context) {
        this(context,null);
    }

    public ScrollerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scroller=new Scroller(context);
    }
    int totalWidth;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        totalWidth=0;
        int childCount=getChildCount();
        for(int i=0;i<childCount;i++){
            View childView =getChildAt(i);
            if(childView.getVisibility()!=GONE){//可见的
               int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                childView.layout(totalWidth,0,totalWidth+childWidth,childHeight);
                totalWidth+=childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
    }
    VelocityTracker mVelocityTracker;
    float mLastDownX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocityTracker==null){
            mVelocityTracker=VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                    mIsAbortScroller=true;
                }else{
                    mIsAbortScroller=false;
                }
                mLastDownX=event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float mMoveX=event.getX()-mLastDownX;
                if(mMoveX>0){//又滑  判断左边界是不是超过边界
                    if(-mMoveX+getScrollX()<0) {
                        mMoveX = getScrollX();
                    }
                }else{//左滑
                    if(-mMoveX+getScrollX()+getWidth()>totalWidth) {
                        mMoveX = -(totalWidth-getWidth()-getScrollX());
                    }
                }
                scrollBy((int) -mMoveX,0);
                mLastDownX=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000 );
                int velocityX= (int) mVelocityTracker.getXVelocity();
                LogUtils.e("velocityX="+velocityX+"--getScrollX()="+getScrollX());
                if(Math.abs(velocityX)>SNAP_VELOCITX ){
                    scroller.fling(getScrollX(),0,-velocityX,0,0,totalWidth-getWidth(),0,0);
                }
                postInvalidate();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        super.computeScroll();

        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
        }

    }

    int SNAP_VELOCITX = 600; //滑动的最小速率
    Scroller scroller;
}
