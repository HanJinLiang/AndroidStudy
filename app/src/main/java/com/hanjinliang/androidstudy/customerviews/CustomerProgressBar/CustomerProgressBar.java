package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-06-27.
 */

public class CustomerProgressBar extends View {
    Context mContext;
    private int mFirstColor;
    private int mSecondColor;
    private float mProgressWidth;
    private long mSpeed;
    private int mProgress=0;

    Paint mPaint;
    TextPaint mTextPaint;
    Rect mRect;
    public CustomerProgressBar(Context context) {
        this(context,null);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.CustomerProgressBar,defStyleAttr,0);
        mFirstColor=ta.getColor(R.styleable.CustomerProgressBar_FirstColor, Color.BLUE);
        mSecondColor=ta.getColor(R.styleable.CustomerProgressBar_SecondColor, Color.GRAY);
        mProgressWidth=ta.getDimension(R.styleable.CustomerProgressBar_ProgressWidth,10);
        mSpeed=ta.getInt(R.styleable.CustomerProgressBar_Speed,20);
        ta.recycle();

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        mPaint.setStrokeWidth(mProgressWidth);

        mTextPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(36);
        mTextPaint.setColor(Color.BLUE);
        mRect=new Rect();
    }
    int centre;
    int radius;
    RectF oval;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centre= getWidth() / 2; // 获取圆心的x坐标
        radius= centre - (int)mProgressWidth / 2;// 半径
        mPaint.setStrokeWidth(mProgressWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

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

    public boolean isNext;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isNext) {
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }else{
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }
        //中间文字随便写写的
        centerText=mProgress+"%";
        mTextPaint.getTextBounds(centerText,0,centerText.length(),mRect);
        canvas.drawText(centerText,centre-mRect.width()/2,centre+mRect.height()/2,mTextPaint);
    }
    String centerText;
    private boolean isRunning=true;
    public void startProgress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    Log.e("startProgress","startProgress-"+mProgress);
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgress++;
                    if (mProgress >= 360)
                    {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                }
            }
        }).start();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startProgress();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning=false;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.e("visibility","visibility-"+visibility);
        if(visibility==VISIBLE){
            if(isRunning){//正在执行
                return;
            }else{
                isRunning=true;
                startProgress();
            }
        }else{//不可见  不执行
            isRunning=false;
        }
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
}
