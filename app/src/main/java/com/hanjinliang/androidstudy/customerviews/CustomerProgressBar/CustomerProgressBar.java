package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-06-27.
 */

public class CustomerProgressBar extends View {
    private int mFirstColor;
    private int mSecondColor;
    private float mProgressWidth;
    private long mSpeed;
    private int mProgress=0;

    Paint mPaint;
    public CustomerProgressBar(Context context) {
        this(context,null);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.CustomerProgressBar,defStyleAttr,0);
        mFirstColor=ta.getColor(R.styleable.CustomerProgressBar_FirstColor, Color.BLUE);
        mSecondColor=ta.getColor(R.styleable.CustomerProgressBar_FirstColor, Color.GRAY);
        mProgressWidth=ta.getDimension(R.styleable.CustomerProgressBar_ProgressWidth,10);
        mSpeed=ta.getInt(R.styleable.CustomerProgressBar_Speed,5);
        ta.recycle();

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        mPaint.setStrokeWidth(mProgressWidth);

        startProgress();
    }
    int centre;
    int radius;
    RectF oval;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centre= getWidth() / 2; // 获取圆心的x坐标
        radius= centre - (int)mProgressWidth / 2;// 半径
        mPaint.setStrokeWidth(mProgressWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
         oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
    }

    public boolean isNext;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isNext) {
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }else{
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }
    }
    private boolean isStop=true;
    public void startProgress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isStop) {
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgress++;
                    if (mProgress == 360)
                    {
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                }
            }
        }).start();
    }
}
