package com.hanjinliang.androidstudy.customerviews.LineView.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2018-08-07.
 */

public class SimpleLineView extends View {
    Paint mXTextPaint;
    Paint mLinePaint;

    private int mCircleRadius;
    private int mMinBottomPadding;
    //测量拿到X轴文字高度矩形
    private Rect mRect=new Rect();
    private int mWidth;
    private int mHeight;
    private int mIndexCount=4;//曲线一共分几等份
    private int mXTopPadding;//X轴文字离曲线高度
    private int mLineYHeight;//曲线部分高度
    private int mLineLeftAndRightPadding;//曲线左右边界
    private int mLineTopPadding;//曲线上边界
    private int mTopLineOffset;//第一条线给的一个偏移量   不然第一条线显示一半
    public SimpleLineView(Context context) {
        this(context,null);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mXTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mXTextPaint.setColor(Color.BLACK);
        mXTextPaint.setStyle(Paint.Style.FILL);
        mXTextPaint.setTextSize(30);
        mXTextPaint.setTextAlign(Paint.Align.CENTER);

        mLinePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#ff6632"));
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setStrokeWidth(dip2px(2));

        mCircleRadius=dip2px(2);//点的半径
        mXTopPadding=dip2px(10);
        mLineLeftAndRightPadding=dip2px(20);
        mLineTopPadding= dip2px(20);
        mTopLineOffset=dip2px(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        // FIXME 随便测量拿到X轴文字高度  不够精确
        mXTextPaint.getTextBounds("text测试",0,"text测试".length(),mRect);

        mMinBottomPadding=(mHeight-mRect.height()-mXTopPadding)/mIndexCount;
        mLineYHeight=(mIndexCount-1)*mMinBottomPadding-mLineTopPadding;

        computeData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLinePaint.setAlpha(255);
        //画横线
        mXTextPaint.setColor(Color.parseColor("#2b2b2b"));
        for(int i=0;i<mIndexCount+1;i++){
            canvas.drawLine(0,  mTopLineOffset+mMinBottomPadding*i,mWidth,  mTopLineOffset+mMinBottomPadding*i,mXTextPaint);
        }

        for(int i=0;i<mDatas.size();i++){
            SimpleLineData simpleLineData = mDatas.get(i);
            if(simpleLineData.isDrawValue()) {
                //X轴标注
                mXTextPaint.setColor(Color.parseColor("#2b2b2b"));
                canvas.drawText(simpleLineData.getxValueText(), mLineLeftAndRightPadding+i * (mWidth-mLineLeftAndRightPadding*2) / (mDatas.size()-1), mHeight - mRect.height() / 2, mXTextPaint);
                //Y轴值
                mXTextPaint.setColor(Color.parseColor("#ff6632"));
                canvas.drawText(simpleLineData.getyValue()+"%", mPoints.get(i).x, mPoints.get(i).y-dip2px(3), mXTextPaint);

            }
            //画点
            canvas.drawCircle(mPoints.get(i).x,mPoints.get(i).y,mCircleRadius,mLinePaint);

        }
        //连线
        mLinePaint.setStrokeWidth(dip2px(1));
        canvas.drawPath(mLinePath,mLinePaint);
        //阴影
        mLinePaint.setAlpha(50);
        canvas.drawPath(mFillPath,mLinePaint);
    }

    private float mMinValue,mMaxValue;
    private ArrayList<PointF> mPoints=new ArrayList<>();
    private Path mLinePath=new Path();
    private Path mFillPath=new Path();
    private void computeData() {
        mMinValue=mDatas.get(0).getyValue();
        mMaxValue=mDatas.get(0).getyValue();
        //求最大值 最小值
        for(int i=0;i<mDatas.size();i++){
            SimpleLineData simpleLineData = mDatas.get(i);
            if(simpleLineData.getyValue()>mMaxValue){
                mMaxValue=simpleLineData.getyValue();
            }
            if(simpleLineData.getyValue()<mMinValue){
                mMinValue=simpleLineData.getyValue();
            }
        }
        mPoints.clear();
        mLinePath.reset();
        mFillPath.reset();
        for(int i=0;i<mDatas.size();i++){
            mPoints.add(new PointF(mLineLeftAndRightPadding+i * (mWidth-mLineLeftAndRightPadding*2) / (mDatas.size()-1),
                            (mMaxValue-mDatas.get(i).getyValue())/(mMaxValue-mMinValue)*mLineYHeight+mLineTopPadding+mTopLineOffset));
            if(i==0){
                mLinePath.moveTo(mPoints.get(i).x,mPoints.get(i).y);
            }else{
                mLinePath.lineTo(mPoints.get(i).x,mPoints.get(i).y);
            }
        }
        mFillPath.addPath(mLinePath);
        mFillPath.lineTo((mWidth-mLineLeftAndRightPadding),mLineYHeight+mMinBottomPadding+mLineTopPadding);
        mFillPath.lineTo(mLineLeftAndRightPadding,mLineYHeight+mMinBottomPadding+mLineTopPadding);
        mFillPath.close();
    }



    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale =getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    ArrayList<SimpleLineData> mDatas=new ArrayList<>();
    public void setData(ArrayList<SimpleLineData> datas) {
        mDatas=datas;
        computeData();
        postInvalidate();
    }

}
