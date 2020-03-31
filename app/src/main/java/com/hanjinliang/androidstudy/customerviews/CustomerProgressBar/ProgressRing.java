package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HanJinLiang on 2017-07-12.
 * 参考链接
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
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStrokeWidth(20);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setShader(new SweepGradient(0,0,new int[]{Color.parseColor("#50afafaf"),Color.parseColor("#ffafafaf"),Color.parseColor("#50afafaf")},new float[]{0f,0.375f,0.75f}));
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeJoin(Paint.Join.ROUND);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mRadius=Math.min(mWidth,mHeight)/2-10;

    }
    private int offset=0;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.translate(mWidth/2,mHeight/2);
        //SweepGradient颜色渐变是从0度开始  所以需要旋转画布
        canvas.rotate(135,0,0);
        canvas.drawArc(new RectF(-mRadius,-mRadius,mRadius,mRadius),0,270,false,mBgPaint);
        if(offset<=maxAngle) {
            offset++;
            //[startColor, endColor]
            //[0, maxAngle / 360]
            mPaint.setShader(new SweepGradient(0,0,new int[]{Color.parseColor("#10fdb54f"),Color.parseColor("#fffdb54f"),Color.TRANSPARENT},new float[]{0f,offset/360f,1}));
            canvas.drawArc(new RectF(-mRadius, -mRadius, mRadius, mRadius), 0, offset, false, mPaint);
            invalidate();
        }else{
            //[startColor, endColor]
            //[0, maxAngle / 360]
            mPaint.setShader(new SweepGradient(0,0,new int[]{Color.parseColor("#10fdb54f"),Color.parseColor("#fffdb54f"),Color.TRANSPARENT},new float[]{0f,offset/360f,1}));
            canvas.drawArc(new RectF(-mRadius, -mRadius, mRadius, mRadius), 0, offset, false, mPaint);
            offset=0;
        }
    }

    /**
     * 最大角度
     */
    private int maxAngle=200;
}
