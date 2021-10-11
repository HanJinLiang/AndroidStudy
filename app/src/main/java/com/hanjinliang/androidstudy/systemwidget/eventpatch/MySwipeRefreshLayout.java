package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/10/9 17:26
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private float startX;
    private float startY;
    private final float mTouchSlop;

    public MySwipeRefreshLayout(@NonNull @NotNull Context context) {
        this(context,null);
    }

    public MySwipeRefreshLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = Math.abs(ev.getX() - startX);
                float distanceY = Math.abs(ev.getY() - startY);
                //外部拦截法
                if (distanceX > mTouchSlop && distanceX > distanceY) {  //判断为横向滑动  不拦截事件
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
