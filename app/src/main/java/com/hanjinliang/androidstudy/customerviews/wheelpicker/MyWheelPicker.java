package com.hanjinliang.androidstudy.customerviews.wheelpicker;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;


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
     * 没选中绘制文字的Y值
     */
    private float mUnSelectedBlockDrawTextY;
    /**
     * 选中绘制文字的Y值
     */
    private float mSelectedBlockDrawTextY;

    /**
     * 中间选中的区块的绘制Y
     */
    private float mCenterItemDrawnY;

    /**
     * 指示绘制Y
     */
    private float mIndicatorDrawnY;


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

    /**
     * 是否在滑动时候  手动停止了
     */
    private boolean isAbortFling;
    private OnItemSelectedListener<T> mOnItemSelectedListener;


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
        mTextSelectedSize=sp2px(18);

        mIndicatorColor=Color.BLACK;
        mIndicatorSize=sp2px(14);

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


        mScroller=new Scroller(getContext());
    }

    /**
     * 计算最大最小偏移量
     */
    private void computeMinMaxOffset() {
        mMaxOffset=mIsCyclic?Integer.MAX_VALUE:mItemHeight*(mDataList.size()-1);
        mMinOffset=mIsCyclic?Integer.MIN_VALUE:0;

        //fling的极限距离
        mMinFling=mIsCyclic?Integer.MIN_VALUE:-mItemHeight*(mDataList.size()-1);
        mMaxFling=mIsCyclic?Integer.MAX_VALUE:0;
    }

    /**
     * 计算字体相关的大小
     */
    private void computeTextSize() {
        //取较大的字体
        mPaint.setTextSize(Math.max(mTextSize,mTextSelectedSize));
        mItemHeight= (int) (mPaint.getFontMetrics().bottom-mPaint.getFontMetrics().top+mItemPadding);
        //会导致动画有一个闪的效果
        //  mPaint.setTextSize(mTextSize);
        mPaint.setTextSize(mTextSelectedSize);
        mUnSelectedBlockDrawTextY=mItemHeight/2-(mPaint.getFontMetrics().bottom+mPaint.getFontMetrics().top)/2;
        mPaint.setTextSize(mTextSelectedSize);
        mSelectedBlockDrawTextY=mItemHeight/2-(mPaint.getFontMetrics().bottom+mPaint.getFontMetrics().top)/2;
        mCenterItemDrawnY=mSelectedBlockDrawTextY+mHalfCount*mItemHeight;

        mPaint.setTextSize(mIndicatorSize);
        mIndicatorDrawnY=mItemHeight/2-(mPaint.getFontMetrics().bottom+mPaint.getFontMetrics().top)/2+mHalfCount*mItemHeight;
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


            //每一项的绘制基线
            float itemDrawY;
            if(i==mOffsetCount){//选中的 自己大小
                itemDrawY=(mHalfCount+i)*mItemHeight+mSelectedBlockDrawTextY+mOffsetY;
            }else{
                itemDrawY=(mHalfCount+i)*mItemHeight+mUnSelectedBlockDrawTextY+mOffsetY;
            }


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
            canvas.drawText(mIndicatorText,mContentRect.centerX()+ (int) mPaint.measureText(mDataList.get(0).toString())/2+mItemPadding,mIndicatorDrawnY,mPaint);
        }
    }
    VelocityTracker mTracker;
    Scroller mScroller;
    private int mMinFling;
    private int mMaxFling;

    Handler mHandler=new Handler();

    Runnable mScrollerRunnable =new Runnable() {
        @Override
        public void run() {
            if(mScroller.computeScrollOffset()){
                mOffsetY=mScroller.getCurrY();
                postInvalidate();
                mHandler.postDelayed(this,16);
            }

            if(mScroller.isFinished()){//结束了  响应监听事件
                if(mOnItemSelectedListener==null){
                    return;
                }
                if(mDataList==null||mDataList.size()==0){
                    return;
                }
                int position=computeRealPosition(-mOffsetY/mItemHeight);
                if(mCurrentPosition!=position) {
                    mCurrentPosition=position;
                    mOnItemSelectedListener.onItemSelected(position, mDataList.get(position));
                }
            }
        }
    };
    private int mCurrentPosition;
    /**
     * 计算真正position的位置
     * @return
     */
    private int computeRealPosition(int position) {
        if(position<0){//保证pos在mDataList的长度范围
            position=mDataList.size()+(position%mDataList.size());
        }
        if(position>=mDataList.size()){
            position=position%mDataList.size();
        }
        return position;
    }

    float mLastDownY;
    float mLastTouchY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mTracker==null){
            mTracker=VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){//点击时候还没有停止滑动
                    mScroller.abortAnimation();
                    isAbortFling=true;
                }else{
                    isAbortFling=false;
                }
                mLastTouchY=mLastDownY=event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveDistance=event.getY()-mLastDownY;
                mOffsetY+=moveDistance;
                mLastDownY=event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                float upY=event.getY();
                if(!isAbortFling&&mLastDownY==mLastTouchY){//点击
                    performClick();//点击

                    if(upY>mSelectedRect.bottom){//点击下半部分
                        int movePos= (int) (upY-mSelectedRect.bottom)/mItemHeight+1;
                        mScroller.startScroll(0,mOffsetY,0,-movePos*mItemHeight);
                    }else if(upY<mSelectedRect.top){//点击上半部分
                        int movePos= (int) (mSelectedRect.top-upY)/mItemHeight+1;
                        mScroller.startScroll(0,mOffsetY,0,movePos*mItemHeight);
                    }

                }else {

                    mTracker.computeCurrentVelocity(1000, 12000);
                    float yVelocity = mTracker.getYVelocity();
                    if (Math.abs(yVelocity) > 800) {//有滑行
                        mScroller.fling(0, mOffsetY, 0, (int) yVelocity, 0, 0, mMinFling, mMaxFling);
                        //修正  保证
                        mScroller.setFinalY(mScroller.getFinalY() +
                                computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));

                    } else {
                        mScroller.startScroll(0, mOffsetY, 0, computeDistanceToEndPoint(mOffsetY % mItemHeight));
                    }
                }
                if(!mIsCyclic) {
                    if (mScroller.getFinalY() > mMaxFling) {
                        mScroller.setFinalY(mMaxFling);
                    } else if (mScroller.getFinalY() < mMinFling) {
                        mScroller.setFinalY(mMinFling);
                    }
                }

                //通过线程
                mHandler.post(mScrollerRunnable);

                mTracker.recycle();
                mTracker=null;
                break;
        }
        return super.onTouchEvent(event);
    }

    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mItemHeight / 2) {
            if (mOffsetY < 0) {
                return -mItemHeight - remainder;
            } else {
                return mItemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }


    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        computeMinMaxOffset();
        postInvalidate();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener<T>{
        public void onItemSelected(int position,T data);
    }

    public void setIndicatorText(String indicatorText) {
        mIndicatorText = indicatorText;
    }

    public void setCurrentPosition(int currentPosition) {
        setCurrentPosition(currentPosition,true);
    }

    public void setCyclic(boolean cyclic) {
        if (mIsCyclic == cyclic) {
            return;
        }
        mIsCyclic = cyclic;
        computeMinMaxOffset();
        requestLayout();
    }

    /**
     * 指定选中位置
     * @param currentPosition  跳转到的位置
     * @param smooth  是否平滑跳转
     */
    public void setCurrentPosition(int currentPosition,boolean smooth) {
        //分支上面的修改
        //分支第二次修改
        if (mCurrentPosition == currentPosition) {
            return;
        }
        if (currentPosition >= mDataList.size()) {
            currentPosition = mDataList.size() - 1;
        }
        if (currentPosition < 0) {
            currentPosition = 0;
        }

        if(!mScroller.isFinished()){
            mScroller.abortAnimation();
        }

        if(smooth){
            mScroller.startScroll(0,mOffsetY,0,(mCurrentPosition-currentPosition)*mItemHeight,500);
            mScroller.setFinalY(mScroller.getFinalY() +
                    computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
            mHandler.post(mScrollerRunnable);
        }else{
            mCurrentPosition=currentPosition;
            mOffsetY=-currentPosition*mItemHeight;
            postInvalidate();
            //监听
            if(mOnItemSelectedListener!=null&&mDataList!=null){
                mOnItemSelectedListener.onItemSelected(currentPosition,mDataList.get(currentPosition));
            }
        }
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
