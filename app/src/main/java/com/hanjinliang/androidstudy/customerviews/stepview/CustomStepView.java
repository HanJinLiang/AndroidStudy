package com.hanjinliang.androidstudy.customerviews.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout.MyAdapter;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-11-17.
 * 自定义View
 */
public class CustomStepView extends View {
    ArrayList<StepViewBean> mDatas=new ArrayList<>();
    private int mLineFinishedColor= Color.parseColor("#2b2b2b");
    private int mLineUnfinishedColor= Color.parseColor("#afafaf");
    private int mLinePadding=dip2px(5);
    private int mLineWidth=dip2px(50);
    private int mDescribeTextColor= Color.parseColor("#2b2b2b");
    private float mDescribeTextSize;
    private int mDirection= Direction.horizontal;
    private int mStepIconWidth=dip2px(20);

    Paint mTextPaint;
    Paint mFinishPaint;
    Paint mUnfinishPaint;


    public static class Direction{
        //值必须和系统的保持一致
        public static final int horizontal=0;
        public static final int vertical=1;
    }

    public CustomStepView(Context context) {
        this(context,null);
    }

    public CustomStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomStepView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.StepView,defStyle,0);
        mLineFinishedColor=ta.getColor(R.styleable.StepView_LineFinishedColor,mLineFinishedColor);
        mLineUnfinishedColor=ta.getColor(R.styleable.StepView_LineUnfinishedColor,mLineUnfinishedColor);
        mLinePadding=ta.getInteger(R.styleable.StepView_LinePadding,mLinePadding);
        mDescribeTextColor=ta.getColor(R.styleable.StepView_DescribeTextColor,mDescribeTextColor);
        mDescribeTextSize=ta.getDimension(R.styleable.StepView_DescribeTextSize,30);
        mDirection=ta.getInteger(R.styleable.StepView_Direction, Direction.horizontal);
        ta.recycle();

        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mDescribeTextSize);
        mTextPaint.setColor(mDescribeTextColor);

        mFinishPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mFinishPaint.setColor(mLineFinishedColor);

        mUnfinishPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnfinishPaint.setColor(mLineUnfinishedColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(MeasureSpec.EXACTLY==MeasureSpec.getMode(widthMeasureSpec)){//MatchParent
            setMeasuredDimension(getResources().getDisplayMetrics().widthPixels-100,MeasureSpec.getSize(heightMeasureSpec));
        }else{
            setMeasuredDimension(getResources().getDisplayMetrics().widthPixels-100,MeasureSpec.getSize(heightMeasureSpec));
        }

    }
    float textWidth;
    float firstWidth;
    @Override
    protected void onDraw(Canvas canvas) {
        if(mDatas==null||mDatas.size()==0){return;}
        if(mDirection== StepView.Direction.horizontal){ //横的
            for(int i=0;i<mDatas.size();i++){
                StepViewBean stepViewBean=mDatas.get(i);
                if(i==0) {
                    firstWidth = mTextPaint.measureText(stepViewBean.getDescribe());
                }
                textWidth=mTextPaint.measureText(stepViewBean.getDescribe());
                float itemX=firstWidth / 2 + (mLineWidth + mLinePadding * 2 + mStepIconWidth) * i+mOffset;
                //画圆
                canvas.drawCircle(itemX,mStepIconWidth/2,mStepIconWidth/2,stepViewBean.isFinished()?mFinishPaint:mUnfinishPaint);

                canvas.drawText(stepViewBean.getDescribe(),itemX-textWidth/2,mStepIconWidth*2f,mTextPaint);
                if(i!=mDatas.size()-1) {//最后一行不画线
                    canvas.drawLine(itemX + mLinePadding + mStepIconWidth / 2, mStepIconWidth / 2,itemX + mLinePadding  + mStepIconWidth / 2+ mLineWidth, mStepIconWidth / 2, stepViewBean.isFinished() ? mFinishPaint : mUnfinishPaint);
                }
            }

        }
    }

    public void initData(ArrayList<StepViewBean> datas){
        mDatas.clear();
        if(datas!=null){
            mDatas.addAll(datas);
        }
        invalidate();
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }




    float downX;
    float mOffset;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveX=event.getX();
                mOffset = moveX - downX;//滑动时的偏移量，用于计算每个是数据源文字的坐标值
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mOffset =0;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }


}
