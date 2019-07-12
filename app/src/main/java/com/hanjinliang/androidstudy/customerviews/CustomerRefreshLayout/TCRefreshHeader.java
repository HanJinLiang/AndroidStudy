package com.hanjinliang.androidstudy.customerviews.CustomerRefreshLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Created by monkey on 2019-07-09 16:45.
 * Describe:
 */

public class TCRefreshHeader extends LinearLayout implements RefreshHeader {
    LottieAnimationView mProgressView;
    public void initView(Context context){
        setGravity(Gravity.CENTER_HORIZONTAL);
        mProgressView = new LottieAnimationView(context);
        mProgressView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mProgressView.setAnimation("data1.json");
        mProgressView.playAnimation();
        mProgressView.setRepeatCount(ValueAnimator.INFINITE);
        mProgressView.useHardwareAcceleration();
        addView(mProgressView);
      //  Glide.with(context).load(R.drawable.loading).asGif().into(mProgressView);
    }

    public TCRefreshHeader(Context context) {
        this(context,null);
    }
    public TCRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public TCRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }


    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
       // mProgressDrawable.start();//开始动画
    }
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
     //   mProgressDrawable.stop();//停止动画
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        LogUtils.e("newState="+newState);
        //GifDrawable drawable = (GifDrawable) mProgressView.getDrawable();
        switch (newState) {
            case None:
            case PullDownToRefresh:
                //drawable.stop();
                mProgressView.setProgress(0);
                mProgressView.pauseAnimation();
                break;
            case Refreshing:
                //drawable.getDecoder().resetFrameIndex();
                //drawable.start();
                mProgressView.playAnimation();
                break;
            case ReleaseToRefresh://显示箭头改为朝上
                break;
        }
    }


}
