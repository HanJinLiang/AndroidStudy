package com.hanjinliang.androidstudy.customerviews.LoadingView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2018-03-22.
 *
 * 圆形进度条
 */

public class CircleLoadingView extends View {

    Paint mCirclePaint;
    int mCircleGap=dip2px(10);//圆环之间的间隙
    int[] mCircleColors=new int[]{Color.parseColor("#61b6f6"),Color.parseColor("#a7c57b")
            ,Color.parseColor("#4572ed"),Color.parseColor("#daa030")};
    int mCircleWidth=dip2px(5);//弧形宽度

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
       // mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
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
            //if(i!=0){continue;}
            mCirclePaint.setColor(mCircleColors[i]);
            float rectStartX=i*(mCircleWidth+mCircleGap)+mCircleWidth/2;
            float rectEndX=getWidth() -i*(mCircleGap+mCircleWidth)-mCircleWidth/2+1;
            Log.e("onDraw","mCircleWidth="+mCircleWidth+"--getWidth()="+getWidth()+"--rectStartX="+rectStartX+"--rectEndX="+rectEndX);
            //画弧形
            canvas.drawArc(new RectF(rectStartX,rectStartX,rectEndX,rectEndX)
            ,mCircleStartAngle[i]+(i%2==0?-mOffsetAngle:mOffsetAngle),mCircleSweepAngle[i],false,mCirclePaint);
        }
    }

    private boolean isLoading;
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

    public void finish(){
        mOffsetAngle=0;
        isLoading=false;
        postInvalidate();
    }

}
