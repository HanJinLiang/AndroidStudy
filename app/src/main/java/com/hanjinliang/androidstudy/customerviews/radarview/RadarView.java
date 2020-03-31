package com.hanjinliang.androidstudy.customerviews.radarview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;


/**
 * Created by HanJinLiang on 2017-06-30.
 * 自定义  雷达扫描View
 * SweepGradient实现
 */

public class RadarView extends View {

    private int mStartColor=Color.TRANSPARENT;
    private int mEndColor=Color.RED;
    private int mWidth,mHeight;
    private Paint mPaint;
    private Paint mRadarPaint;

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(SizeUtils.dp2px(1));

        mRadarPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadarPaint.setStyle(Paint.Style.FILL);
        mRadarPaint.setColor(Color.GRAY);
        mRadarPaint.setStrokeWidth(SizeUtils.dp2px(1));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        //计算宽度
        if(widthMode==MeasureSpec.EXACTLY){//MatchParent
            mWidth=widthSize;
        }else{//默认高度
            mWidth=SizeUtils.dp2px(200);//默认宽度200dp
        }

        //计算高度
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            mHeight=heightSize;
        }else{//默认高度
            mHeight=SizeUtils.dp2px(50);//默认高度50dp
        }
        //高度大于宽度时   高度等于宽度
        if(mHeight>mWidth){
            mHeight=mWidth;
        }
        setMeasuredDimension(mWidth,mHeight);
    }
    private int mRadius;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRadius=getWidth()/2;
        mRadarPaint.setShader(new SweepGradient(mRadius,mRadius,mStartColor,mEndColor));
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆形
        canvas.drawPoint(mRadius,mRadius,mPaint);
        //画X坐标
        canvas.drawLine(0,mRadius,mWidth,mRadius,mPaint);
        //画Y坐标
        canvas.drawLine(mRadius,0,mRadius,mWidth,mPaint);

        //画圆形
        canvas.drawCircle(mRadius,mRadius,mRadius/5,mPaint);
        canvas.drawCircle(mRadius,mRadius,mRadius/2,mPaint);
        //需要减去画笔的一般宽度
        canvas.drawCircle(mRadius,mRadius,mRadius-SizeUtils.dp2px(0.5f),mPaint);

        canvas.save();
        //旋转画布后再去描绘颜色  达到动态扫描效果
        canvas.rotate(mDegrees,mRadius,mRadius);
        //画扫描前景
        canvas.drawCircle(mRadius,mRadius,mRadius-SizeUtils.dp2px(0.5f),mRadarPaint);
        canvas.restore();
    }

    public void startScan(){
        if(mUpAnimator!=null&&!mUpAnimator.isRunning()){
            mUpAnimator.start();
        }
    }

    private int mDegrees=-90;
    ValueAnimator mUpAnimator;

    private void initView() {
        mUpAnimator = ValueAnimator.ofInt(-90, 270);//从-90°开始旋转
        mUpAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mUpAnimator.setDuration(1000);
        mUpAnimator.setInterpolator(new LinearInterpolator());
        mUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegrees = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    public void stopScan() {
        if(mUpAnimator!=null&mUpAnimator.isRunning()){
            mUpAnimator.cancel();
        }
    }
}
