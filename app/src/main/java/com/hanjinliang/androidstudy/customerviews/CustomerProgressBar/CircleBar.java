package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import java.text.DecimalFormat;

public class CircleBar extends View {

    private RectF mColorWheelRectangle = new RectF();//定义一个矩形,包含矩形的四个单精度浮点坐标
    private Paint mDefaultWheelPaint;
    private Paint mColorWheelPaint;//进度条的画笔
    private Paint mTextPaint;//文字

    private float circleStrokeWidth;
    private float mSweepAnglePer;
    private int mProgress;
    private int mMaxProgress = 100;// 默认最大值

    int mCircleColor;
    int mCircleBgColor;
    private float mCircleRatio=0.05f;//圆环宽度占比
    private boolean mIsDrawValue=false;

    public CircleBar(Context context) {
        this(context,null);
    }

    public CircleBar(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init( context);
    }
    int baseLineY;
    private void init(Context context) {
        //关闭硬件加速
        setLayerType( LAYER_TYPE_SOFTWARE , null);

        //圆的颜色
        mCircleColor=Color.parseColor("#30E1A6");

        //圆的背景颜色
        mCircleBgColor=Color.parseColor("#F2F2F2");


        mColorWheelPaint = new Paint();
        mColorWheelPaint.setColor(mCircleColor);//设置画笔颜色
        mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心,只绘制轮廓线
        mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
        mColorWheelPaint.setAntiAlias(true);// 去锯齿


        mDefaultWheelPaint = new Paint();
        mDefaultWheelPaint.setColor(mCircleBgColor);//背景色
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
        mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);
        mDefaultWheelPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mCircleColor);//百分比
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize( sp2px(context,16));//字体大小
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px( Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * oval是RecF类型的对象，其定义了椭圆的形状
         * startAngle指的是绘制的起始角度，钟表的3点位置对应着0度，如果传入的startAngle小于0或者大于等于360，那么用startAngle对360进行取模后作为起始绘制角度。
         * sweepAngle指的是从startAngle开始沿着钟表的顺时针方向旋转扫过的角度。如果sweepAngle大于等于360，那么会绘制完整的椭圆弧。如果sweepAngle小于0，那么会用sweepAngle对360进行取模后作为扫过的角度。
         * useCenter是个boolean值，如果为true，表示在绘制完弧之后，用椭圆的中心点连接弧上的起点和终点以闭合弧；如果值为false，表示在绘制完弧之后，弧的起点和终点直接连接，不经过椭圆的中心点。
         */
        canvas.drawArc(mColorWheelRectangle, 0, 359, false, mDefaultWheelPaint);
        canvas.drawArc(mColorWheelRectangle, 270, mSweepAnglePer, false, mColorWheelPaint);
        if(mIsDrawValue) {
            canvas.drawText(mProgress + "%", getWidth() / 2, baseLineY, mTextPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initCircleWidth();
        //文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        //保证文字垂直居中
        baseLineY = (int) (mColorWheelRectangle.centerY()- top/2 - bottom/2);//基线中间点的y轴计算公式
    }

    /**
     * 设置圆环的宽度
     */
    private void initCircleWidth() {
        circleStrokeWidth = getWidth()*mCircleRatio;// 圆弧的宽度
        mColorWheelRectangle.set(circleStrokeWidth , circleStrokeWidth , getWidth() - circleStrokeWidth , getWidth() - circleStrokeWidth);// 设置矩形
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
        //mColorWheelPaint.setShadowLayer(circleStrokeWidth, 0, 0,mCircleColor);// 设置阴影

        mColorWheelPaint.setMaskFilter(new BlurMaskFilter(circleStrokeWidth/2, BlurMaskFilter.Blur.SOLID));//设置发光效果
    }

    /**
     * 更新进度
     * @param progress
     */
    public void update(int progress) {
        update(progress,false);
    }
    ValueAnimator valueAnimator;
    /**
     * 更新进度
     * @param progress
     */
    public void update(int progress,boolean isNeedAnim) {
        update(progress,isNeedAnim,1000);
    }

    /**
     * 更新进度
     * @param progress
     */
    public void update(int progress,boolean isNeedAnim,long duration) {
        if(isNeedAnim){//需要动画
            valueAnimator= ValueAnimator.ofInt(0,progress).setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress= (int) animation.getAnimatedValue();
                    mSweepAnglePer = mProgress * 360 / mMaxProgress;
                    postInvalidate();
                }
            });
            valueAnimator.start();
        }else{
            if(valueAnimator!=null&&valueAnimator.isRunning()){//取消动画
                valueAnimator.cancel();
            }
            mProgress=progress;
            mSweepAnglePer = mProgress * 360 / mMaxProgress;
            postInvalidate();
        }
    }

    /**
     * 设置画笔颜色
     * @param circleColor
     */
    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
        mColorWheelPaint.setColor(mCircleColor);
        postInvalidate();
    }

    /**
     * 设置圆环宽度占比
     * @param circleRatio
     */
    public void setCircleRatio(float circleRatio) {
        mCircleRatio = circleRatio;
        initCircleWidth();
        postInvalidate();
    }

    /**
     * 设置是否绘制中间文字  默认不绘制
     * @param drawValue
     */
    public void setDrawValue(boolean drawValue) {
        mIsDrawValue = drawValue;
        postInvalidate();
    }
}
