package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-06-28.
 * 圆形上涨View
 */

public class CustomerProgressCircleView extends View {
    private Context mContext;
    private int mDefaultColor=Color.parseColor("#88dddddd");//默认颜色 未到达颜色
    private int mProgressColor=Color.parseColor("#8800ff66");
    private int mRadius;
    private int mCenter;
    private Paint mPaint;
    private boolean isNeedBorder;
    public CustomerProgressCircleView(Context context) {
        this(context,null);
    }

    public CustomerProgressCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerProgressCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRectF=new RectF();
    }
    Path mPath=new Path();
    RectF mRectF;
    private boolean isLeft;
    private int x;
    private int y;
    private int mPercent=50;
    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//设置mode SRC_IN
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setXfermode(null);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mDefaultColor);
        canvas.drawCircle(mCenter,mCenter,mRadius,mPaint);


//        画矩形
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(mProgressColor);
//        mPaint.setXfermode(mMode);
//        mRectF.set(0+dip2px(2),getWidth()-mProgressHeight+dip2px(2),getWidth()-dip2px(2),getWidth()-dip2px(2));
//        canvas.drawRect(mRectF,mPaint);


        //利用贝塞尔曲线画波浪线
        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }
        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }


        y=(int)((1-mPercent/100f)*getHeight());
        mPath.reset();
        mPath.moveTo(0,y);
        mPath.cubicTo(100+x*2,y+50,getWidth()-(100+x*2),y-50,getWidth(),y);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mProgressColor);
        mPaint.setXfermode(mMode);
        canvas.drawPath(mPath,mPaint);

        if(isNeedBorder) {//是否需话外圈圆
            mPaint.setXfermode(null);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mPaint.setStrokeWidth(dip2px(2));
            canvas.drawCircle(mCenter, mCenter, mRadius - dip2px(1), mPaint);
        }
        postInvalidateDelayed(10);
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
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            height=heightSize;
        }else{
            height=dip2px(200);//默认值
        }

        setMeasuredDimension(Math.max(width,height),Math.max(width,height));//为保证是圆形的   取最大值
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenter=getWidth()/2;
        mRadius=mCenter;
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px( float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isRunning=true;
        startProgress();
    }
    private  boolean isRunning;
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning=false;
    }

    public void setPercent(int percent) {
        mPercent = percent;
        postInvalidate();
    }

    public void startProgress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mPercent++;
                    if (mPercent >= 100)
                    {
                        mPercent = 0;
                    }
                    postInvalidate();
                }
            }
        }).start();

    }
}
