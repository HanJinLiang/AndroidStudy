package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${lwh} on 2018/3/1.
 *
 * @descirbe 进度条view
 */

public class TaskProgressView extends View {
    private Paint mProgressPaint;
    private Paint mProgressPaint1;
    private int mPercent=100;
    private int mProgressHeight;//进度条高度

    private float mRadius;//圆角
    private Path mProgressPath=new Path();
    private Path mLinePath=new Path();
    public TaskProgressView(Context context) {
        this(context,null);
    }

    public TaskProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TaskProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 1、设置禁用硬件设置
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        init();
    }

    private void init() {

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setColor(Color.parseColor("#FF788321"));
        mProgressPaint.setAntiAlias(true);

        mProgressPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint1.setStyle(Paint.Style.FILL);
        mProgressPaint1.setColor(Color.parseColor("#FFFFFFFF"));
        mProgressPaint1.setAntiAlias(true);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int height= mProgressHeight;//默认值
        if(heightMode==MeasureSpec.EXACTLY){//不是明确的值
            height=Math.max(height,heightSize);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),height);//高度保证能够正常显示
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mProgressHeight=dip2px(10);//进度条高度   单位dp
        mRadius=mProgressHeight;


        mProgressPath.reset();
        mProgressPath.moveTo(0,0);
        mProgressPath.lineTo(getWidth(),0);
        mProgressPath.arcTo(new RectF(getWidth()-2*mRadius,-mRadius,getWidth(),mRadius),0,90);
        mProgressPath.lineTo(mRadius,mRadius);
        mProgressPath.arcTo(new RectF(0,-mRadius,2*mRadius,mRadius),90,90);
        mProgressPath.lineTo(0,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mLinePath.reset();
        mLinePath.moveTo(0,0);
        mLinePath.lineTo(getWidth()* mPercent / 100,0);
        mLinePath.lineTo(getWidth()* mPercent / 100,getHeight());
        mLinePath.lineTo(0,getHeight());
        mLinePath.close();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLinePath.op(mProgressPath, Path.Op.INTERSECT);
        }else{
            /**
             * Api 19下采用画布的clip实现
             */
            canvas.clipPath(mProgressPath, Region.Op.INTERSECT);
        }
        mProgressPaint.setShader(new LinearGradient(0,0,getWidth()* mPercent / 100,getWidth()* mPercent / 100,new int[]{Color.YELLOW,Color.RED}, new float[]{0f,1f}, Shader.TileMode.CLAMP));
        canvas.drawPath(mLinePath,mProgressPaint);
    }

    /**
     * 设置进度
     * @param percent
     */
    public void setPercent(int percent) {
        if(percent<0){
            percent=0;
        }else if(percent>100){
            percent=100;
        }
        this.mPercent = percent;
        postInvalidate();
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
}
