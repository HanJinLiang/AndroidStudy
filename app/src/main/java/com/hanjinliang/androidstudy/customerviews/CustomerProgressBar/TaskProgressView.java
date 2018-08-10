package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${lwh} on 2018/3/1.
 *
 * @descirbe 进度条view
 */

public class TaskProgressView extends View {
    private Paint mProgressPaint;
    private int mPercent=100;
    private int mProgressHeight;//进度条高度

    private float mProgressStopX;//进度条结束
    public TaskProgressView(Context context) {
        this(context,null);
    }

    public TaskProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TaskProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mProgressHeight=dip2px(getContext(),20);//进度条高度   单位dp

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressHeight);
        mProgressPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mProgressStopX = getWidth()-mProgressHeight / 2;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        mProgressPaint.setStrokeCap(Paint.Cap.BUTT);
        mProgressPaint.setColor(Color.parseColor("#E6E6E6"));
        canvas.drawLine(0, 0, getWidth(), 0, mProgressPaint);
        if(mPercent>0) {
            mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
            mProgressPaint.setColor(Color.parseColor("#788321"));
            canvas.drawLine(mProgressHeight / 2, 0, mProgressStopX * mPercent / 100, 0, mProgressPaint);
        }
        mProgressPaint.setXfermode(null);

    }


    public void setPercent(int mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px(  Context context,float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
