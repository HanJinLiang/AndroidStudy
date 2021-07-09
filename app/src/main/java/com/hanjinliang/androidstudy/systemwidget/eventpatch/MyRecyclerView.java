package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.LogUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by HanJinLiang on 2017-11-24.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    float mDownX ;
    float mOffsetX ;
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {//内部拦截解决滑动冲突
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ACTION_DOWN");
                mDownX=e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE");
                mOffsetX=e.getX()-mDownX;
                if(Math.abs(mOffsetX) > 100){//  水平方向大于 100
                    //不同意父容器拦截  自己处理事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
               // getParent().requestDisallowInterceptTouchEvent(false);
                LogUtils.e("ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(e);
    }
}
