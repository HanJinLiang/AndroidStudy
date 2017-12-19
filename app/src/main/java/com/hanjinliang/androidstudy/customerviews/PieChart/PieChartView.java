package com.hanjinliang.androidstudy.customerviews.PieChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HanJinLiang on 2017-12-19.
 */

public class PieChartView extends View {
    ArrayList<PieBeans> mDatas=new ArrayList<>();
    HashMap<String,Path> mPaths=new HashMap<>();
    private int mCircleRadius;
    private int mLineWidth;
    private int mTextPaddingWidth;
    private Paint mPiePaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCircleRadius= SizeUtils.dp2px(50);
        mLineWidth=mCircleRadius/2;
        mTextPaddingWidth=SizeUtils.dp2px(2);

        mPiePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setColor(Color.RED);
        mPiePaint.setStyle(Paint.Style.FILL);

        mLinePaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.FILL);

        mTextPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
//        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(SizeUtils.sp2px(12));
    }
    RectF mRectF;
    Rect mRect;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF =new RectF(w/2-mCircleRadius,h/2-mCircleRadius,w/2+mCircleRadius,h/2+mCircleRadius);
        mRect =new Rect(w/2-mCircleRadius,h/2-mCircleRadius,w/2+mCircleRadius,h/2+mCircleRadius);
    }

    public void setDatas(ArrayList<PieBeans> datas) {
        mDatas = datas;
        invalidate();
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
    float mTotalPercent;
    Path mPath;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        mTotalPercent=0;
        for(int i=0;i<mDatas.size();i++){
            if(mClickIndex==i){
                mCircleRadius= SizeUtils.dp2px(53);
            }else{
                mCircleRadius= SizeUtils.dp2px(50);
            }
            PieBeans pieBeans=mDatas.get(i);
            mPiePaint.setColor(pieBeans.getColor());
            if(mPaths.get(pieBeans.getText())==null){
                mPaths.put(pieBeans.getText(),new Path());
            }

            mRectF.set(getWidth()/2-mCircleRadius,getHeight()/2-mCircleRadius,getWidth()/2+mCircleRadius,getHeight()/2+mCircleRadius);
            mPath=mPaths.get(pieBeans.getText());
            mPath.reset();
            mPath.moveTo(mRectF.centerX(), mRectF.centerY());
            mPath.addArc(mRectF,mTotalPercent,pieBeans.getPercent()*3.6f);
            mPath.lineTo(mRectF.centerX(), mRectF.centerY());
            canvas.drawPath(mPath,mPiePaint);

//            canvas.drawArc(mRectF,mTotalPercent,pieBeans.getPercent()*3.6f,true,mPiePaint);
            float textWidth=mTextPaint.measureText(pieBeans.getText());
            //文字
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom


            if(mTotalPercent+pieBeans.getPercent()*1.8<90) {//右下

                float inLineY = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8)) * mCircleRadius * 9 / 10);
                float inLineX = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8)) * mCircleRadius * 9 / 10);

                float outLineY = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8)) * mCircleRadius * 11 / 10);
                float outLineX = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8))* mCircleRadius * 11 / 10);

                canvas.drawLine(inLineX+ mRectF.centerX(), inLineY+ mRectF.centerY(), outLineX+ mRectF.centerX(), outLineY+ mRectF.centerY(), mLinePaint);
                canvas.drawLine(outLineX+ mRectF.centerX(), outLineY+ mRectF.centerY(), outLineX+ mRectF.centerX()+mLineWidth, outLineY+ mRectF.centerY(), mLinePaint);
                canvas.drawText(pieBeans.getText(),outLineX+ mRectF.centerX()+mLineWidth+mTextPaddingWidth, outLineY+ mRectF.centerY()- top/2 - bottom/2,mTextPaint);
            }else if(mTotalPercent+pieBeans.getPercent()*1.8>=90&&mTotalPercent+pieBeans.getPercent()*1.8<180){//左下
                float inLineY = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-90)) * mCircleRadius * 9 / 10);
                float inLineX = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-90)) * mCircleRadius * 9 / 10);

                float outLineY = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-90)) * mCircleRadius * 11 / 10);
                float outLineX = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-90))* mCircleRadius * 11 / 10);


                canvas.drawLine(mRectF.centerX()-inLineX, inLineY+ mRectF.centerY(), mRectF.centerX()-outLineX, outLineY+ mRectF.centerY(), mLinePaint);
                canvas.drawLine(mRectF.centerX()-outLineX, outLineY+ mRectF.centerY(), mRectF.centerX()-outLineX-mLineWidth, outLineY+ mRectF.centerY() , mLinePaint);
                canvas.drawText(pieBeans.getText(), mRectF.centerX()-outLineX-mLineWidth-textWidth-mTextPaddingWidth, outLineY+ mRectF.centerY() - top/2 - bottom/2,mTextPaint);

            }else if(mTotalPercent+pieBeans.getPercent()*1.8>=180&&mTotalPercent+pieBeans.getPercent()*1.8<270){//左上
                float inLineY = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-180)) * mCircleRadius * 9 / 10);
                float inLineX = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-180)) * mCircleRadius * 9 / 10);

                float outLineY = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-180)) * mCircleRadius * 11 / 10);
                float outLineX = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-180))* mCircleRadius * 11 / 10);

                canvas.drawLine(mRectF.centerX()-inLineX, mRectF.centerY()-inLineY, mRectF.centerX()-outLineX, mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawLine(mRectF.centerX()-outLineX, mRectF.centerY()-outLineY, mRectF.centerX()-outLineX-mLineWidth, mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawText(pieBeans.getText(), mRectF.centerX()-outLineX-mLineWidth-textWidth-mTextPaddingWidth, mRectF.centerY()-outLineY - top/2 - bottom/2,mTextPaint);

            }else if(mTotalPercent+pieBeans.getPercent()*1.8>=270&&mTotalPercent+pieBeans.getPercent()*1.8<360){//右上
                float inLineY = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-270)) * mCircleRadius * 9 / 10);
                float inLineX = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-270)) * mCircleRadius * 9 / 10);

                float outLineY = (float) (Math.cos(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-270)) * mCircleRadius * 11 / 10);
                float outLineX = (float) (Math.sin(Math.toRadians(mTotalPercent + pieBeans.getPercent() * 1.8-270))* mCircleRadius * 11 / 10);

                canvas.drawLine(inLineX+ mRectF.centerX(), mRectF.centerY()-inLineY, outLineX+ mRectF.centerX(), mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawLine(outLineX+ mRectF.centerX(), mRectF.centerY()-outLineY, outLineX+ mRectF.centerX()+mLineWidth, mRectF.centerY()-outLineY , mLinePaint);
                canvas.drawText(pieBeans.getText(),outLineX+ mRectF.centerX()+mLineWidth+mTextPaddingWidth, mRectF.centerY()-outLineY - top/2 - bottom/2,mTextPaint);

            }

            mTotalPercent+=(pieBeans.getPercent()*3.6f);

        }

    }

    int mClickIndex=-1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                float x=event.getX();
                float y=event.getY();
//                //点击位置x坐标与圆心的x坐标的距离
//                float distanceX = Math.abs(mRectF.centerX()-x);
//                //点击位置y坐标与圆心的y坐标的距离
//                float distanceY = Math.abs(mRectF.centerY()-y);
//                //点击位置与圆心的直线距离
//                int distanceZ = (int) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
//
//                if(distanceZ<mCircleRadius){
//                    //点击的在圆里面
//                    LogUtils.e("点击的在圆里面");
//                    clickX=x;
//                    clickY=y;
//
//                    invalidate();
//                }
                int index=clickIndex((int)x,(int)y);
                if(index!=-1){
                    mClickIndex=index;
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    Region mRegion=new Region();
    public int clickIndex(int x,int y){
        for(int i=0;i<mDatas.size();i++){
            mRegion.setPath(mPaths.get(mDatas.get(i).getText()),new Region(mRect));
            boolean isContains=mRegion.contains(x,y);
            if(isContains){
                return  i;
            }
        }
        return -1;
    }
}
