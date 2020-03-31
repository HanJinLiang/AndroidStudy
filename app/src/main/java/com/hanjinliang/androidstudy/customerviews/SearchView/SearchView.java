package com.hanjinliang.androidstudy.customerviews.SearchView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.blankj.utilcode.util.SizeUtils;

import androidx.annotation.Nullable;

/**
 * Created by HanJinLiang on 2017-07-07.
 */

public class SearchView extends View{
    private String TAG=this.getClass().getSimpleName();
    private int mWidth,mHeight;
    Paint mPaint;
    Path mSearchPath;

    Path mCirclePath;

    PathMeasure mPathMeasure;
    PathMeasure mCirclePathMeasure;
    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(SizeUtils.dp2px(4));
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        initPath();
        initAnim();
        initListener();
    }

    private void initListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
    }


    private void initPath() {
        mSearchPath=new Path();
        mSearchPath.addArc(new RectF(-50,-50,50,50),45,359.9f);
        int x= (int) (Math.sin(Math.toDegrees(45))*(100-SizeUtils.dp2px(2)));
        mSearchPath.lineTo(x,x);

        mPathMeasure=new PathMeasure(mSearchPath,false);

        mCirclePath=new Path();
        mCirclePath.addArc(new RectF(-100,-100,100,100),45,359.9f);

        mCirclePathMeasure=new PathMeasure(mCirclePath,false);

    }
    float animValue;
    ValueAnimator mStartAnim;
    ValueAnimator mSearchAnim;
    ValueAnimator mFinishAnim;
    private void initAnim() {
        mStartAnim=ValueAnimator.ofFloat(0,1).setDuration(2000);
        mStartAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mStartAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState=State.STARTING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSearchAnim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mSearchAnim=ValueAnimator.ofFloat(0,1).setDuration(2000);
        mSearchAnim.setRepeatMode(ValueAnimator.RESTART);
        mSearchAnim.setRepeatCount(ValueAnimator.INFINITE);
        //mSearchAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mSearchAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mSearchAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState=State.SEARCHING;
                if(mSearchViewListener!=null){
                    mSearchViewListener.startSearching();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFinishAnim.start();//结束后
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        mFinishAnim=ValueAnimator.ofFloat(1,0).setDuration(1000);
        mFinishAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mFinishAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState=State.FINISH;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                reset();//状态恢复
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    SearchViewListener mSearchViewListener;

    public void setSearchViewListener(SearchViewListener searchViewListener) {
        mSearchViewListener = searchViewListener;
    }

    public interface SearchViewListener{
        public void startSearching();
    }

    /**
     * 点击开始搜索
     */
    public void startSearch(){
        if(mState==State.NONE) {//只有在初始状态才支持
            mStartAnim.start();
        }
    }

    private void reset(){
        mState=State.NONE;
        mSearchAnim.setRepeatCount(ValueAnimator.INFINITE);
    }
    /**
     * 搜索完成
     */
    public void searchFinish(){
        if(mState==State.SEARCHING) {//只有搜索中才能完成
            if (mSearchAnim != null && mSearchAnim.isRunning()) {
                //这时候只要设置状态为Finish自动化切入到下一个状态
                mSearchAnim.setRepeatCount(0);
            } else {
                //
                mFinishAnim.start();
            }
        }else{
            Log.e(TAG,"只有在搜索中状态才能完成");
        }
    }

    private State mState=State.NONE;
    /**
     * 枚举状态
     */
    public  enum State{
        NONE,STARTING,SEARCHING,FINISH
    }
    Path mTempPath=new Path();
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawColor(Color.BLUE);

        switch (mState){
            case NONE:
                canvas.drawPath(mSearchPath,mPaint);
                break;
            case STARTING:
                //搜索按钮动画
                mTempPath.reset();
                mPathMeasure.getSegment(mPathMeasure.getLength()*animValue,mPathMeasure.getLength(),mTempPath,true);
                canvas.drawPath(mTempPath,mPaint);
                break;
            case SEARCHING:
                mTempPath.reset();
                mCirclePathMeasure.getSegment(mCirclePathMeasure.getLength()*(1-animValue)-(animValue>0.5?(200*(1-animValue)):(200*animValue)),mCirclePathMeasure.getLength()*(1-animValue),mTempPath,true);
                canvas.drawPath(mTempPath,mPaint);

                break;
            case FINISH:
                mTempPath.reset();
                mPathMeasure.getSegment(mPathMeasure.getLength()*animValue,mPathMeasure.getLength(),mTempPath,true);
                canvas.drawPath(mTempPath,mPaint);
                break;
        }

    }


}
