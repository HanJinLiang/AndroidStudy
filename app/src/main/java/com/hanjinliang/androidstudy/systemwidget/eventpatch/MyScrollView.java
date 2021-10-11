package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/10/11 10:28
 */
public class MyScrollView extends ScrollView {
    ListView listView;
    private float mLastY;
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                listView = (ListView) ((ViewGroup)getChildAt(0)).getChildAt(1);
                //ListView滑动到顶部，且继续下滑，让scrollView拦截事件
                if (listView.getFirstVisiblePosition() == 0 && (ev.getY() - mLastY) > 0) {
                    //scrollView拦截事件
                    intercept = true;
                }
                //listView滑动到底部，如果继续上滑，就让scrollView拦截事件
                else if (listView.getLastVisiblePosition() ==listView.getCount() - 1 && (ev.getY() - mLastY) < 0) {
                    //scrollView拦截事件
                    intercept = true;
                }else {
                    //不允许scrollView拦截事件
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastY = ev.getY();
        return intercept;
    }
}
