package com.hanjinliang.androidstudy.customerviews.PieChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HanJinLiang on 2017-12-19.
 */

public class PieChartView extends View {
    ArrayList<PieBeans> mDatas=new ArrayList<>();//数据源
    HashMap<String,Path> mPaths=new HashMap<>();//存放所有的扇形Path
    private int mCircleRadius;//默认的饼状图的半径
    private int mLineInnerWidth;//线条在饼状图内部的长度
    private int mLineWidth;//指示线的宽度
    private int mTextPaddingWidth;//文字距离指示线的宽度

    private Paint mPiePaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPiePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setColor(Color.RED);
        mPiePaint.setStyle(Paint.Style.FILL);

        mLinePaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.FILL);

        mTextPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(sp2px(12));

    }
    RectF mRectF;
    Rect mRect;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleRadius=Math.min(w,h)/4;//圆半径 为宽高较小的 2/3
        mLineWidth=mCircleRadius/3;
        mLineInnerWidth=mCircleRadius/20;
        mTextPaddingWidth=dp2px(2);

        mRectF =new RectF(w/2-mCircleRadius,h/2-mCircleRadius,w/2+mCircleRadius,h/2+mCircleRadius);
        mRect =new Rect(w/2-mCircleRadius,h/2-mCircleRadius,w/2+mCircleRadius,h/2+mCircleRadius);
    }

    public void setDatas(ArrayList<PieBeans> datas) {
        mDatas = datas;
        invalidate();
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
    float mTotalPercent;
    Path mPath;
    private int mTempCircleRadius;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景色
        canvas.drawColor(Color.GRAY);
        mTotalPercent=mOffsetAngle;
        for(int i=0;i<mDatas.size();i++){
            if(mClickIndex==i){//是被点击的  半径变大
                mTempCircleRadius=mCircleRadius*21/20;
            }else{
                mTempCircleRadius=mCircleRadius;
            }
            PieBeans pieBeans=mDatas.get(i);
            mPiePaint.setColor(pieBeans.getColor());//设置颜色
            if(mPaths.get(pieBeans.getText())==null){//初始化Path
                mPaths.put(pieBeans.getText(),new Path());
            }

            mRectF.set(getWidth()/2-mTempCircleRadius,getHeight()/2-mTempCircleRadius,getWidth()/2+mTempCircleRadius,getHeight()/2+mTempCircleRadius);
            mPath=mPaths.get(pieBeans.getText());
            mPath.reset();
            mPath.moveTo(mRectF.centerX(), mRectF.centerY());
            mPath.addArc(mRectF,mTotalPercent,pieBeans.getPercent()*3.6f);
            mPath.lineTo(mRectF.centerX(), mRectF.centerY());
            canvas.drawPath(mPath,mPiePaint);

//            canvas.drawArc(mRectF,mTotalPercent,pieBeans.getPercent()*3.6f,true,mPiePaint);
            float textWidth=mTextPaint.measureText(pieBeans.getText());
            //文字
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

            double angle=(mTotalPercent+pieBeans.getPercent()*1.8)%360;
            if(angle<90) {//右下
                float inLineY = (float) (Math.sin(Math.toRadians(angle)) * (mTempCircleRadius-mLineInnerWidth));
                float inLineX = (float) (Math.cos(Math.toRadians(angle)) * (mTempCircleRadius-mLineInnerWidth));

                float outLineY = (float) (Math.sin(Math.toRadians(angle)) *(mTempCircleRadius+mLineInnerWidth));
                float outLineX = (float) (Math.cos(Math.toRadians(angle))*(mTempCircleRadius+mLineInnerWidth));

                //指示线 折现部分
                canvas.drawLine(inLineX+ mRectF.centerX(), inLineY+ mRectF.centerY(), outLineX+ mRectF.centerX(), outLineY+ mRectF.centerY(), mLinePaint);
                //指示线 直线部分
                canvas.drawLine(outLineX+ mRectF.centerX(), outLineY+ mRectF.centerY(), outLineX+ mRectF.centerX()+mLineWidth, outLineY+ mRectF.centerY(), mLinePaint);
                //指示 文字部分
                canvas.drawText(pieBeans.getText(),outLineX+ mRectF.centerX()+mLineWidth+mTextPaddingWidth, outLineY+ mRectF.centerY()- top/2 - bottom/2,mTextPaint);
            }else if(angle>=90&&angle<180){//左下
                float inLineY = (float) (Math.cos(Math.toRadians(angle-90)) * (mTempCircleRadius-mLineInnerWidth));
                float inLineX = (float) (Math.sin(Math.toRadians(angle-90)) *  (mTempCircleRadius-mLineInnerWidth));

                float outLineY = (float) (Math.cos(Math.toRadians(angle-90)) *(mTempCircleRadius+mLineInnerWidth));
                float outLineX = (float) (Math.sin(Math.toRadians(angle-90))*(mTempCircleRadius+mLineInnerWidth));


                canvas.drawLine(mRectF.centerX()-inLineX, inLineY+ mRectF.centerY(), mRectF.centerX()-outLineX, outLineY+ mRectF.centerY(), mLinePaint);
                canvas.drawLine(mRectF.centerX()-outLineX, outLineY+ mRectF.centerY(), mRectF.centerX()-outLineX-mLineWidth, outLineY+ mRectF.centerY() , mLinePaint);
                canvas.drawText(pieBeans.getText(), mRectF.centerX()-outLineX-mLineWidth-textWidth-mTextPaddingWidth, outLineY+ mRectF.centerY() - top/2 - bottom/2,mTextPaint);

            }else if(angle>=180&&angle<270){//左上
                float inLineY = (float) (Math.sin(Math.toRadians(angle-180)) *  (mTempCircleRadius-mLineInnerWidth));
                float inLineX = (float) (Math.cos(Math.toRadians(angle-180)) * (mTempCircleRadius-mLineInnerWidth));

                float outLineY = (float) (Math.sin(Math.toRadians(angle-180)) *(mTempCircleRadius+mLineInnerWidth));
                float outLineX = (float) (Math.cos(Math.toRadians(angle-180))*(mTempCircleRadius+mLineInnerWidth));

                canvas.drawLine(mRectF.centerX()-inLineX, mRectF.centerY()-inLineY, mRectF.centerX()-outLineX, mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawLine(mRectF.centerX()-outLineX, mRectF.centerY()-outLineY, mRectF.centerX()-outLineX-mLineWidth, mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawText(pieBeans.getText(), mRectF.centerX()-outLineX-mLineWidth-textWidth-mTextPaddingWidth, mRectF.centerY()-outLineY - top/2 - bottom/2,mTextPaint);

            }else if(angle>=270&&angle<360){//右上
                float inLineY = (float) (Math.cos(Math.toRadians(angle-270)) *  (mTempCircleRadius-mLineInnerWidth));
                float inLineX = (float) (Math.sin(Math.toRadians(angle-270)) * (mTempCircleRadius-mLineInnerWidth));

                float outLineY = (float) (Math.cos(Math.toRadians(angle-270)) * (mTempCircleRadius+mLineInnerWidth));
                float outLineX = (float) (Math.sin(Math.toRadians(angle-270))*(mTempCircleRadius+mLineInnerWidth));

                canvas.drawLine(inLineX+ mRectF.centerX(), mRectF.centerY()-inLineY, outLineX+ mRectF.centerX(), mRectF.centerY()-outLineY, mLinePaint);
                canvas.drawLine(outLineX+ mRectF.centerX(), mRectF.centerY()-outLineY, outLineX+ mRectF.centerX()+mLineWidth, mRectF.centerY()-outLineY , mLinePaint);
                canvas.drawText(pieBeans.getText(),outLineX+ mRectF.centerX()+mLineWidth+mTextPaddingWidth, mRectF.centerY()-outLineY - top/2 - bottom/2,mTextPaint);

            }

            //累计总的角度
            mTotalPercent+=(pieBeans.getPercent()*3.6f);
        }

    }

    //被点击的索引
    int mClickIndex=-1;
    //手势检测器
    GestureDetector gestureDetector;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //使用手势检测
        if(gestureDetector==null){
            gestureDetector=new GestureDetector(getContext(),onGestureListener);
        }
        return gestureDetector.onTouchEvent(event);
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                return true;
//            case MotionEvent.ACTION_UP:
//                float x=event.getX();
//                float y=event.getY();
//                int index=clickIndex((int)x,(int)y);
//                if(index!=-1){
//                    mClickIndex=index;
//                    invalidate();
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
    }
    Region mRegion=new Region();//区域
    public int clickIndex(int x,int y){
        for(int i=0;i<mDatas.size();i++){
            mRegion.setPath(mPaths.get(mDatas.get(i).getText()),new Region(mRect));
            boolean isContains=mRegion.contains(x,y);
            if(isContains){
                return  i;
            }
        }
        return -1;
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    private   int dp2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private float mOffsetAngle=0;//旋转角度
    private float mAngleSpeed=2;//旋转速度
    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {//手指轻触屏幕的一瞬间，由一个ACTION_DOWN触发
            LogUtils.e("轻触一下");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {//手指轻触屏幕，尚未松开或拖动，由一个ACTION_DOWN触发
            LogUtils.e("轻触未松开");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {//手指离开屏幕，伴随一个ACTION_UP触发，单击行为
            LogUtils.e("单击");
              int index=clickIndex((int)e.getX(),(int)e.getY());
                if(index!=-1&&mClickIndex!=index){
                    mClickIndex=index;
                    if(mOnPieItemClickListener!=null){//点击选中回调
                        mOnPieItemClickListener.OnPieItemClickListener(mClickIndex,mDatas.get(mClickIndex));
                    }
                    invalidate();
                }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//手指按下屏幕并拖动
            // 由一个由一个ACTION_DOWN，多个ACTION_MOVE触发，是拖动行为
            LogUtils.e("distanceX="+distanceX+"---distanceY="+distanceY);
            //滑动的点是否在圆内 两点间的直线距离
           double distance=Math.abs( Math.sqrt(Math.pow((mRectF.centerX()-e2.getX()),2)+Math.pow((mRectF.centerY()-e2.getY()),2)));
           if(distance<=mCircleRadius){
               //在圆内
               LogUtils.e("在圆内拖动");
               if(Math.abs(distanceX)>Math.abs(distanceY)){//水平方向
                   if(e2.getY()<mRectF.centerY()){//上半部分
                        if(distanceX<0){//右滑动
                            mOffsetAngle+=mAngleSpeed;
                        }else{//左滑动
                            mOffsetAngle-=mAngleSpeed;
                        }
                   }else{//下半部分
                       if(distanceX<0){//右滑动
                           mOffsetAngle-=mAngleSpeed;
                       }else{//左滑动
                           mOffsetAngle+=mAngleSpeed;
                       }
                   }
               }else{//竖直方向
                   if(e2.getX()<mRectF.centerX()){//左边部分
                       if(distanceY<0){//下滑滑动
                           mOffsetAngle-=mAngleSpeed;
                       }else{//上滑动
                           mOffsetAngle+=mAngleSpeed;
                       }
                   }else{//右边部分
                       if(distanceY<0){//下滑滑动
                           mOffsetAngle+=mAngleSpeed;
                       }else{//上滑动
                           mOffsetAngle-=mAngleSpeed;
                       }
                   }
               }
               if(mOffsetAngle<0){//如果小于0  转化为正的角度
                   mOffsetAngle=360+mOffsetAngle%360;
               }
               invalidate();
               return true;
           }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {//长按
            LogUtils.e("长按");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //按下屏幕，快速滑动后松开，由一个由一个ACTION_DOWN，多个ACTION_MOVE，一个ACTION_UP触发
            LogUtils.e("快速滑动");
            return false;
        }
    };

    private OnPieItemClickListener mOnPieItemClickListener;
    public interface OnPieItemClickListener{
        public void OnPieItemClickListener(int index,PieBeans pieBeans);
    }

    public void setOnPieItemClickListener(OnPieItemClickListener onPieItemClickListener) {
        mOnPieItemClickListener = onPieItemClickListener;
    }
}
