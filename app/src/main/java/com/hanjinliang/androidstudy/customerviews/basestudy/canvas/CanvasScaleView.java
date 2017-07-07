package com.hanjinliang.androidstudy.customerviews.basestudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by HanJinLiang on 2017-07-06.
 * http://www.gcssloop.com/customview/Canvas_Convert
 */

public class CanvasScaleView extends View {
    Paint mPaint;
    RectF mRect;
    public CanvasScaleView(Context context) {
        this(context,null);
    }

    public CanvasScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(SizeUtils.dp2px(5));
        mPaint.setColor(Color.BLACK);

        mRect=new RectF(-500,-500,500,500);   // 矩形区域
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //坐标平移到屏幕中间
        canvas.translate(getWidth()/2,getHeight()/2);
        for (int i=0; i<=20; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(mRect,mPaint);
        }
    }
}
