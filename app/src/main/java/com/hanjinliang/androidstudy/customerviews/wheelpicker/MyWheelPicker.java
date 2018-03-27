package com.hanjinliang.androidstudy.customerviews.wheelpicker;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by HanJinLiang on 2018-03-27.
 */

public class MyWheelPicker<T> extends View {
    /**
     * 数据源
     */
    List<T> mDataList;

    /**
     * 画笔
     */
    Paint mPaint;
    /**
     * 每一项高度
     */
    private int mItemHeight;
    /**
     * 每一项高度Padding
     */
    private int mItemPadding;

    /**
     * 一半的个数  总个数就是 mHalfCount*2+1
     */
    private int mHalfCount=2;
    /**
     * 整个内容区域的Rect
     */
    private Rect mContentRect;
    /**
     *选中区域的Rect
     */
    private Rect mSelectedRect;

    /**
     * 第一块绘制文字的Y值
     */
    private float mFirstBlockDrawTextY;

    /**
     * 中间选中的区块的绘制Y
     */
    private float mCenterItemDrawnY;


    /**
     * 选中背景色颜色
     */
    private int mSelectedBgColor;
    /**
     * 字体颜色
     */
    private int mTextColor;
    /**
     * 字体大小
     */
    private int mTextSize;
    /**
     * 选中字体颜色
     */
    private int mTextSelectedColor;
    /**
     * 选中字体大小
     */
    private int mTextSelectedSize;

    LinearGradient mLinearGradient;
    private int mOffsetY;
    /**
     * 是否启用循环
     */
    private boolean mIsCyclic;
    /**
     * 最大的偏移量
     */
    int mMaxOffset;
    /**
     * 最小的偏移量
     */
    int mMinOffset;

    /**
     * 指示文字
     */
    private String mIndicatorText;
    /**
     * 指示文字颜色
     */
    private int mIndicatorColor;
    /**
     * 指示文字大小
     */
    private int mIndicatorSize;

    public MyWheelPicker(Context context) {
        this(context,null);
    }

    public MyWheelPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mSelectedBgColor=Color.LTGRAY;
        mTextColor=Color.BLACK;
        mTextSelectedColor=Color.BLUE;
        mTextSize=sp2px(12);
        mTextSelectedSize=sp2px(16);

        mIndicatorColor=Color.BLACK;
        mIndicatorSize=sp2px(12);

        mLinearGradient=new LinearGradient(mTextColor,mTextSelectedColor);

        mIsCyclic=true;

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);


        //初始化 mItemHeight
        mItemPadding=dp2px(20);

        computeTextSize();
        computeMinMaxOffset();
    }

    /**
     * 计算最大最小偏移量
     */
    private void computeMinMaxOffset() {
        mMaxOffset=mIsCyclic?Integer.MAX_VALUE:mItemHeight*(mDataList.size()-1);
        mMinOffset=mIsCyclic?Integer.MIN_VALUE:0;
    }

    /**
     * 计算字体相关的大小
     */
    private void computeTextSize() {
        //取较大的字体
        mPaint.setTextSize(Math.max(mTextSize,mTextSelectedSize));
        mItemHeight= (int) (mPaint.getFontMetrics().bottom-mPaint.getFontMetrics().top+mItemPadding);
        mFirstBlockDrawTextY=mItemHeight/2-(mPaint.getFontMetrics().bottom+mPaint.getFontMetrics().top)/2;
        mCenterItemDrawnY=mFirstBlockDrawTextY+mHalfCount*mItemHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode!=MeasureSpec.EXACTLY){//高度不是明确值 计算高度
            heightSize=mItemHeight*(mHalfCount*2+1)+getPaddingTop()+getPaddingBottom();
        }

        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mContentRect=new Rect(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());

        mSelectedRect=new Rect(getPaddingLeft(),getPaddingTop()+mItemHeight*mHalfCount,
                getWidth()-getPaddingRight(),getPaddingTop()+mItemHeight*(mHalfCount+1));


    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);

        //选中背景色
        mPaint.setColor(mSelectedBgColor);
        canvas.drawRect(mSelectedRect,mPaint);


        int mOffsetCount=-mOffsetY/mItemHeight;

        for(int i=mOffsetCount-mHalfCount-1;i<=mOffsetCount+mHalfCount+1;i++){
            int pos=i;
            if(mIsCyclic){//开启循环
                if(pos<0){//保证pos在mDataList的长度范围
                    pos=mDataList.size()+(i%mDataList.size());
                }
                if(pos>=mDataList.size()){
                    pos=i%mDataList.size();
                }
            }else {//关闭循环  超出范围的都不绘制
                if (i < 0 || i > mDataList.size() - 1) {//小于0表示上面
                    continue;
                }
            }
            if(i==mOffsetCount){//选中的
                mPaint.setColor(mTextSelectedColor);
                mPaint.setTextSize(mTextSelectedSize);
            }else{
                mPaint.setColor(mTextColor);
                mPaint.setTextSize(mTextSize);
            }

            //每一项的绘制基线
            float itemDrawY=(mHalfCount+i)*mItemHeight+mFirstBlockDrawTextY+mOffsetY;



            //颜色渐变  先绘制渐变色 不然颜色会被覆盖
            if(Math.abs(itemDrawY-mCenterItemDrawnY)<mItemHeight){//距离在一项之间
                float colorRatio=1-Math.abs(itemDrawY-mCenterItemDrawnY)/mItemHeight;
                mPaint.setColor(mLinearGradient.getColor(colorRatio));
            }else{
                mPaint.setColor(mTextColor);
            }

            //透明度渐变
            float alphaRatio;
            if(itemDrawY>mCenterItemDrawnY){//下半部分
                alphaRatio=(mContentRect.height()-itemDrawY)/(mContentRect.height()-mCenterItemDrawnY);
            }else{
                alphaRatio=itemDrawY/mCenterItemDrawnY;
            }
            alphaRatio = alphaRatio < 0 ? 0 :alphaRatio;
            mPaint.setAlpha((int) (alphaRatio*255));


            //字体渐变
            if(Math.abs(itemDrawY-mCenterItemDrawnY)<mItemHeight){//距离在一项之间
                float sizeRatio=1-Math.abs(itemDrawY-mCenterItemDrawnY)/mItemHeight;

                mPaint.setTextSize(Math.min(mTextSelectedSize,mTextSize)+sizeRatio*(Math.abs(mTextSelectedSize-mTextSize)));
            }else{
                mPaint.setTextSize(mTextSize);
            }


            canvas.drawText(mDataList.get(pos).toString(),mContentRect.centerX(),itemDrawY,mPaint);
        }

        if(mIndicatorText !=null&&!mIndicatorText.isEmpty()){//绘制指示文字
            mPaint.setTextAlign(Paint.Align.LEFT);
            mPaint.setTextSize(mIndicatorSize);
            mPaint.setColor(mIndicatorColor);
            canvas.drawText(mIndicatorText,mContentRect.centerX(),mCenterItemDrawnY,mPaint);
        }
    }

    float mLastDownY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastDownY=event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveDistance=event.getY()-mLastDownY;
                mOffsetY+=moveDistance;
                mLastDownY=event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                int remainder=Math.abs(mOffsetY%mItemHeight);
                if(remainder!=0){//移动的距离不是整数
                    if(remainder>mItemHeight/2){//超过了一半
                        mOffsetY=(mOffsetY/mItemHeight+(mOffsetY>0?1:-1))*mItemHeight;
                    }else{
                        mOffsetY=(mOffsetY/mItemHeight)*mItemHeight;
                    }
                }
                if(!mIsCyclic) {
                    if (mOffsetY > mMaxOffset) {//最下面一条 超出边界
                        mOffsetY = mMaxOffset;
                    } else if (mOffsetY > mMinOffset) {//最上面一条
                        mOffsetY = mMinOffset;
                    }
                }
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }

    public void setIndicatorText(String indicatorText) {
        mIndicatorText = indicatorText;
    }

    private int dp2px(int dp){
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
