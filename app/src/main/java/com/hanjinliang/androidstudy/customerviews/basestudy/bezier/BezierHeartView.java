package com.hanjinliang.androidstudy.customerviews.basestudy.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by HanJinLiang on 2017-07-06.
 * 利用贝塞尔曲线实现 从圆形到心形的过度
 * http://www.gcssloop.com/customview/Path_Bezier
 *
 */

public class BezierHeartView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    Paint mPaint;
    private int mRadius=300;//半径
    private float mDifference = mRadius*C;        // 圆形的控制点与数据点的差值

    private PointF[] mDataPoint;
    private PointF[] mControlPoint;

    public BezierHeartView(Context context) {
        this(context,null);
    }

    public BezierHeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mRadius-=2;
        mPath=new Path();
        /**
         * 4个数据固定点
         */
        mDataPoint=new PointF[]{new PointF(mRadius,0),new PointF(0,mRadius),new PointF(-mRadius,0),new PointF(0,-mRadius)};
        //8个数据控制点 三阶贝塞尔曲线控制点
        mControlPoint=new PointF[]{new PointF(mRadius,0+mDifference),new PointF(0+mDifference,mRadius),new PointF(-mDifference,mRadius),new PointF(-mRadius,mDifference),
                new PointF(-mRadius,-mDifference),new PointF(-mDifference,-mRadius),new PointF(mDifference,-mRadius),new PointF(mRadius,-mDifference)};

        initAnim();

    }


    //从圆形过度到心形的偏移量
    private float mOffsetData=0;
    private float mOffsetA=0;
    private float mOffsetB=0;

    //从圆形过度到心形的偏移量最大值
    private float maxOffsetData=mRadius*2/3;
    private float maxOffsetA=mRadius/3;
    private float maxOffsetB=mRadius*4/5;


    Path mPath;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
        //画圆
//        mPath.moveTo(mDataPoint[0].x,mDataPoint[0].y);
//        mPath.cubicTo(mControlPoint[0].x,mControlPoint[0].y,mControlPoint[1].x,mControlPoint[1].y,mDataPoint[1].x,mDataPoint[1].y);
//        mPath.cubicTo(mControlPoint[2].x,mControlPoint[2].y,mControlPoint[3].x,mControlPoint[3].y,mDataPoint[2].x,mDataPoint[2].y);
//        mPath.cubicTo(mControlPoint[4].x,mControlPoint[4].y,mControlPoint[5].x,mControlPoint[5].y,mDataPoint[3].x,mDataPoint[3].y);
//        mPath.cubicTo(mControlPoint[6].x,mControlPoint[6].y,mControlPoint[7].x,mControlPoint[7].y,mDataPoint[0].x,mDataPoint[0].y);
        //画坐标
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(-getWidth()/2,0,getWidth()/2,0,mPaint);
        canvas.drawLine(0,-getHeight()/2,0,getHeight()/2,mPaint);
        mPaint.setColor(Color.RED);

        mPath.reset();
        //画心形
        mPath.moveTo(mDataPoint[0].x,mDataPoint[0].y);
        mPath.cubicTo(mControlPoint[0].x-mOffsetA,mControlPoint[0].y,mControlPoint[1].x,mControlPoint[1].y-mOffsetB,mDataPoint[1].x,mDataPoint[1].y);
        mPath.cubicTo(mControlPoint[2].x,mControlPoint[2].y-mOffsetB,mControlPoint[3].x+mOffsetA,mControlPoint[3].y,mDataPoint[2].x,mDataPoint[2].y);
        mPath.cubicTo(mControlPoint[4].x,mControlPoint[4].y,mControlPoint[5].x,mControlPoint[5].y,mDataPoint[3].x,mDataPoint[3].y+mOffsetData);
        mPath.cubicTo(mControlPoint[6].x,mControlPoint[6].y,mControlPoint[7].x,mControlPoint[7].y,mDataPoint[0].x,mDataPoint[0].y);

        canvas.drawPath(mPath,mPaint);
        canvas.translate(-getWidth()/2,-getHeight()/2);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    public void startAnim(){
         if(valueAnimator!=null&&!valueAnimator.isRunning()){
             valueAnimator.start();
         }
    }
    ValueAnimator valueAnimator;
    private void initAnim() {
        valueAnimator=ValueAnimator.ofFloat(0,1).setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetData=(float)animation.getAnimatedValue()*maxOffsetData;
                mOffsetA=(float)animation.getAnimatedValue()*maxOffsetA;
                maxOffsetB=(float)animation.getAnimatedValue()*maxOffsetB;
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
