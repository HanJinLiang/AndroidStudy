package com.hanjinliang.androidstudy.customerviews.basestudy.patheffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HanJinLiang on 2017-07-03.
 * paint  PathEffect 学习
 * http://www.runoob.com/w3cnote/android-tutorial-patheffect.html
 */

public class PathEffectView extends View {
    private Paint mPaint;
    private Path mPath;

    private PathEffect[] effects = new PathEffect[7];
    private int[] colors;

    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化画笔
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);       //绘画风格:空心
        mPaint.setStrokeWidth(5);                  //笔触粗细
        mPath = new Path();
        mPath.moveTo(0, 0);
        for (int i = 1; i <= 15; i++) {
            // 生成15个点，随机生成它们的坐标，并将它们连成一条Path
            mPath.lineTo(i * 80, (float) Math.random() * 150);
        }
        // 初始化7个颜色
        colors = new int[] { Color.RED, Color.BLUE, Color.GREEN,
                Color.YELLOW, Color.BLACK, Color.MAGENTA, Color.CYAN };

        mPathMeasure=new PathMeasure(mPath,false);
        phase=mPathMeasure.getLength();
    }
    PathMeasure mPathMeasure;
    private float phase = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //初始化其中路径效果：
        effects[0] = null;                                    //无效果
        effects[1] = new CornerPathEffect(10);                //CornerPathEffect
        effects[2] = new DiscretePathEffect(3.0f, 5.0f);      //DiscretePathEffect
        effects[3] = new DashPathEffect(new float[] { 20, 10, 5, 10 },phase);   //DashPathEffect  20实线 10虚线 5虚线 10实线...   phase表示偏移量
        Path p = new Path();
        p.addRect(0, 0, 8, 8, Path.Direction.CCW);
        effects[4] = new PathDashPathEffect(p, 40, phase,
                PathDashPathEffect.Style.ROTATE);             //PathDashPathEffect
        effects[5] = new ComposePathEffect(effects[2], effects[4]);    //ComposePathEffect
        effects[6] = new SumPathEffect(effects[2], effects[4]);   //SumPathEffect
        // 将画布移动到(10,10)处开始绘制
        canvas.translate(10, 10);
        // 依次使用7中不同的路径效果、7中不同的颜色来绘制路径
        for (int i = 0; i < effects.length; i++) {
            mPaint.setPathEffect(effects[i]);
            mPaint.setColor(colors[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 250);
        }
        // 改变phase值，形成动画效果
//        phase +=2;
//        invalidate();
    }
}
