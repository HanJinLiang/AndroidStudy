package com.hanjinliang.androidstudy.customerviews.StrokeRunBgLinearLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by monkey on 2019-08-21 11:38.
 * Describe:
 */

public class StrokeRunBgLinearLayout extends LinearLayout {
    private int mWidth;
    private int mHeight;
    private int mRadius;

    private Paint mBgPaint;
    private Paint mStrokePaint;
    private int mStrokeWidth;

    private Path mBgPath;
    private Path mStrokePath;

    private int mTotalLength;

    private int mCurrentLength;


    public StrokeRunBgLinearLayout(Context context) {
        this(context,null);
    }

    public StrokeRunBgLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StrokeRunBgLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速

        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.BLUE);
        mBgPaint.setStyle(Paint.Style.FILL);

        mStrokePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        //描边的宽度
        mStrokeWidth=SizeUtils.dp2px(2);
        mStrokePaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mRadius=(mHeight-mStrokeWidth)/2;
        mBgPath=new Path();
        mBgPath.moveTo(mRadius,0);
        mBgPath.lineTo(mWidth-mRadius,0);
        mBgPath.arcTo(new RectF(mWidth-mRadius*2,0,mWidth,mHeight),-90,180);
        mBgPath.lineTo(mRadius,mHeight);
        mBgPath.arcTo(new RectF(0,0,mRadius*2,mHeight),90,180);
        mBgPath.close();

        mStrokePath=new Path();

        //总长度
        mTotalLength= (int) ((mWidth-mRadius*2)*2+Math.PI*mRadius*2);
        mCurrentLength=0;
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawPath(mBgPath,mBgPaint);
        mStrokePath.reset();
        if(mCurrentLength<=Math.PI*mRadius*2/4){//小于右边下半圆弧
            mStrokePath.moveTo(mWidth-mStrokeWidth/2,mHeight/2);
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),0,getAngleByLength(mCurrentLength));
        }else if(mCurrentLength>Math.PI*mRadius*2/4&&mCurrentLength<=(Math.PI*mRadius*2/4+mWidth-mRadius*2)){//下直线
            mStrokePath.moveTo(mWidth-mStrokeWidth/2,mHeight/2);
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),0,90);
            float bottomLineLength = (float) (mCurrentLength - Math.PI * mRadius * 2 / 4);
            mStrokePath.lineTo(mWidth-mStrokeWidth/2-bottomLineLength-mRadius,mHeight-mStrokeWidth/2);
        }else if(mCurrentLength>(Math.PI*mRadius*2/4+mWidth-mRadius*2)&&mCurrentLength<=(Math.PI*mRadius*2*3/4+mWidth-mRadius*2)){//左边圆弧
            mStrokePath.moveTo(mWidth-mStrokeWidth/2,mHeight/2);
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),0,90);
            mStrokePath.lineTo(mRadius+mStrokeWidth/2,mHeight-mStrokeWidth/2);
            int leftAcrLength= (int) (mCurrentLength-(Math.PI*mRadius*2/4+mWidth-mRadius*2));
            mStrokePath.arcTo(new RectF(mStrokeWidth/2,mStrokeWidth/2,mRadius*2+mStrokeWidth/2,mHeight-mStrokeWidth/2),90,getAngleByLength(leftAcrLength));
        }else if(mCurrentLength>(Math.PI*mRadius*2*3/4+mWidth-mRadius*2)&&mCurrentLength<=(mTotalLength-Math.PI*mRadius*2/4)){//上直线
            mStrokePath.moveTo(mWidth-mStrokeWidth/2,mHeight/2);
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),0,90);
            mStrokePath.lineTo(mRadius+mStrokeWidth/2,mHeight-mStrokeWidth/2);
            mStrokePath.arcTo(new RectF(mStrokeWidth/2,mStrokeWidth/2,mRadius*2+mStrokeWidth/2,mHeight-mStrokeWidth/2),90,180);
            int topLineLength= (int) (mCurrentLength-(Math.PI*mRadius*2*3/4+mWidth-mRadius*2));
            mStrokePath.lineTo(mRadius+mStrokeWidth/2+topLineLength,mStrokeWidth/2);
        }else if(mCurrentLength>(mTotalLength-Math.PI*mRadius*2/4)){//右上弧
            mStrokePath.moveTo(mWidth-mStrokeWidth/2,mHeight/2);
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),0,90);
            mStrokePath.lineTo(mRadius+mStrokeWidth/2,mHeight-mStrokeWidth/2);
            mStrokePath.arcTo(new RectF(mStrokeWidth/2,mStrokeWidth/2,mRadius*2+mStrokeWidth/2,mHeight-mStrokeWidth/2),90,180);
            mStrokePath.lineTo(mWidth-mRadius-mStrokeWidth/2,mStrokeWidth/2);
            int rightAcrLength= (int) (mCurrentLength-(mTotalLength-Math.PI*mRadius*2/4));
            mStrokePath.arcTo(new RectF(mWidth-mRadius*2-mStrokeWidth/2,mStrokeWidth/2,mWidth-mStrokeWidth/2,mHeight-mStrokeWidth/2),-90,getAngleByLength(rightAcrLength));
        }

        canvas.drawPath(mStrokePath,mStrokePaint);
        super.dispatchDraw(canvas);
    }

    /**
     * 根据弧长算出角度
     * @param arcLength
     * @return
     */
    private float getAngleByLength(int arcLength) {
        return (float) (360*arcLength/(Math.PI*mRadius*2));
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            startRunAnim();
        }else {
            stopRunAnim();
        }
    }

    ValueAnimator mValueAnimator;
    public void startRunAnim(){
        mValueAnimator=ValueAnimator.ofInt(mTotalLength);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentLength= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.setDuration(3000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //目前是线性匀速
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.start();
    }

    public void stopRunAnim(){
        if(mValueAnimator!=null){
            mValueAnimator.cancel();
        }
    }
}
