package com.hanjinliang.androidstudy.jjzg.part3;

import android.content.Context;
import android.icu.text.DisplayContext;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.blankj.utilcode.util.LogUtils;

/**
 * 自定义viewGroup
 * 可横向滑动的viewGroup，类似viewPager
 */
public class HorizontalView extends ViewGroup {

    private static final String TAG = "hfy:HorizontalView";

    /**
     * 滑动辅助对象
     */
    private Scroller mScroller;

    /**
     * 速度追踪器
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 记录父view在拦截之前的x、y
     */
    private int mLastXIntercept;
    private int mLastYIntercept;

    /**
     * 记录 父view 拦截时及之后的 开始的触摸x、y
     */
    private int mLastX;
    private int mLastY;

    /**
     * 当前的展示的子view下标
     */
    private int index;

    /**
     * 触摸横向移动距离
     */
    private int offsetXTotal;

    /**
     * 滑动小于 子view 宽度的一半
     */
    private boolean isScrollLessHalfWidth;

    public HorizontalView(Context context) {
        super(context);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //处理AT_MOST，宽：子view的宽和，高：第一个view的高
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int childCount = getChildCount();
            View firstChild = getChildAt(0);
            setMeasuredDimension(childCount * firstChild.getMeasuredWidth(), firstChild.getMeasuredHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int childCount = getChildCount();
            View firstChild = getChildAt(0);
            setMeasuredDimension(childCount * firstChild.getMeasuredWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            View firstChild = getChildAt(0);
            setMeasuredDimension(widthSize, firstChild.getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        View child;
        int left = 0;
        //子view的布局
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if ((child.getVisibility() != GONE)) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                child.layout(left, 0, left + width, height);
                left += width;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        //解决滑动冲突
        boolean isIntercept = false;

        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercept = x;
                mLastYIntercept = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //是横向的滑动
                boolean isHorizontal = Math.abs(x - mLastXIntercept) > Math.abs(y - mLastYIntercept);
                if (isHorizontal) {
                    //此处一旦拦截，后面的ACTION就不会在调用onInterceptTouchEvent
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        Log.i(TAG, "onInterceptTouchEvent: isIntercept：" + isIntercept);

        //此处要记录下 拦截时的 开始的 触摸点（仅用于下面onTouchEvent处理滑动使用）
        mLastX = mLastXIntercept;
        mLastY = mLastYIntercept;

        mLastXIntercept = x;
        mLastYIntercept = y;

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        int childMeasuredWidth = getChildAt(0).getMeasuredWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //因在onInterceptTouchEvent中ACTION_DOWN返回false，所以这里不会走到。
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸事件添加的速度追踪器，用于在UP时计算速度
                mVelocityTracker.addMovement(event);

                int offsetX = x - mLastX;
                offsetXTotal += offsetX;

                isScrollLessHalfWidth = Math.abs(offsetXTotal) < childMeasuredWidth / 2;
                //滑动于子view 宽度的一半
                if (isScrollLessHalfWidth) {
                    //使所有子view进行水平滑动
                    scrollBy(-offsetX, 0);
                } else {
                    //滑动大于子view 宽度的一半，就直接 自动地 平滑地  完整地 滑过去。
                    if (offsetXTotal > 0) {
                        //不是第一个，就右滑，这里用To，因为子view的左边缘要滑到屏幕右边，负值表示子view要右滑（要理解mScrollX）
                        if (index > 0) {
                            smoothScrollTo(childMeasuredWidth * (--index), 0);
                        }
                    } else if (index < (getChildCount() - 1)) {
                        //左滑
                        smoothScrollTo(childMeasuredWidth * (++index), 0);
                    }

                    offsetXTotal = 0;
                }
                break;
            case MotionEvent.ACTION_UP:

                //滑动没有子view宽度一半 或者 第一个view右滑、最后的view左滑，那么 手指抬起时就滑回原位
                if (offsetXTotal > 0 && index == 0
                        || offsetXTotal < 0 && index == (getChildCount() - 1)) {
                    smoothScrollTo(childMeasuredWidth * (index), 0);
                } else if (isScrollLessHalfWidth) {

                    mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = mVelocityTracker.getXVelocity();
                    Log.i(TAG, "onTouchEvent: xVelocity=" + xVelocity);

                    if (Math.abs(xVelocity) > 50) {
                        //不是  第一个view右滑、最后的view左滑，也没有滑过宽度一半，但 速度很快，也 完整地 自动滑动过去。
                        if (xVelocity > 0) {
                            if (index > 0) {
                                smoothScrollTo(childMeasuredWidth * (--index), 0);
                            }
                        } else {
                            if (index < (getChildCount() - 1)) {
                                smoothScrollTo(childMeasuredWidth * (++index), 0);
                            }
                        }
                    } else {
                        //滑动没有子view宽度一半，速度也不快，那么 手指抬起时 也 滑回原位
                        smoothScrollTo(childMeasuredWidth * (index), 0);
                    }

                }

                //抬起 把横向滑动距离 置为0
                offsetXTotal = 0;

                mVelocityTracker.clear();
                break;
        }

        //这里需要记录上次的move的位置，因为scrollBy使所有字view滑动，本身不动，MotionEvent是一直改变的。
        mLastX = x;
        mLastY = y;

        Log.i(TAG, "onTouchEvent: index=" + index);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 弹性滑动到某一位置
     *
     * @param destX 目标X
     * @param destY 目标Y
     */
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int offsetX = destX - scrollX;
        int offsetY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, offsetX, offsetY, 600);
        invalidate();
    }
}