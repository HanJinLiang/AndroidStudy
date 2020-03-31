package com.hanjinliang.androidstudy.customerviews.basestudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by HanJinLiang on 2017-07-06.
 * http://www.gcssloop.com/customview/Canvas_Convert
 */

public class RotateView extends View {
    Paint mPaint;
    public RotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
        //画大圆
        canvas.drawCircle(0,0,400,mPaint);
        //画小圆
        canvas.drawCircle(0,0,350,mPaint);
        //每次旋转10° 画两个圆的联系
        for(int i=0;i<360/10;i++){
            canvas.drawLine(0,-350,0,-400,mPaint);
            canvas.rotate(10);//每次旋转10  会和上一次效果叠加
        }
    }
}
