package com.hanjinliang.androidstudy.customerviews.grayapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by monkey on 2020-04-07 13:25.
 * Describe:灰色处理的自定义FrameLayout  用来替代id/content的
 * 但是在视频播放等有问题  https://mp.weixin.qq.com/s/8fTWLYaPhi0to47EUmFd7A
 */
public class GrayFrameLayout extends FrameLayout {
    private Paint mPaint = new Paint();

    public GrayFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public GrayFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GrayFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置饱和度  设置成灰色
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

}
