package com.hanjinliang.androidstudy.customerviews.CustomerProgressBar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.blankj.utilcode.util.SizeUtils;

import androidx.annotation.Nullable;


/**
 * Created by HanJinLiang on 2017-06-30.
 * 自定义加载view
 * http://www.jianshu.com/p/3eb9777f6ab7
 */

public class CustomerLoadingView extends View {

    private String mBtnText="确认提交";
    private int mWidth,mHeight;
    private Paint mTxtPaint;//绘制文字
    private Paint mPaint;
    private Paint mOkPaint;
    Rect mTxtRect;
    public CustomerLoadingView(Context context) {
        this(context,null);
    }

    public CustomerLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomerLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTxtPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setTextSize(SizeUtils.sp2px(16));
        mTxtPaint.setColor(Color.WHITE);
        //计算绘制文字需要空间
        mTxtRect=new Rect();
        mTxtPaint.getTextBounds(mBtnText,0,mBtnText.length(),mTxtRect);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.CYAN);


        mOkPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mOkPaint.setStyle(Paint.Style.STROKE);
        mOkPaint.setColor(Color.WHITE);
        mOkPaint.setStrokeWidth(SizeUtils.dp2px(2));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onClick();
                }
            }
        });

    }

    ClickListener mClickListener;
    public void setClickListener(ClickListener clickListener){
        mClickListener=clickListener;
    }

    interface ClickListener{
        public void onClick();
        public void onAnimFinish();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        //计算宽度
        if(widthMode==MeasureSpec.EXACTLY){//MatchParent
            mWidth=widthSize;
        }else{//默认高度
            mWidth=SizeUtils.dp2px(200);//默认宽度200dp
        }

        //计算高度
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            mHeight=heightSize;
        }else{//默认高度
            mHeight=SizeUtils.dp2px(50);//默认高度50dp
        }
        //高度大于宽度时   高度等于宽度
        if(mHeight>mWidth){
            mHeight=mWidth;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        startAnim();
        super.setOnClickListener(l);
    }

    RectF mRectF;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRectF=new RectF(0,0,getWidth(),getHeight());
        initAnimation();
    }

    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();
    private void initAnimation() {
        startToRound();//矩形变为圆角
        startToCircle();//收缩变成圆形
        upView();//上移
        //对勾
        initOk();
        set_draw_ok_animation();
        animatorSet.play(mUpAnimator)
                .with(mOkAnimator)
                .after(mCircleAnimator)
                .after(mRoundAnimator);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mClickListener!=null){
                    mClickListener.onAnimFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 开始动画
     */
    public void startAnim(){
        if(animatorSet.isRunning()){
            return;
        }
        animatorSet.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(mRectXOffset,0,getWidth()-mRectXOffset,getHeight());
        canvas.drawRoundRect(mRectF,mRoundRadius,mRoundRadius,mPaint);
        if(mRoundRadius==0) {//直角时候才画文字
            canvas.drawText(mBtnText, mWidth / 2 - mTxtRect.width() / 2, mHeight / 2 + mTxtRect.height() / 2, mTxtPaint);
        }
        if (startDrawOk) {//是否开始话对勾
            canvas.drawPath(mPath, mOkPaint);
        }
    }
    private  int mRoundRadius=0;
    ValueAnimator mRoundAnimator;
    public void startToRound(){
            mRoundAnimator=ValueAnimator.ofInt(0,getHeight()/2);
            mRoundAnimator.setDuration(500);
            mRoundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRoundRadius= (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
    }

    ValueAnimator mCircleAnimator;
    private int mRectXOffset;
    public void startToCircle(){
            mCircleAnimator=ValueAnimator.ofInt(0,getWidth()/2-getHeight()/2);//X方向缩小的值
            mCircleAnimator.setDuration(1000);
            mCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRectXOffset= (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
    }
    ValueAnimator mUpAnimator;
    private void upView(){
            mUpAnimator= ObjectAnimator.ofFloat(this,"translationY",this.getTranslationY(),this.getTranslationY()-SizeUtils.dp2px(100));//上移100dp
            mUpAnimator.setInterpolator(new ElasticOutInterpolator());
            mUpAnimator.setDuration(1000);
    }

    /**
     * 弹性插值器
     */
    public class ElasticOutInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float t) {
            if (t == 0) return 0;
            if (t >= 1) return 1;
            float p=.3f;
            float s=p/4;
            return ((float)Math.pow(2,-10*t) * (float)Math.sin( (t-s)*(2*(float)Math.PI)/p) + 1);
        }
    }

    Path mPath=new Path();
    PathMeasure mPathMeasure;
    /**
     * 绘制对勾
     * 下边计算比例是参考网上一些例子加上自己一步一步尝试的出来的比例，仅供参考
     * 如果条件允许最好还是让设计师给你标明一下比例哦！
     */
    private void initOk() {
        //对勾的路径
        mPath.moveTo((mWidth-mHeight)/2+ mHeight / 8 * 3, mHeight / 2);
        mPath.lineTo((mWidth-mHeight)/2+ mHeight / 2, mHeight / 5 * 3);
        mPath.lineTo((mWidth-mHeight)/2+ mHeight / 3 * 2, mHeight / 5 * 2);

        mPathMeasure = new PathMeasure(mPath, true);
    }
    ValueAnimator mOkAnimator;
    boolean startDrawOk;
    DashPathEffect effect;
    /**
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        mOkAnimator = ValueAnimator.ofFloat(1, 0);
        mOkAnimator.setDuration(1000);
        mOkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();

                 effect = new DashPathEffect(new float[]{mPathMeasure.getLength(), mPathMeasure.getLength()}, value * mPathMeasure.getLength());
                 mOkPaint.setPathEffect(effect);
                invalidate();
            }
        });
    }
}
