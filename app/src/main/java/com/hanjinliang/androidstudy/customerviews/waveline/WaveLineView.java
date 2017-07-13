package com.hanjinliang.androidstudy.customerviews.waveline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by HanJinLiang on 2017-07-13.
 * 波浪线
 * http://blog.csdn.net/harvic880925/article/details/50995587
 */

public class WaveLineView extends View {
    private int mWidth,mHeight;
    Paint mPaint;
    Path mPath;


    public WaveLineView(Context context) {
        this(context,null);
    }

    public WaveLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0x880000aa);
        mPaint.setStyle(Paint.Style.FILL);

        mPath=new Path();
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mWavelength=w;
        startAnim();
    }
    private int mWavelength;//一个波长的宽度
    private int mWaveHeight=50;//波浪线高度
    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        //移动到波浪线起点  左右两边分别所处一个波长  用于动画移动
        mPath.moveTo(-mWavelength+translationOffset,(1-mTempProgress/Max)*mHeight);
        //循环的画出个波长
        for(int i=-mWavelength;i<mWidth+mWavelength;i+=mWavelength){
            mPath.rQuadTo(mWavelength/4,mWaveHeight,mWavelength/2,0);
            mPath.rQuadTo(mWavelength/4,-mWaveHeight,mWavelength/2,0);
        }
        //连接到屏幕底部
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.close();

        canvas.drawPath(mPath,mPaint);

        //画两条曲线 波浪交替效果
        mPath.reset();
        //移动到波浪线起点  左右两边分别所处一个波长  用于动画移动
        mPath.moveTo(-mWavelength+translationOffset,(1-mTempProgress/Max)*mHeight);
        //循环的画出个波长
        for(int i=-mWavelength;i<mWidth+mWavelength;i+=mWavelength){
            mPath.rQuadTo(mWavelength/4,-mWaveHeight,mWavelength/2,0);
            mPath.rQuadTo(mWavelength/4,+mWaveHeight,mWavelength/2,0);
        }
        //连接到屏幕底部
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.close();

        canvas.drawPath(mPath,mPaint);

    }
    private int translationOffset=0;
    ValueAnimator mValueAnimator;
    private void startAnim(){
        //动画移动偏移量
        mValueAnimator=ValueAnimator.ofInt(0,mWavelength).setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translationOffset= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mValueAnimator!=null){
            mValueAnimator.cancel();
            mValueAnimator=null;
        }
    }
    private float Max=100;//最大进度
    int mProgress=0;//设置的进度
    int mTempProgress;//动画用临时进度

    /**
     * 设置当前进度
     * @param progress
     */
    public void setProgress(int progress){
        if(mProgressAnim!=null){
            mProgressAnim.cancel();
            mProgressAnim=null;
        }
        mProgress=progress;
        progressAnim();
    }
    ValueAnimator mProgressAnim;
    private void progressAnim() {
        mProgressAnim=ValueAnimator.ofInt(1,mProgress).setDuration(2000);
        mProgressAnim.setInterpolator(new LinearInterpolator());
        mProgressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTempProgress= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mProgressAnim.start();
    }


}
