package com.hanjinliang.androidstudy.customerviews.NetsScoreView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;


/**
 * Created by HanJinLiang on 2017-07-04.
 * 网状评分控件
 */
public class NetsScoreView extends View {
    private int mWidth;
    private int mHeight;
    private float mRadius;
    private int mEdgeCount=6;//多边形边数
    private int mMaxScore=100;
    private float[] scores={40,58,70,20,9,39};

    private Context mContext;
    private Paint mNetsPaint;
    private Path mPath;
    private Paint mScoreLinePaint;
    private Paint mScoreFillPaint;
    private Paint mScoreTxtPaint;

    private float mTxtMaxLength;

    public NetsScoreView(Context context) {
        this(context,null);
    }

    public NetsScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetsScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        mNetsPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mNetsPaint.setStyle(Paint.Style.STROKE);
        mNetsPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mNetsPaint.setColor(Color.GRAY);

        mScoreLinePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreLinePaint.setStyle(Paint.Style.STROKE);
        mScoreLinePaint.setStrokeWidth(SizeUtils.dp2px(1));
        mScoreLinePaint.setColor(Color.RED);

        mScoreFillPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreFillPaint.setStyle(Paint.Style.FILL);
        mScoreFillPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mScoreFillPaint.setColor(Color.argb(100,255,0,0));

        mScoreTxtPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreTxtPaint.setStyle(Paint.Style.FILL);
        mScoreTxtPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mScoreTxtPaint.setColor(Color.RED);
        mScoreTxtPaint.setTextAlign(Paint.Align.CENTER);
        mScoreTxtPaint.setTextSize(sp2px(12));


        mPath=new Path();

        mTxtMaxLength=getTxtMaxLength();
    }

    /**
     * 分数文字最大的值
     * @return
     */
    private float getTxtMaxLength(){
        Rect rect=new Rect();
        float maxLength=0;
        for(int i=0;i<mEdgeCount;i++){
            mScoreTxtPaint.getTextBounds(scores[i]+"分",0,(scores[i]+"分").length(),rect);
            if(rect.width()>maxLength){
                maxLength=rect.width();
            }
        }
        return maxLength;
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public   int sp2px(float spValue) {
        final float fontScale =mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth=getWidth();
        mHeight=getHeight();
        mRadius=mWidth/2-mTxtMaxLength/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mWidth/2);//
        for(int i=1;i<=mEdgeCount;i++){
            drawPolygon(canvas,mEdgeCount,mRadius/mEdgeCount*i);
        }
        drawCenterLine(canvas,mEdgeCount,mRadius);

        drawScore(canvas,mEdgeCount);

        drawScoreTxt(canvas,mEdgeCount);
    }

    /**
     * 画文字分数
     * @param canvas
     * @param edgeCount
     */
    private void drawScoreTxt(Canvas canvas, int edgeCount) {
        float radius=mRadius+mTxtMaxLength/2;
        for (int i=0;i<edgeCount;i++){
            canvas.drawText(scores[i]+"分",radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i),mScoreTxtPaint);
        }
    }

    /**
     *
     */
    private void drawScore(Canvas canvas, int edgeCount) {
        mPath.reset();
        for (int i=0;i<edgeCount;i++){
            float radius=scores[i]/mMaxScore*mRadius;
            if (i==0){
                mPath.moveTo(radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i));//绘制起点
            }else{
                mPath.lineTo(radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i));
            }
        }
        mPath.close();
        canvas.drawPath(mPath,mScoreLinePaint);
        canvas.drawPath(mPath,mScoreFillPaint);
    }

    /**
     * 画圆心到个顶点的连线
     * @param canvas
     * @param edgeCount
     * @param radius
     */
    private void drawCenterLine(Canvas canvas, int edgeCount, float radius) {
        for (int i=0;i<edgeCount;i++){
            canvas.drawLine(0,0 ,radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i),mNetsPaint);
        }
    }

    /**
     * 画多边形
     * @param canvas
     * @param edgeCount  多边形边数
     * @param radius 半径
     */
    private void drawPolygon(Canvas canvas, int edgeCount, float radius) {
        mPath.reset();
        for (int i=0;i<edgeCount;i++){
            if (i==0){
                mPath.moveTo(radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i));//绘制起点
            }else{
                mPath.lineTo(radius*cos(360/edgeCount*i),radius*sin(360/edgeCount*i));
            }
        }
        mPath.close();
        canvas.drawPath(mPath,mNetsPaint);
    }

    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }
}
