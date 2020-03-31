package com.hanjinliang.androidstudy.customerviews.StatusLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;


/**
 * Created by monkey on 2019-07-16 16:18.
 * Describe:
 */

public class StatusLayout extends LinearLayout {
    private int mInnerBgColor;
    private int mShadowColor;
    private Paint mInnerBgPaint;
    private Paint mStrokePaint;
    private Paint mShadowPaint;
    private int mStrokeWidth;
    private int mBgRound;
    private int mShadowWidth;

    private boolean isShowStroke;
    public StatusLayout(Context context) {
        this(context,null);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速

        mInnerBgColor= Color.parseColor("#2A2A46");
        mShadowColor=Color.WHITE;
        mBgRound=dp2px(5);

        mStrokeWidth=  dp2px(1);
        mShadowWidth=dp2px(5);


        mInnerBgPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerBgPaint.setStyle(Paint.Style.FILL);

        mStrokePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(dp2px(1));

        mShadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(mShadowWidth, BlurMaskFilter.Blur.OUTER));
        init();
    }

    private void init() {
        mInnerBgPaint.setColor(mInnerBgColor);
        mStrokePaint.setColor(mShadowColor);
        mShadowPaint.setColor(mShadowColor);
    }


    @Override
    public void dispatchDraw(Canvas canvas) {
        if(isShowStroke) {
            //先绘制带有阴影的
            canvas.drawRoundRect(new RectF(mShadowWidth, mShadowWidth, getWidth() - mShadowWidth, getHeight() - mShadowWidth), mBgRound, mBgRound, mShadowPaint);
            //绘制边框线
            canvas.drawRoundRect(new RectF(mShadowWidth, mShadowWidth, getWidth() - mShadowWidth, getHeight() - mShadowWidth), mBgRound, mBgRound, mStrokePaint);
        }
        //后绘制中间背景色
        canvas.drawRoundRect(new RectF(mShadowWidth+mStrokeWidth,mShadowWidth+mStrokeWidth,getWidth()-mShadowWidth-mStrokeWidth,getHeight()-mShadowWidth-mStrokeWidth),mBgRound,mBgRound, mInnerBgPaint);
        super.dispatchDraw(canvas);
    }


    private  int dp2px(float dpValue) {
        final float scale =getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //--------------------------------------

    public void setInnerBgColor(@ColorInt int color){
        mInnerBgColor=color;
        init();
        invalidate();
    }

    ValueAnimator mValueAnimator;
    public void setShowStroke(boolean showStroke) {
         setShowStroke(showStroke,500);
    }

    public void setShowStroke(boolean showStroke,int animTime) {
        isShowStroke = showStroke;
        int startAlpha,endAlpha;
        if(isShowStroke) {
            startAlpha=0;
            endAlpha=255;

        }else {
            startAlpha=255;
            endAlpha=0;
        }
        mValueAnimator=ValueAnimator.ofInt(startAlpha, endAlpha).setDuration(animTime);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                mStrokePaint.setAlpha(alpha);
                mShadowPaint.setAlpha(alpha);
                invalidate();
            }
        });
        mValueAnimator.start();
    }

}
