package com.hanjinliang.androidstudy.customerviews.NetsScoreView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;


/**
 * Created by HanJinLiang on 2017-07-04.
 * 网状评分控件
 */
public class NetsScoreView extends View {
    private int mWidth;
    private int mHeight;
    private float mRadius;
    private int mEdgeCount=5;//多边形边数
    private int mMaxScore=100;//最大值
    private float[] scores={0,0,0,0,0};//分值
    private Context mContext;
    private Paint mNetsPaint;
    private Path mPath;
    private Paint mScoreLinePaint;
    private Paint mScoreFillPaint;
    private Paint mScoreTxtPaint;

    private float mTxtMaxLength;
    private float mTxtHeight;

    public NetsScoreView(Context context) {
        this(context,null);
    }

    public NetsScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetsScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext=context;
        if (isInEditMode()) { return; }
        mNetsPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mNetsPaint.setStyle(Paint.Style.STROKE);
        mNetsPaint.setStrokeWidth(SizeUtils.dp2px(1));
        mNetsPaint.setColor(Color.GRAY);

        mScoreLinePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreLinePaint.setStyle(Paint.Style.STROKE);
        mScoreLinePaint.setStrokeWidth(dp2px(1));
        mScoreLinePaint.setColor(Color.RED);

        mScoreFillPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreFillPaint.setStyle(Paint.Style.FILL);
        mScoreFillPaint.setStrokeWidth(dp2px(2));
        mScoreFillPaint.setColor(Color.argb(100,255,0,0));

        mScoreTxtPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreTxtPaint.setStyle(Paint.Style.FILL);
        mScoreTxtPaint.setStrokeWidth(dp2px(1));
        mScoreTxtPaint.setColor(Color.RED);
        mScoreTxtPaint.setTextAlign(Paint.Align.CENTER);
        mScoreTxtPaint.setTextSize(sp2px(12));


        mPath=new Path();

        mTxtMaxLength=getTxtMaxLength();
    }

    public void setData(int edgeCount,float[] scores){
        mEdgeCount=edgeCount;
        this.scores=scores;
        if(mEdgeCount!=scores.length){
            try {
                throw new Exception("变数和分数数组长度必须一致");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        invalidate();
    }

    /**
     * 分数文字最大的值
     * @return
     */
    private float getTxtMaxLength(){
        Rect rect=new Rect();
        float maxLength=0;
        for(int i=0;i<mEdgeCount;i++){
            mScoreTxtPaint.getTextBounds(getShowTxt(i),0,getShowTxt(i).length(),rect);
            if(rect.width()>maxLength){
                maxLength=rect.width();
            }
        }
        mTxtHeight=rect.height();
        return maxLength;
    }

    /**
     * 根据分数获取要显示的txt
     * @param index
     * @return
     */
    private String getShowTxt(int index){
        if(mTxtFormat==null){
            return scores[index]+"";
        }
        return  mTxtFormat.originalDataFormat(index,scores[index]);
    }

    TxtFormat mTxtFormat;
    public void setTxtFormat(TxtFormat txtFormat) {
        //根据用户自定义format 重新计算最大宽度 以及半径
        mTxtFormat = txtFormat;
        mTxtMaxLength=getTxtMaxLength();
        mRadius=mWidth/2-mTxtMaxLength;
    }

    /**
     * 格式化显示数值
     */
    public interface TxtFormat{
        public String originalDataFormat(int index,float value);
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
        mRadius=mWidth/2-mTxtMaxLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mWidth/2);//
        for(int i=1;i<=mEdgeCount;i++){
            drawPolygon(canvas,mEdgeCount,mRadius/mEdgeCount*i);
        }

        drawCenterLine(canvas,mEdgeCount);

        drawScore(canvas,mEdgeCount);

        drawScoreTxt(canvas,mEdgeCount);
        canvas.translate(-mWidth/2,-mWidth/2);
    }

    /**
     * 画文字分数
     * @param canvas
     * @param edgeCount
     */
    private void drawScoreTxt(Canvas canvas, int edgeCount) {
        float radius=mRadius+mTxtMaxLength/2;
        float nextAngle;
        float nextRadians;
        float nextPointX;
        float nextPointY;

        float averageAngle = 360 / edgeCount;
        float offsetAngle = averageAngle > 0 && edgeCount % 2 == 0 ? averageAngle / 2 : 0;
        for (int position = 0; position < edgeCount; position++) {
            nextAngle = offsetAngle + (position * averageAngle);
            nextRadians = (float) Math.toRadians(nextAngle);
            nextPointX = (float) ( Math.sin(nextRadians) * radius);
            nextPointY = (float) ( Math.cos(nextRadians) * radius);

            canvas.drawText(getShowTxt(position),nextPointX,nextPointY+mTxtHeight/2,mScoreTxtPaint);
        }
        for (int i=0;i<edgeCount;i++){

        }
    }


    /**
     *画分数覆盖图
     */
    private void drawScore(Canvas canvas, int edgeCount) {
        mPath.reset();
        float nextAngle;
        float nextRadians;
        float nextPointX;
        float nextPointY;

        float averageAngle = 360 / edgeCount;
        float offsetAngle = averageAngle > 0 && edgeCount % 2 == 0 ? averageAngle / 2 : 0;
        for (int position = 0; position < edgeCount; position++) {
            float radius=scores[position]/mMaxScore*mRadius;
            nextAngle = offsetAngle + (position * averageAngle);
            nextRadians = (float) Math.toRadians(nextAngle);
            nextPointX = (float) ( Math.sin(nextRadians) * radius);
            nextPointY = (float) ( Math.cos(nextRadians) * radius);
            canvas.drawPoint(nextPointX,nextPointY,mScoreFillPaint);
            if(position == 0){
                mPath.moveTo(nextPointX, nextPointY);
            }else{
                mPath.lineTo(nextPointX, nextPointY);
            }
        }

        mPath.close();
        canvas.drawPath(mPath,mScoreLinePaint);
        canvas.drawPath(mPath,mScoreFillPaint);
    }



    /**
     * 画多边形
     * @param canvas
     * @param edgeCount  多边形边数
     * @param radius 半径
     */
    private void drawPolygon(Canvas canvas, int edgeCount, float radius) {
        mPath.reset();
        float nextAngle;
        float nextRadians;
        float nextPointX;
        float nextPointY;

        float averageAngle = 360 / edgeCount;
        float offsetAngle = averageAngle > 0 && edgeCount % 2 == 0 ? averageAngle / 2 : 0;
        for (int position = 0; position < edgeCount; position++) {
            nextAngle = offsetAngle + (position * averageAngle);
            nextRadians = (float) Math.toRadians(nextAngle);
            nextPointX = (float) ( Math.sin(nextRadians) * radius);
            nextPointY = (float) ( Math.cos(nextRadians) * radius);

            if(position == 0){
                mPath.moveTo(nextPointX, nextPointY);
            }else{
                mPath.lineTo(nextPointX, nextPointY);
            }
        }

        mPath.close();
        canvas.drawPath(mPath,mNetsPaint);
    }

    /**
     * 画原点到各顶点的连线
     * @param canvas
     * @param edgeCount
     */
    private void drawCenterLine(Canvas canvas, int edgeCount) {
        float nextAngle;
        float nextRadians;
        float nextPointX;
        float nextPointY;

        float averageAngle = 360 / edgeCount;
        float offsetAngle = averageAngle > 0 && edgeCount % 2 == 0 ? averageAngle / 2 : 0;
        for (int position = 0; position < edgeCount; position++) {
            nextAngle = offsetAngle + (position * averageAngle);
            nextRadians = (float) Math.toRadians(nextAngle);
            nextPointX = (float) ( Math.sin(nextRadians) * mRadius);
            nextPointY = (float) ( Math.cos(nextRadians) * mRadius);
            //画圆心到个顶点的连线
            canvas.drawLine(0, 0, nextPointX, nextPointY, mNetsPaint);
        }
    }

}
