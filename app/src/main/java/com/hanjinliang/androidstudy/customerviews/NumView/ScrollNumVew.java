package com.hanjinliang.androidstudy.customerviews.NumView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by HanJinLiang on 2018-09-26.
 * 可以滚动的TextView
 */
public class ScrollNumVew extends View {
    float mNum;
    private Paint mNumPaint;
    private float mNumTextSize;//字体大小
    private float mSmallTextSize;//小数部分字体大小
    private float mNumWidth;//总宽度
    private int baseLineY;

    String mNumStr;//数字的String
    private int indexOfP;//小数点index

    public ScrollNumVew(Context context) {
        this(context,null);
    }

    public ScrollNumVew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollNumVew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNumPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setColor(ContextCompat.getColor(getContext(),android.R.color.black));

        mNumTextSize=sp2px(26);
        mSmallTextSize=sp2px(14);

        mNumPaint.setTextSize(mNumTextSize);

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(mNumStr==null){
            setMeasuredDimension(widthSize,heightSize);
            return;
        }

        int totalWidth = (int) mNumPaint.measureText(mNumStr);
        int textSize =(int)  mNumPaint.getTextSize();

        if(widthMode!=MeasureSpec.EXACTLY){
            widthSize=Math.min(widthSize,totalWidth);
        }

        if(heightMode!=MeasureSpec.EXACTLY){
            heightSize=Math.min(heightSize,textSize);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //文字
        Paint.FontMetrics fontMetrics = mNumPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        baseLineY = (int) (getHeight()/2- top/2 - bottom/2);//基线中间点的y轴计算公式
    }

    int[] mSplitTotalHeight;
    float[] mSplitOffsets;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mNumWidth=0;
        if(mNumStr==null){return;}
        for(int i=0;i<mNumStr.length();i++){
            char charAt = mNumStr.charAt(i);
            String splitStr = String.valueOf(charAt);
            if(indexOfP==-1||i<indexOfP){//整数部分
                mNumPaint.setTextSize(mNumTextSize);
                //每一位的Integer值
                Integer splitInt = Integer.valueOf(splitStr);
                mSplitTotalHeight[i]=splitInt*getHeight();
                float splitStrWidth = mNumPaint.measureText(splitStr);
                for(int j=0;j<=splitInt;j++) {
                    canvas.drawText(String.valueOf(j), mNumWidth,baseLineY+j*getHeight()-mSplitOffsets[i], mNumPaint);
                }
                mNumWidth+=splitStrWidth;
            }else{//小数部分
                mNumPaint.setTextSize(mSmallTextSize);
                float splitStrWidth = mNumPaint.measureText(splitStr);
                canvas.drawText(splitStr,mNumWidth,baseLineY,mNumPaint);
                mNumWidth+=splitStrWidth;
            }
        }
    }

    public void setNum(float num){
        mNum=num;
        mNumStr = String.valueOf(mNum);
        indexOfP = mNumStr.indexOf(".");
        mSplitTotalHeight=new int[indexOfP];
        mSplitOffsets=new float[indexOfP];
        requestLayout();
        startScan();
    }

    public void startScan(){
        if(mUpAnimator!=null&&!mUpAnimator.isRunning()){
            mUpAnimator.start();
        }
    }

    ValueAnimator mUpAnimator;

    private void initView() {
        mUpAnimator = ValueAnimator.ofFloat(0, 1);//从0-1
        //mUpAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mUpAnimator.setDuration(1000);
        mUpAnimator.setInterpolator(new DecelerateInterpolator());
        mUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                for(int i=0;i<mSplitOffsets.length;i++){
                    mSplitOffsets[i]=value*mSplitTotalHeight[i];
                }
                postInvalidate();
            }
        });
    }


    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public  int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
