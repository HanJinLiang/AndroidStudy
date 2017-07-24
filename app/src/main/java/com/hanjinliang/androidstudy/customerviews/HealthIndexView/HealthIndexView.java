package com.hanjinliang.androidstudy.customerviews.HealthIndexView;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-21.
 */

public class HealthIndexView extends View {
    private final String TAG=getClass().getSimpleName();
    private Context mContext;
    private String[] mHealthValues;
    private int[] mIndexColors;//ColorRes
    private String[] mDescTexts;

    private float mInnerPadding ;//文字和颜色上下的边距
    private float mOutPadding ;//文字上下的边距


    private float mColorHeight ;//色块高度

    private float mItemWidth;//每一个色块的宽度

    private Paint mPaintText;
    private Paint mPaintColor;
    private int mTextColor;
    private float mTextSize;
    float mUpTextHeight;
    float mDownTextHeight;
    int mWidth,mHeight;

    public HealthIndexView(Context context) {
        this(context,null);
    }

    public HealthIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HealthIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.HealthIndexView,defStyleAttr,0);
        mOutPadding=ta.getDimension(R.styleable.HealthIndexView_OutingPadding, dip2px(10));//文字控件上下的边距
        mInnerPadding=ta.getDimension(R.styleable.HealthIndexView_InnerPadding, dip2px(10));//文字和颜色块上下的边距
        mColorHeight=ta.getDimension(R.styleable.HealthIndexView_ColorBlockHeight,dip2px(20));//色块高度
        mTextColor=ta.getColor(R.styleable.HealthIndexView_TextColor,Color.GRAY);
        mTextSize=ta.getDimension(R.styleable.HealthIndexView_TextSize,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,context.getResources().getDisplayMetrics()));
        ta.recycle();

        //文字画笔
        mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setColor(mTextColor);
        mPaintText.setTextSize(mTextSize);

        //色块画笔
        mPaintColor=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColor.setStyle(Paint.Style.FILL);
    }

    /**
     * 测量文字
     */
    private void measureText(){
        Rect rect=new Rect();
        for(int i=0;i<mHealthValues.length;i++){
            mPaintText.getTextBounds(mHealthValues[i],0,mHealthValues[i].length(),rect);
            mUpTextHeight=Math.max(mUpTextHeight,rect.height());
        }

        for(int i=0;i<mDescTexts.length;i++){
            mPaintText.getTextBounds(mDescTexts[i],0,mDescTexts[i].length(),rect);
            mDownTextHeight=Math.max(mDownTextHeight,rect.height());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width;
        if(widthMode==MeasureSpec.EXACTLY){//MatchParent
            width=widthSize;
        }else{//不确定的  给屏幕的宽度
            width=getScreenWidth();//默认值
        }

        int height;
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            height=heightSize;
        }else{
            height= (int) (mUpTextHeight+mDownTextHeight+mColorHeight+2*mInnerPadding+2*mOutPadding);//默认值  上下文字和色块以及间距
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        if(mIndexColors!=null&&mIndexColors.length>0) {
            mItemWidth = mWidth / mIndexColors.length;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawValueText(canvas);
        drawColorBlock(canvas);
        drawDescText(canvas);
    }

    /**
     * 画分值
     * @param canvas
     */
    private void drawValueText(Canvas canvas) {
        if(mHealthValues==null){
            return;
        }
        //FIXME 需要修正 垂直居中
        float baseLine=mOutPadding+mUpTextHeight;
        for(int i=0;i<mHealthValues.length;i++){
            canvas.drawText(mHealthValues[i],mItemWidth*(i+1),baseLine,mPaintText);
        }
    }

    /**
     * 画色块
     * @param canvas
     */
    private void drawColorBlock(Canvas canvas) {
        if(mIndexColors==null){
            return;
        }
        for(int i=0;i<mIndexColors.length;i++){
            mPaintColor.setColor(mIndexColors[i]);
            canvas.drawRect(i*mItemWidth,mUpTextHeight+mInnerPadding+mOutPadding,(i+1)*mItemWidth,mUpTextHeight+mInnerPadding+mColorHeight+mOutPadding,mPaintColor);
        }

    }


    /**
     * 画描述文字
     * @param canvas
     */
    private void drawDescText(Canvas canvas) {
        if(mDescTexts==null){
            return;
        }
        //FIXME 需要修正 垂直居中
        float baseLine=mOutPadding+mUpTextHeight+mInnerPadding*2+mDownTextHeight+mColorHeight;
        for(int i=0;i<mDescTexts.length;i++){
            canvas.drawText(mDescTexts[i],mItemWidth*i+mItemWidth/2,baseLine,mPaintText);
        }
    }
    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dip2px( float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    private   int getScreenWidth() {
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }

    /**
     * 设置数据源
     * @param healthValues  上面的值
     * @param @ColorRes indexColorsId   颜色数组
     * @param descTexts     描述文字
     */
    public void setDataRes(String[] healthValues,@Nullable @ColorRes int[] indexColorsId, String[] descTexts){
        int[] indexColorsInt=new int[indexColorsId.length];
        for(int i=0;i<indexColorsInt.length;i++){
            indexColorsInt[i]=ContextCompat.getColor(mContext,indexColorsId[i]);
        }
        setDataInt(healthValues,indexColorsInt,descTexts);
    }

    /**
     * 设置数据源
     * @param healthValues  上面的值
     * @param @ColorInt indexColorsInt   颜色数组
     * @param descTexts     描述文字
     */
    public void setDataInt(String[] healthValues,@Nullable @ColorInt int[] indexColorsInt, String[] descTexts){
        if(indexColorsInt.length!=descTexts.length||indexColorsInt.length<2||healthValues.length+1!=indexColorsInt.length){
            Log.d(TAG, "setData: 数据不符合规范");
            return;
        }
        mHealthValues=healthValues;
        mIndexColors=indexColorsInt;
        mDescTexts=descTexts;
        measureText();
        invalidate();
    }

}
