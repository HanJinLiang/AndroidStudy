package com.hanjinliang.androidstudy.customerviews.ArcMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-14.
 */
public class BgTextView extends android.support.v7.widget.AppCompatTextView {
    Path mPath;
    Paint mPaint;
    private int mWidth,mHeight;
    private float mStrokeWidth=10;
    private int mBgColor;
    public BgTextView(Context context) {
        this(context,null);
    }

    public BgTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BgTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.BgTextView,defStyleAttr,0);
        mStrokeWidth=ta.getDimension(R.styleable.BgTextView_BgStrokeWidth,12);
        mBgColor=ta.getColor(R.styleable.BgTextView_BgColor,Color.BLUE);
        //记得回收
        ta.recycle();

        //初始化画笔
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPath=new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        //绘制背景Path
        mPath.reset();
        mPath.moveTo(mWidth,mHeight-mStrokeWidth/2);
        mPath.lineTo(mHeight/2,mHeight-mStrokeWidth/2);
        mPath.addArc(new RectF(mStrokeWidth/2,mStrokeWidth/2,mHeight-mStrokeWidth/2,mHeight-mStrokeWidth/2),90,180);
        mPath.lineTo(mWidth,mStrokeWidth/2);
        //设置padding 保证右边圆环没有文字
        setPadding(mHeight/2,0,0,0);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath,mPaint);
        super.onDraw(canvas);
    }
}
