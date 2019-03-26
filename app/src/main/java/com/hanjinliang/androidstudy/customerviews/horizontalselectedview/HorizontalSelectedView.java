package com.hanjinliang.androidstudy.customerviews.horizontalselectedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanJinLiang on 2017-06-27.
 * 横向滚动选择View
 * http://blog.csdn.net/iamdingruihaha/article/details/71422269
 */
public class HorizontalSelectedView extends View {
    private int mCount;//一屏幕显示的数量
    private int anInt;//单个宽度
    private float mSelectedSize;//选中字体大小
    private int mSelectedColor;//选中字体颜色
    private float mUnSelectedSize;//选中字体大小
    private int mUnSelectedColor;//选中字体颜色

    private TextPaint mSelectedPaint;//选中画笔
    private TextPaint mUnSelectedPaint;//未选中画笔

    private List<String>  mDatas;//数据源

    private int mWidth;//宽度
    private int mHeight;//高度

    private int mSelectedIndex=0;//选中的索引

    Rect mRect=new Rect();

    private int textWidth = 0;
    private int textHeight = 0;

    public HorizontalSelectedView(Context context) {
        this(context,null);
    }

    public HorizontalSelectedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalSelectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        //获取自定义属性
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.HorizontalSelectedView,defStyleAttr,0);
        mCount=ta.getInt(R.styleable.HorizontalSelectedView_HorizontalSelectedViewCount,5);
        mSelectedSize=ta.getDimension(R.styleable.HorizontalSelectedView_HorizontalSelectedViewSelectedSize,16);
        mSelectedColor=ta.getColor(R.styleable.HorizontalSelectedView_HorizontalSelectedViewSelectedColor, Color.parseColor("#2b2b2b"));
        mUnSelectedSize=ta.getDimension(R.styleable.HorizontalSelectedView_HorizontalSelectedViewUnselectedSize,12);
        mUnSelectedColor=ta.getColor(R.styleable.HorizontalSelectedView_HorizontalSelectedViewUnselectedColor, Color.parseColor("#afafaf"));
        //记得回收
        ta.recycle();

        //设置选中画笔
        mSelectedPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setColor(mSelectedColor);
        mSelectedPaint.setTextSize(mSelectedSize);

        //设置未选中画笔
        mUnSelectedPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mUnSelectedPaint.setColor(mUnSelectedColor);
        mUnSelectedPaint.setTextSize(mUnSelectedSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth=getWidth();
        mHeight=getHeight();
        anInt=mWidth/mCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height ;//只要高度需要计算


        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mSelectedPaint.getTextBounds("sd", 0, 2, mRect);
            float textHeight = mRect.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSelectedIndex >= 0 && mSelectedIndex <= mDatas.size() - 1) {//加个保护；防止越界

            mSelectedPaint.getTextBounds(mDatas.get(mSelectedIndex), 0, mDatas.get(mSelectedIndex).length(), mRect);
            //X表示text的baseLine (X,Y) 表示的是text的左下角
            canvas.drawText(mDatas.get(mSelectedIndex), mWidth / 2 - mRect.width() / 2+mOffset, mHeight / 2 + mRect.height() / 2, mSelectedPaint);//画出居中的

            for (int i = 0; i < mDatas.size(); i++) {//遍历data，把每个地方都绘制出来，
                if (mSelectedIndex > 0 && mSelectedIndex <mDatas.size() - 1) {//这里主要是因为strings数据源的文字长度不一样，为了让被选中两边文字距离中心宽度一样，我们取得左右两个文字长度的平均值
                    mUnSelectedPaint.getTextBounds(mDatas.get(mSelectedIndex- 1), 0, mDatas.get(mSelectedIndex - 1).length(), mRect);
                    int width1 = mRect.width();
                    mUnSelectedPaint.getTextBounds(mDatas.get(mSelectedIndex + 1), 0, mDatas.get(mSelectedIndex+ 1).length(), mRect);
                    int width2 = mRect.width();
                    textWidth = (width1 + width2) / 2;
                }
                if (i == 0) {//得到高，高度是一样的，所以无所谓
                    mUnSelectedPaint.getTextBounds(mDatas.get(0), 0, mDatas.get(0).length(), mRect);
                    textHeight = mRect.height();
                }

                if (i != mSelectedIndex)
                    canvas.drawText(mDatas.get(i), (i - mSelectedIndex) * anInt + mWidth/ 2 - textWidth / 2 +mOffset , getHeight() / 2 + textHeight / 2, mUnSelectedPaint);//画出每组文字
            }
        }
    }
    float downX;
    float mOffset;
    boolean isSelectedChanged;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSelectedChanged=false;
                downX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX=event.getX();
                if (mSelectedIndex != 0 && mSelectedIndex!= mDatas.size() - 1)
                    mOffset = moveX - downX;//滑动时的偏移量，用于计算每个是数据源文字的坐标值
                else {
                    mOffset =(moveX - downX)/1.5f;//当滑到两端的时候添加一点阻力
                }
                if(moveX>downX){//向右滑动
                    if(moveX-downX>=anInt){//滑动超过一个单位宽度
                        if (mSelectedIndex > 0) {
                            mSelectedIndex--;
                            downX = moveX;
                            isSelectedChanged=true;
                        }
                    }

                }else{//向左滑动
                    if(downX-moveX>=anInt){//滑动超过一个单位宽度
                        if (mSelectedIndex < mDatas.size() - 1) {
                            mSelectedIndex++;
                            downX = moveX;
                            isSelectedChanged=true;
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if(downX==event.getX()){//是单击
                    //获取到点击屏幕的区域
                    int  clickIndex = (int) (downX /(getWidth()/mCount));
                    LogUtils.e("clickIndex="+clickIndex);
                    int selectedIndex=mSelectedIndex+clickIndex-mCount/2;
                    if(selectedIndex<0||selectedIndex>mDatas.size()-1){
                        break;
                    }
                    mSelectedIndex=selectedIndex;
                    isSelectedChanged=true;
                }else {
                    mOffset = 0;
                }
                invalidate();//重绘
                if(isSelectedChanged&&mOnSelectIndexChangedListener!=null){
                    mOnSelectIndexChangedListener.onSelectIndexChanged(mSelectedIndex);
                }
                break;
        }
        return super.onTouchEvent(event);
    }



    /**
     * 设置数据源
     * @param datas
     */
    public void setDatas(List<String> datas) {
        mDatas = datas;
        mSelectedIndex=datas.size()-1;//默认选择最后一个
    }

    /**
     * 设置选中索引
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        if(selectedIndex<0||selectedIndex>mDatas.size()-1){
           return;
        }
        if(selectedIndex==mSelectedIndex){return;}
        mSelectedIndex=selectedIndex;
        invalidate();//重绘
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    OnSelectIndexChangedListener mOnSelectIndexChangedListener;

    public void setOnSelectIndexChangedListener(OnSelectIndexChangedListener onSelectIndexChangedListener) {
        mOnSelectIndexChangedListener = onSelectIndexChangedListener;
    }

    public interface  OnSelectIndexChangedListener{
        void onSelectIndexChanged(int selectedIndex);
    }
}
