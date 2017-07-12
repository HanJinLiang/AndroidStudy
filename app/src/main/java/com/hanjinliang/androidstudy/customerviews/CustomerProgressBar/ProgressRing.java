package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HanJinLiang on 2017-07-12.
 */

public class ProgressRing extends View {
    private int mWidth,mHeight;
    private int mRadius;
    private Paint mPaint;
    private Paint mBgPaint;

    public ProgressRing(Context context) {
        this(context,null);
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStrokeWidth(10);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(Color.GRAY);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mRadius=Math.min(mWidth,mHeight)/2-5;

        mPaint.setShader(new SweepGradient(0,0,new int[]{Color.YELLOW,Color.RED},new float[]{0f,0.5f}));
    }
    private int offset=0;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(135,0,0);
        //canvas.drawArc(new RectF(-mRadius,-mRadius,mRadius,mRadius),0,270,false,mBgPaint);
        canvas.drawArc(new RectF(-mRadius,-mRadius,mRadius,mRadius),0,360,false,mPaint);
//        if(offset<=270) {
//            offset++;
//            canvas.drawArc(new RectF(-mRadius, -mRadius, mRadius, mRadius), 5, offset, false, mPaint);
//            invalidate();
//        }else{
//            offset=0;
//            invalidate();
//        }
    }
}
