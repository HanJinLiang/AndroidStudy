package com.hanjinliang.androidstudy.customerviews.WeightView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by HanJinLiang on 2017-08-01.
 * 称重ProgressView
 */
public class WeightProgressView extends View {
    private int mRadius;


    Paint mPaint;
    Paint mArcPaint;
    private RectF mRectF;

    private int mArcOffset=0;
    private int mStrokeWidth=dip2px(2);//线的宽度
    private int mColor=Color.GREEN;//颜色
    private int mCircleSpeed=2000;//转一圈时间
    public WeightProgressView(Context context) {
        this(context,null);
    }

    public WeightProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeightProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        //这里更改虚线实线比例  20实线 10虚线
        mPaint.setPathEffect(new DashPathEffect(new float[] {20, 10}, 0));
        mPaint.setColor(mColor);

        mArcPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width;
        if(widthMode==MeasureSpec.EXACTLY){//MatchParent
            width=widthSize;
        }else{
            width=dip2px(200);//默认值
        }

        int height;
        //如果是放在相对布局  就是给的是200dp 走的都是AT_MOST  而且会多次调用onMeasure 参数不一样
        if(heightMode==MeasureSpec.EXACTLY||heightMode==MeasureSpec.AT_MOST){//MatchParent
            height=heightSize;
        }else{
            height=dip2px(200);//默认值
        }

        int finalWidth=Math.min(width,height);
        setMeasuredDimension(finalWidth,finalWidth);//为保证是圆形的   取最小值
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius=w/2;
        mRectF=new RectF(mStrokeWidth/2,mStrokeWidth/2,w-mStrokeWidth/2,h-mStrokeWidth/2);
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
         canvas.drawCircle(mRadius,mRadius,mRadius-mStrokeWidth/2,mPaint);
        if(mStatus==Status.ROATING) {
            canvas.drawArc(mRectF, -90 + mArcOffset, 90, false, mArcPaint);
        }
    }


    /**
     * 状态
     */
    public enum Status{
        ROATING,STOP
    }

    Status mStatus=Status.STOP;//默认是静止的

    ValueAnimator mValueAnimator;

    /**
     * 获取当前的状态
     * @return
     */
    public Status getStatus() {
        return mStatus;
    }

    /**
     * 还是旋转
     */
    public void startRotate(){
        if(mStatus==Status.STOP){
            mStatus=Status.ROATING;

            mValueAnimator=ValueAnimator.ofInt(0,360);
            mValueAnimator.setDuration(mCircleSpeed);
            mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mArcOffset= (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mValueAnimator.start();
        }
    }

    /**
     * 结束旋转
     */
    public void stopRotate(){
        if(mStatus==Status.ROATING){
            if(mValueAnimator!=null){
                mValueAnimator.cancel();
                mValueAnimator=null;
                mArcOffset= 0;
                postInvalidate();
                mStatus=Status.STOP;
            }
        }
    }

}
