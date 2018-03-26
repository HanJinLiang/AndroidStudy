package com.hanjinliang.androidstudy.customerviews.LoadingView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018-03-22.
 *
 * 圆形进度条
 */

public class CircleLoadingView extends View {

    Paint mCirclePaint;
    int mCircleGap=dip2px(8);//圆环之间的间隙
    int[] mCircleColors=new int[]{Color.parseColor("#30a0da"),Color.parseColor("#ed7245")
            ,Color.parseColor("#7bc5a7"),Color.parseColor("#f6b661")};
    int mCircleWidth=dip2px(2);//弧形宽度

    int[] mCircleStartAngle=new int[]{80,135,-90,90 };//开始的角度
    int[] mCircleSweepAngle=new int[]{315,270,270,315 };//扫过的角度

    int mOffsetAngle=0;
    int mSpeed=3;


    public CircleLoadingView(Context context) {
        this(context,null);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mCirclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleWidth);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
    }


    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<mCircleColors.length;i++) {
            mCirclePaint.setColor(mCircleColors[i]);
            float rectStartX=i*(mCircleWidth+mCircleGap)+mCircleWidth/2;
            float rectEndX=getWidth() -i*(mCircleGap+mCircleWidth)-mCircleWidth/2;
            mOffsetAngle+=i;//调整速度 每一个圈速度不一样
            //画弧形
            canvas.drawArc(new RectF(rectStartX,rectStartX,rectEndX,rectEndX)
            ,mCircleStartAngle[i]+(i%2==0?-mOffsetAngle:mOffsetAngle),mCircleSweepAngle[i],false,mCirclePaint);
        }
    }

    private boolean isLoading;

    /**
     * 开始加载
     */
    public void loading(){
            if(isLoading){
                return;
            }
            isLoading=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isLoading) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mOffsetAngle+=mSpeed;
                        if (mOffsetAngle >= 360)//大于360
                        {
                            mOffsetAngle = 0;
                        }
                        postInvalidate();
                    }
                }
            }).start();
    }

    /**
     * 结束加载
     */
    public void finish(){
        mOffsetAngle=0;
        isLoading=false;
        postInvalidate();
    }

    /**
     * 设置速度
     * @param speed
     */
    public void setSpeed(int speed) {
        mSpeed = speed;
    }
}
