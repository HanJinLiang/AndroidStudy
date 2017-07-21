package com.hanjinliang.androidstudy.customerviews.LineView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-07-21.
 */

public class LineView extends View {
    Context mContext;
    Paint mPaintCoord;
    Paint mXPaint;
    private int mScreenCount=7;
    private float mXItemWidth;
    private float mPadding;
    private int mWidth,mHeight;
    private ArrayList<WeightData> mData=new ArrayList<WeightData>();

    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        mContext=context;

        mPadding=dip2px(10);

        initData();
        //坐标画笔
        mPaintCoord=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCoord.setColor(Color.GRAY);


        //坐标画笔
        mXPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mXPaint.setColor(Color.RED);
        mXPaint.setTextAlign(Paint.Align.CENTER);
        mXPaint.setTextSize(30);

    }

    private void initData() {
        mData.add(new WeightData("阿萨德",90));
        mData.add(new WeightData("是的",40));
        mData.add(new WeightData("但",20));
        mData.add(new WeightData("5",30));
        mData.add(new WeightData("65",50));
        mData.add(new WeightData("34",60));
        mData.add(new WeightData("23",20));
        mData.add(new WeightData("大都是",20));
        mData.add(new WeightData("56",80));
        mData.add(new WeightData("6是7",20));
        mData.add(new WeightData("2",90));

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
            width=dip2px(getScreenWidth());//默认值
        }

        int height;
        if(heightMode==MeasureSpec.EXACTLY){//MatchParent
            height=heightSize;
        }else{
            height=dip2px(300);//默认值  上下文字和色块以及间距
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mXItemWidth=(mWidth-mPadding*2)/mScreenCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mPadding,mHeight-mPadding);
        //X轴
        canvas.drawLine(0,0,mWidth-mPadding,0,mPaintCoord);
        //Y轴
        canvas.drawLine(0,0,0,-(mHeight-mPadding),mPaintCoord);
        //x轴描述值
        for(int i=0;i<mData.size();i++){
            canvas.drawText(mData.get(i).getDesc(),mXItemWidth*i+mOffset,0,mXPaint);
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
    float mOffset;
    float downX=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX=event.getX();
                if(Math.abs(moveX-downX)>5){//滑动超过一定距离
                    mOffset=moveX-downX;
                    //downX=moveX;
                }
                LogUtils.e("mOffset="+mOffset);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //moveDistance=0;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
