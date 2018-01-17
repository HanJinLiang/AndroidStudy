package com.hanjinliang.androidstudy.customerviews.DashLine;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.stepview.CustomStepView;
import com.hanjinliang.androidstudy.customerviews.stepview.StepView;


/**
 * Created by HanJinLiang on 2017-06-30.
 * 自定义加载view
 * http://www.jianshu.com/p/3eb9777f6ab7
 */

public class DashLineView extends View {


    private Paint mDashPaint;//画笔
    private int mLineColor;
    private float mFullLength;//实线长度
    private float mDashLength;//虚线长度
    int mWidth,mHeight;
    private int mDirection= StepView.Direction.horizontal;
    public static class Direction{
        //值必须和系统的保持一致
        public static final int horizontal=0;
        public static final int vertical=1;
    }
    public DashLineView(Context context) {
        this(context,null);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.DashLineView,defStyleAttr,0);
        mLineColor=ta.getColor(R.styleable.DashLineView_DashLineColor,Color.GRAY);
        mFullLength=ta.getDimension(R.styleable.DashLineView_FullLength,10);
        mDashLength=ta.getDimension(R.styleable.DashLineView_DashLength,10);
        mDirection=ta.getInteger(R.styleable.DashLineView_LineDirection,0);
        ta.recycle();

        mDashPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mDashPaint.setColor(mLineColor);
        mDashPaint.setPathEffect(new DashPathEffect(new float[] { mFullLength, mDashLength}, 0));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        if(mDirection==Direction.horizontal){//横着 的虚线
            //计算宽度
            if(widthMode==MeasureSpec.EXACTLY){//MatchParent 或者指定高度
                mWidth=widthSize;
            }else{//默认高度
                LogUtils.e("horizontal DashLineView widthMode not support WarpContent");
            }
            //计算高度
            if(heightMode==MeasureSpec.EXACTLY){//MatchParent
                mHeight=heightSize;
            }else{//默认高度
                mHeight=SizeUtils.dp2px(2);//默认高度2dp
            }

            mDashPaint.setStrokeWidth(mHeight);
        }else{//竖着的虚线
            //计算宽度
            if(widthMode==MeasureSpec.EXACTLY){//MatchParent 或者指定高度
                mWidth=widthSize;
            }else{//默认高度
                mWidth=SizeUtils.dp2px(2);//默认高度2dp
            }

            //计算高度
            if(heightMode==MeasureSpec.EXACTLY){//MatchParent
                mHeight=heightSize;
            }else{//默认高度
                LogUtils.e("vertical DashLineView heightMode not support WarpContent");
            }
            mDashPaint.setStrokeWidth(mWidth);
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mDirection==Direction.horizontal){//横着 的虚线
            canvas.drawLine(0,0+getHeight()/2,getWidth(),0+getHeight()/2,mDashPaint);
        }else{//竖着的虚线
            canvas.drawLine(0+getWidth()/2,0,0+getWidth()/2,getHeight(),mDashPaint);
        }
    }

}
