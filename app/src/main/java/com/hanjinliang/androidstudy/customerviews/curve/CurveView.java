package com.hanjinliang.androidstudy.customerviews.curve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-06-28.
 * 贝塞尔曲线
 */

public class CurveView extends View {
    Path mPath;
    Paint mPaint;
    public CurveView(Context context) {
        this(context,null);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

        mPath=new Path();
    }
    float helpPointX=800;
    float helpPointY=1500;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(400,1000);
        //mPath.quadTo(helpPointX,helpPointY,1000,1000);
        mPath.cubicTo(600,500,helpPointX,helpPointY,1000,1000);
        canvas.drawPath(mPath,mPaint);
        canvas.drawPoint(600,500,mPaint);
        canvas.drawPoint(helpPointX,helpPointY,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                helpPointX=event.getX();
                helpPointY=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                helpPointX=800;
                helpPointY=1500;
                break;
        }
        postInvalidate();
        return true;
    }
}
