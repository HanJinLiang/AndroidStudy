package com.hanjinliang.androidstudy.customerviews.basestudy.ViewDragHelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-10.
 * ViewDragHelper学习
 */

public class VDHLinearLayout extends LinearLayout {
    ScrollView topView;
    Button dragBtn;
    ScrollView bottomView;

    int dragBtnHeight;

    public VDHLinearLayout(Context context) {
        super(context);
        init();
    }

    public VDHLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VDHLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    ViewDragHelper dragHelper;
    final int MIN_TOP = 300; // 距离顶部最小的距离
    void init()
    {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == dragBtn||child == bottomView; // 只处理dragBtn
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - dragBtnHeight) // 底部边界
                {
                    top = getHeight() - dragBtnHeight;
                }
                else if (top < MIN_TOP) // 顶部边界
                {
                    top = MIN_TOP;
                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                // 改变底部区域高度
                LinearLayout.LayoutParams bottomViewLayoutParams = (LinearLayout.LayoutParams)bottomView.getLayoutParams();
                bottomViewLayoutParams.height = bottomViewLayoutParams.height + dy * -1;
                bottomView.setLayoutParams(bottomViewLayoutParams);

                // 改变顶部区域高度
                LinearLayout.LayoutParams topViewLayoutParams = (LinearLayout.LayoutParams)topView.getLayoutParams();
                topViewLayoutParams.height = topViewLayoutParams.height + dy;
                topView.setLayoutParams(topViewLayoutParams);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        topView = (ScrollView)findViewById(R.id.topView);
        dragBtn = (Button)findViewById(R.id.dragBtn);
        bottomView = (ScrollView)findViewById(R.id.bottomView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dragBtnHeight = dragBtn.getMeasuredHeight();
    }
}