package com.hanjinliang.androidstudy.jjzg.part3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/7/30 16:42
 */
public class MoveView1 extends View {

    public MoveView1(Context context) {
        super(context);
    }

    public MoveView1(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveView1(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    int lastX,lastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=x;
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX=x-lastX;
                int offsetY=y-lastY;
                //方法1 通过调用layout方法重新布局 达到滑动
                //layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);

                //方法2 通过对left四个方向设置偏移量
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

                //方法3  通过设置LayoutParams改变布局参数实现  父布局是LinearLayout
//                LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) getLayoutParams();
//                params.leftMargin=getLeft()+offsetX;
//                  params.topMargin=getTop()+offsetY;
//                setLayoutParams(params);

                //scrollTo 或者 scrollBy
                ((View)getParent()).scrollBy(-offsetX,-offsetY);
                //((View)getParent()).scrollTo(((View)getParent()).getScrollX()-offsetX,((View)getParent()).getScrollY()-offsetY);


                break;
        }
        return true;
    }
}
