package com.hanjinliang.androidstudy.customerviews.ArcMenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-07-13.
 */
public class ArcMenu extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    ImageView mControlView;
    private int[] mMenuRes=new int[]{R.mipmap.menu_edit,R.mipmap.menu_edit,R.mipmap.menu_like,R.mipmap.menu_search,R.mipmap.menu_send};
    //菜单是否展开的flag,false表示没展开
    private boolean isOpen = false;
    public int mMenuCount=5;


    private int mMenuButtonWidth=dp2px(50);
    private int mControlButtonWidth=dp2px(60);
    private int mArcRadius=dp2px(150);
    private int mRightMargin=dp2px(15);
    public ArcMenu(@NonNull Context context) {
        this(context,null);

    }

    public ArcMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }



    public ArcMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext=context;

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
//
//        int minWidth=mArcRadius+mRightMargin+mControlButtonWidth/2+mMenuButtonWidth/2;
//
//        if(heightSize<minWidth||widthSize<minWidth){
//            setMeasuredDimension(minWidth,minWidth);
//        }else{
//            setMeasuredDimension(Math.min(widthSize,heightSize),Math.min(widthSize,heightSize));
//        }
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        addView();
    }

    private void addView(){
        for (int i = 0; i <mMenuCount; i++) {
            ImageView imageView =new ImageView(mContext);
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(mMenuButtonWidth,mMenuButtonWidth);
            params.gravity= Gravity.BOTTOM|Gravity.RIGHT;
            params.setMargins(0,0,mRightMargin,mRightMargin);
            imageView.setImageResource(mMenuRes[i]);
            imageView.setLayoutParams(params);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
            addView(imageView);
        }
        mControlView=new ImageView(mContext);
        mControlView.setTag(-1);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(mControlButtonWidth,mControlButtonWidth);
        params.gravity= Gravity.BOTTOM|Gravity.RIGHT;
        int marginsOffset=(mControlButtonWidth-mMenuButtonWidth)/2;
        params.setMargins(0,0,mRightMargin-marginsOffset,mRightMargin-marginsOffset);
        mControlView.setLayoutParams(params);
        mControlView.setOnClickListener(this);
        mControlView.setImageResource(R.mipmap.menu_control);
        addView(mControlView);
    }

    @Override
    public void onClick(View v) {
        int index=(int)v.getTag();
        switch (index) {
            case -1://操作按钮
                if (isOpen) {
                    showExitAnim();
                }else{
                    showEnterAnim(); //100为扇形半径dp值
                }
                break;
            default:
                if(mOnMenuClickListener!=null){
                    mOnMenuClickListener.onMenuClick(index);
                }
                break;
        }
    }

    OnMenuClickListener mOnMenuClickListener;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener{
        public void onMenuClick(int index);
    }


    //显示扇形菜单的属性动画
    private void showEnterAnim() {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < mMenuCount; i++) {
            AnimatorSet set = new AnimatorSet();
            double x = -Math.cos(Math.toRadians(90/(mMenuCount-1)*i))*mArcRadius;
            double y = -Math.sin(Math.toRadians(90/(mMenuCount-1)*i))*mArcRadius;
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationX", 0,(float)x),
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationY", 0,(float)y)
                    ,ObjectAnimator.ofFloat(imageViews.get(i),"alpha",0,1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100*i);
            set.start();
        }
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mControlView,"rotation",0,45).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置打开
        isOpen = true;
    }

    //关闭扇形菜单的属性动画
    private void showExitAnim() {
        //for循环来开始小图标的关闭动画
        for (int i = 0; i <mMenuCount; i++) {
            AnimatorSet set = new AnimatorSet();
            double x = -Math.cos(Math.toRadians(90/(mMenuCount-1)*i))*mArcRadius;
            double y = -Math.sin(Math.toRadians(90/(mMenuCount-1)*i))*mArcRadius;
            set.playTogether(
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationX",(float) x,0),
                    ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float) y,0)
                    ,ObjectAnimator.ofFloat(imageViews.get(i),"alpha",1,0).setDuration(2000)
            );
            set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(300).setStartDelay(50*i);
            set.start();
        }
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mControlView,"rotation",45,0).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

        //菜单状态置关闭
        isOpen = false;
    }


    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    private  int dp2px(float dpValue) {
        final float scale=getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
