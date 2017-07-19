package com.hanjinliang.androidstudy.customerviews.ArcMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-14.
 */
public class BgTextView extends android.support.v7.widget.AppCompatTextView {
    Path mPath;
    Paint mPaint;
    private int mWidth,mHeight;
    private float mStrokeWidth ;
    private int mBgColor;

    private float mLeftTopRadius ;
    private float mLeftBottomRadius ;
    private float mRightTopRadius ;
    private float mRightBottomRadius ;
    private float mRadius ;


    //控制4个边是否绘制
    private boolean isDrawRight=false;
    private boolean isDrawLeft=true;
    private boolean isDrawBottom=true;
    private boolean isDrawTop=true;

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
        mStrokeWidth=ta.getDimension(R.styleable.BgTextView_BgStrokeWidth,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,getResources().getDisplayMetrics()));
        mBgColor=ta.getColor(R.styleable.BgTextView_BgColor,Color.BLUE);

        mLeftTopRadius=ta.getDimension(R.styleable.BgTextView_LeftTopRadius,mStrokeWidth);
        mLeftBottomRadius=ta.getDimension(R.styleable.BgTextView_LeftBottomRadius,mStrokeWidth);
        mRightBottomRadius=ta.getDimension(R.styleable.BgTextView_RightBottomRadius,mStrokeWidth);
        mRightTopRadius=ta.getDimension(R.styleable.BgTextView_RightTopRadius,mStrokeWidth);
        mRadius=ta.getDimension(R.styleable.BgTextView_Radius,-1);
        if(mRadius!=-1){//有设置mRadius
            mLeftTopRadius=mRadius;
            mLeftBottomRadius=mRadius;
            mRightBottomRadius=mRadius;
            mRightTopRadius=mRadius;
        }
        isDrawRight=ta.getBoolean(R.styleable.BgTextView_DrawRight,true);
        isDrawLeft=ta.getBoolean(R.styleable.BgTextView_DrawLeft,true);
        isDrawBottom=ta.getBoolean(R.styleable.BgTextView_DrawBottom,true);
        isDrawTop=ta.getBoolean(R.styleable.BgTextView_DrawTop,true);

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
        initPath();
    }

    /**
     * 初始化path
     */
    private void initPath() {
        controlRadius();

        //绘制背景Path
        mPath.reset();
        //移动到右上角
        mPath.moveTo(mWidth-mRightTopRadius,0+mStrokeWidth/2);
        if(isDrawRight) {//需要画右边
            mPath.addArc(new RectF(mWidth - mRightTopRadius * 2 + mStrokeWidth / 2, 0 + mStrokeWidth / 2, mWidth - mStrokeWidth / 2, mRightTopRadius * 2 - mStrokeWidth / 2), -90, 90);
            mPath.lineTo(mWidth - mStrokeWidth / 2, mHeight - mRightBottomRadius);
            mPath.addArc(new RectF(mWidth - mRightBottomRadius * 2 + mStrokeWidth / 2, mHeight - mRightBottomRadius * 2 + mStrokeWidth / 2, mWidth - mStrokeWidth / 2, mHeight - mStrokeWidth / 2), 0, 90);
        }else{//不需要直接移动到
            mPath.moveTo(mWidth-mRightBottomRadius, mHeight - mStrokeWidth / 2);
        }


        if(isDrawBottom) {//连接到左下角
            mPath.lineTo(mLeftBottomRadius, mHeight - mStrokeWidth / 2);
        }else{//移动到左下角
            mPath.moveTo(mLeftBottomRadius, mHeight - mStrokeWidth / 2);
        }

        if(isDrawLeft) {//需要画左边
            mPath.addArc(new RectF(0 + mStrokeWidth / 2, mHeight - mLeftBottomRadius * 2 + mStrokeWidth / 2, mLeftBottomRadius * 2 - mStrokeWidth / 2, mHeight - mStrokeWidth / 2), 90, 90);

            mPath.lineTo(0 + mStrokeWidth / 2, mLeftTopRadius);
            mPath.addArc(new RectF(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2, mLeftTopRadius * 2 - mStrokeWidth / 2, mLeftTopRadius * 2 - mStrokeWidth / 2), 180, 90);
        }else{//直接移动到
            mPath.moveTo(0+mLeftBottomRadius, 0 + mStrokeWidth / 2);
        }

        //连接到左上角
        if(isDrawTop) {
            mPath.lineTo(mWidth-mRightTopRadius,0+mStrokeWidth/2);
        }else{
            mPath.moveTo(mWidth-mRightTopRadius,0+mStrokeWidth/2);
        }
    }

    /**
     * 控制各个Radius 在[mStrokeWidth,mHeight/2]区间
     */
    private void controlRadius() {
        if(mLeftTopRadius<mStrokeWidth){
            mLeftTopRadius=mStrokeWidth;
        }
        if(mLeftBottomRadius<mStrokeWidth){
            mLeftBottomRadius=mStrokeWidth;
        }
        if(mRightTopRadius<mStrokeWidth){
            mRightTopRadius=mStrokeWidth;
        }
        if(mRightBottomRadius<mStrokeWidth){
            mRightBottomRadius=mStrokeWidth;
        }

        if(mLeftTopRadius>mHeight/2){
            mLeftTopRadius=mHeight/2;
        }
        if(mLeftBottomRadius>mHeight/2){
            mLeftBottomRadius=mHeight/2;
        }
        if(mRightTopRadius>mHeight/2){
            mRightTopRadius=mHeight/2;
        }
        if(mRightBottomRadius>mHeight/2){
            mRightBottomRadius=mHeight/2;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        canvas.drawPath(mPath,mPaint);
        super.onDraw(canvas);
    }

    /**
     * 设置四个边是否绘制
     * @param isDrawRight
     * @param isDrawLeft
     * @param isDrawBottom
     * @param isDrawTop
     */
    private void setDrawEnable(boolean isDrawRight,boolean isDrawLeft,boolean isDrawBottom,boolean isDrawTop){
        this.isDrawRight=isDrawRight;
        this.isDrawLeft=isDrawLeft;
        this.isDrawBottom=isDrawBottom;
        this.isDrawTop=isDrawTop;

        initPath();
        //刷新
        invalidate();
    }


    /**
     * 统一圆角
     */
    private void setRadius(float radius){
        mRadius=radius;
        mLeftTopRadius=mRadius;
        mLeftBottomRadius=mRadius;
        mRightBottomRadius=mRadius;
        mRightTopRadius=mRadius;
        initPath();
        //刷新
        invalidate();
    }

    /**
     * 分别设置四个圆角
     */
    private void setRadius(float leftTopRadius,float leftBottomRadius,float rightBottomRadius,float rightTopRadius){
        mLeftTopRadius=leftTopRadius;
        mLeftBottomRadius=leftBottomRadius;
        mRightBottomRadius=rightBottomRadius;
        mRightTopRadius=rightTopRadius;
        initPath();
        //刷新
        invalidate();
    }
}
