package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${lwh} on 2018/3/1.
 *
 * @descirbe 进度条view
 */

public class MyProgressDetailView extends View {
    private Paint mPaint;
    private int mPercent;
    private int mColor;

    private Paint mTextPaint;
    private int mProgressHeight;//进度条高度
    private int mProgressTextSize;//进度文字大小
    private int mProgressTextColor;//进度文字颜色
    private int mProgressTextPadding;//文字和进度条间隔

    public MyProgressDetailView(Context context) {
        this(context,null);
    }

    public MyProgressDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mColor=Color.RED;
        mProgressHeight=dip2px(getContext(),4);//进度条高度   单位dp
        mProgressTextSize=sp2px(getContext(),12);
        mProgressTextColor=Color.GRAY;
        mProgressTextPadding=dip2px(getContext(),10);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mProgressHeight);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mProgressTextSize);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);//


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int height= (int)(mTextPaint.getTextSize()+mProgressTextPadding+mProgressHeight*1.5);//默认值
        if(heightMode==MeasureSpec.EXACTLY){//不是明确的值
            height=Math.max(height,heightSize);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),height);//高度保证能够正常显示
    }

    int baseLineY;//文字基线
    float progressCenterY;//进度条Y
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        //保证文字垂直居中
        baseLineY = (int) (mTextPaint.getTextSize()/2- top/2 - bottom/2);//基线中间点的y轴计算公式

        progressCenterY=mTextPaint.getTextSize()+mProgressTextPadding+mProgressHeight/2;
    }
    float mProgressWidth;//进度条宽度
    String mTextStr;
    float mTextStrWidth;//文字宽度
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mProgressWidth=getWidth()* mPercent / 100;
        mTextStr="已投"+mPercent+"%";
        mTextStrWidth=mTextPaint.measureText(mTextStr);


        if(mTextStrWidth/2>mProgressWidth){//左边
            canvas.drawText(mTextStr,mTextStrWidth/2,baseLineY,mTextPaint);
        }else if(mProgressWidth+mTextStrWidth/2>getWidth()){//右边
            canvas.drawText(mTextStr,getWidth()-mTextStrWidth/2,baseLineY,mTextPaint);
        }else{//中间能显示下
            canvas.drawText(mTextStr,mProgressWidth,baseLineY,mTextPaint);
        }

        mPaint.setAlpha(255);
        canvas.drawLine(0,progressCenterY,mProgressWidth,progressCenterY,mPaint);
        canvas.drawCircle(mProgressWidth,progressCenterY,mProgressHeight*1.4f/2f,mPaint);
        mPaint.setAlpha(100);
        canvas.drawCircle(mProgressWidth,progressCenterY,mProgressHeight*2f/2f,mPaint);
    }


    public void setPercent(int mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        mPaint.setColor(mColor);
        invalidate();
    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px( Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px(  Context context,float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
