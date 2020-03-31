package com.hanjinliang.androidstudy.customerviews.NumView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-11-22.
 */
public class NumView extends View {
    private int mInnerCircleColor=Color.WHITE;
    private int mOntCircleColor=Color.RED;
    private int mTextColor=Color.BLACK;
    private float mOutCircleWidth;
    private float mInnerPadding;
    private float mTextSize;

    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;
    private int mNum;
    public NumView(Context context) {
        this(context,null);
    }

    public NumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.NumView,defStyleAttr,0);
        mInnerCircleColor=ta.getColor(R.styleable.NumView_InnerCircleColor, mInnerCircleColor);
        mInnerPadding=ta.getDimension(R.styleable.NumView_NumInnerPadding, dip2px(2));
        mOutCircleWidth=ta.getDimension(R.styleable.NumView_OutCircleWidth, dip2px(2));
        mOntCircleColor=ta.getColor(R.styleable.NumView_OutCircleColor,mOntCircleColor);
        mTextColor=ta.getColor(R.styleable.NumView_NumTextColor,mTextColor);
        mTextSize=ta.getDimension(R.styleable.NumView_NumTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,context.getResources().getDisplayMetrics()));
        ta.recycle();

        //文字画笔
        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        //外圈画笔
        mOutPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(mOutCircleWidth);
        mOutPaint.setColor(mOntCircleColor);

        //内圈画笔
        mInnerPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setColor(mInnerCircleColor);
    }
    Rect mRect=new Rect();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextPaint.getTextBounds(Integer.toString(mNum),0,Integer.toString(mNum).length(),mRect);
        LogUtils.e("onMeasure--"+mRect.width());
        float width=mRect.width()+mOutCircleWidth*2+mInnerPadding*2;
        float height=mRect.height()+mOutCircleWidth*2+mInnerPadding*2;
        int finalSize= (int) Math.max(width,height);//取最大值
        setMeasuredDimension(finalSize,finalSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画外圈
        canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2-mOutCircleWidth/2,mOutPaint);
        //画内圈
        canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2-mOutCircleWidth,mInnerPaint);
        //文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (getHeight()/2- top/2 - bottom/2);//基线中间点的y轴计算公式

        canvas.drawText(Integer.toString(mNum),getHeight()/2,baseLineY,mTextPaint);
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

    public void setNum(int num) {
        mNum = num;
        requestLayout();
    }

    public void numAdd() {
        mNum++;
        requestLayout();
    }

    public void numDec() {
        mNum--;
        requestLayout();
    }
}
