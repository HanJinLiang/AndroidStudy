package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ${lwh} on 2018/3/1.
 *
 * @descirbe 进度条view
 */

public class MyProgressView extends View {
    private Paint mPaint;
    private int mPercent;
    private int mColor;
    private int mBgColor;
    private Paint mTextPaint;
    private int mProgressHeight;//进度条高度
    public MyProgressView(Context context) {
        this(context,null);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mProgressHeight=dip2px(getContext(),6);//进度条高度   单位dp

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mProgressHeight);
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(sp2px(getContext(),12));
        mTextPaint.setTextAlign(Paint.Align.CENTER);//

        mBgColor=Color.parseColor("#E6E6E6");

        textPadding=dip2px(getContext(),5);
        mProgressTextWidth=mTextPaint.measureText("10%");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
          int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int height= Math.max(mProgressHeight,(int)mTextPaint.getTextSize());//默认值
        if(heightMode==MeasureSpec.EXACTLY){//不是明确的值
            height=Math.max(height,heightSize);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),height);//高度保证能够正常显示
    }

    int baseLineY;//文字基线
    float mProgressTextWidth;//百分比部分宽度
    int textPadding;//进度和文字的间隔
    float mCenterY;//Y的中心
    float mProgressStopX;//进度条结束
    float mTextStartX;//文字开始X位置
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterY=getHeight()/2;
        mProgressStopX=getWidth()-mProgressTextWidth-mProgressHeight/2-textPadding;
        //mTextStartX=getWidth();
        mTextStartX=mProgressStopX+textPadding+mProgressTextWidth/2+mProgressHeight/2;
        //文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离

        //保证文字垂直居中
        baseLineY = (int) (mCenterY- top/2 - bottom/2);//基线中间点的y轴计算公式


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        mPaint.setColor(mBgColor);
        canvas.drawLine(mProgressHeight / 2, mCenterY, mProgressStopX, mCenterY, mPaint);
        if(mPercent>0) {
            mPaint.setColor(mColor);
            canvas.drawLine(mProgressHeight / 2, mCenterY, mProgressStopX * mPercent / 100, mCenterY, mPaint);
        }
        mTextPaint.setColor(mColor);
        canvas.drawText(mPercent + "%",mTextStartX, baseLineY, mTextPaint);
        mTextPaint.setColor(mBgColor);
        canvas.drawLine(getWidth()-mProgressTextWidth,mCenterY,getWidth(),mCenterY,mTextPaint);
    }


    public void setPercent(int mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
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
