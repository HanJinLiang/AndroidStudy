package com.hanjinliang.androidstudy.customerviews.Region;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * Created by HanJinLiang on 2017-07-11.
 * 遥控器按钮
 * http://www.gcssloop.com/customview/touch-matrix-region
 */

public class RemoteControlMenu extends View {
    Path up_p, down_p, left_p, right_p, center_p;

    Region up, down, left, right, center;

    Matrix mMapMatrix = null;

    Bitmap mBitmapUp,mBitmapRight,mBitmapDown,mBitmapLeft,mBitmapmConfirm;

    int CENTER = 0;
    int UP = 1;
    int RIGHT = 2;
    int DOWN = 3;
    int LEFT = 4;
    int touchFlag = -1;
    int currentFlag = -1;

    MenuListener mListener = null;

    int mDefaultColor = 0xFF4E5268;
    int mTouchedColor = 0xFF4E7898;

    Paint mDefaultPaint;

    public RemoteControlMenu(Context context) {
        this(context, null);
    }

    public RemoteControlMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        up_p = new Path();
        down_p = new Path();
        left_p = new Path();
        right_p = new Path();
        center_p = new Path();

        up = new Region();
        down = new Region();
        left = new Region();
        right = new Region();
        center = new Region();

        mDefaultPaint =new Paint(Paint.ANTI_ALIAS_FLAG);

        mDefaultPaint.setColor(mDefaultColor);

        mMapMatrix = new Matrix();

        mBitmapUp= BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_up);
        mBitmapRight= BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        mBitmapDown= BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_down);
        mBitmapLeft= BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_left);
        mBitmapmConfirm= BitmapFactory.decodeResource(context.getResources(), R.drawable.conform);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
        mMapMatrix.reset();

        // 注意这个区域的大小
        Region globalRegion = new Region(-w, -h, w, h);
        int minWidth = w > h ? h : w;
        minWidth *= 0.8;

        int br = minWidth / 2;
        RectF bigCircle = new RectF(-br, -br, br, br);

        int sr = minWidth / 4;
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);

        float bigSweepAngle = 84;
        float smallSweepAngle = -80;

        // 根据视图大小，初始化 Path 和 Region
        center_p.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        center.setPath(center_p, globalRegion);

        right_p.arcTo(bigCircle, -40, bigSweepAngle);
        right_p.arcTo(smallCircle, 40, smallSweepAngle);
        right_p.close();
        right.setPath(right_p, globalRegion);

        down_p.addArc(bigCircle, 50, bigSweepAngle);
        down_p.arcTo(smallCircle, 130, smallSweepAngle);
        down_p.close();
        down.setPath(down_p, globalRegion);

        left_p.addArc(bigCircle, 140, bigSweepAngle);
        left_p.arcTo(smallCircle, 220, smallSweepAngle);
        left_p.close();
        left.setPath(left_p, globalRegion);

        up_p.addArc(bigCircle, 230, bigSweepAngle);
        up_p.arcTo(smallCircle, 310, smallSweepAngle);
        up_p.close();
        up.setPath(up_p, globalRegion);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //利用Matrix 换算坐标
//        float[] pts = new float[2];
//        pts[0] = event.getRawX();
//        pts[1] = event.getRawY();
//        mMapMatrix.mapPoints(pts);
//
//        int x = (int) pts[0] ;
//        int y = (int) pts[1] ;

        int x =  (int)event.getX() -mViewWidth/2;
        int y = (int) event.getY()-mViewHeight/2;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchedPath(x, y);
                currentFlag = touchFlag;
                break;
            case MotionEvent.ACTION_MOVE:
                currentFlag = getTouchedPath(x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchedPath(x, y);
                // 如果手指按下区域和抬起区域相同且不为空，则判断点击事件
                if (currentFlag == touchFlag && currentFlag != -1 && mListener != null) {
                    if (currentFlag == CENTER) {
                        mListener.onCenterClicked();
                    } else if (currentFlag == UP) {
                        mListener.onUpClicked();
                    } else if (currentFlag == RIGHT) {
                        mListener.onRightClicked();
                    } else if (currentFlag == DOWN) {
                        mListener.onDownClicked();
                    } else if (currentFlag == LEFT) {
                        mListener.onLeftClicked();
                    }
                }
                touchFlag = currentFlag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = currentFlag = -1;
                break;
        }

        invalidate();
        return true;
    }

    // 获取当前触摸点在哪个区域
    int getTouchedPath(int x, int y) {
        if (center.contains(x, y)) {
            return 0;
        } else if (up.contains(x, y)) {
            return 1;
        } else if (right.contains(x, y)) {
            return 2;
        } else if (down.contains(x, y)) {
            return 3;
        } else if (left.contains(x, y)) {
            return 4;
        }
        return -1;
    }
    int mViewWidth,mViewHeight;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        // 获取测量矩阵(逆矩阵)
        if (mMapMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMapMatrix);
        }

        // 绘制默认颜色
        canvas.drawPath(center_p, mDefaultPaint);
        canvas.drawPath(up_p, mDefaultPaint);
        canvas.drawPath(right_p, mDefaultPaint);
        canvas.drawPath(down_p, mDefaultPaint);
        canvas.drawPath(left_p, mDefaultPaint);

        // 绘制触摸区域颜色
        mDefaultPaint.setColor(mTouchedColor);
        if (currentFlag == CENTER) {
            canvas.drawPath(center_p, mDefaultPaint);
        } else if (currentFlag == UP) {
            canvas.drawPath(up_p, mDefaultPaint);
        } else if (currentFlag == RIGHT) {
            canvas.drawPath(right_p, mDefaultPaint);
        } else if (currentFlag == DOWN) {
            canvas.drawPath(down_p, mDefaultPaint);
        } else if (currentFlag == LEFT) {
            canvas.drawPath(left_p, mDefaultPaint);
        }
        mDefaultPaint.setColor(mDefaultColor);

        //画按钮图标
        float bigCircleCenter=mViewWidth*3/8*0.8f;//外圈距离中心点的距离
        canvas.drawBitmap(mBitmapUp,-mBitmapUp.getWidth()/2,-bigCircleCenter-mBitmapUp.getHeight()/2,mDefaultPaint);
        canvas.drawBitmap(mBitmapRight,bigCircleCenter-mBitmapUp.getWidth()/2,-mBitmapUp.getHeight()/2,mDefaultPaint);
        canvas.drawBitmap(mBitmapDown,-mBitmapUp.getWidth()/2,bigCircleCenter-mBitmapUp.getHeight()/2,mDefaultPaint);
        canvas.drawBitmap(mBitmapLeft,-bigCircleCenter-mBitmapUp.getWidth()/2,-mBitmapUp.getHeight()/2,mDefaultPaint);
        canvas.drawBitmap(mBitmapmConfirm,-mBitmapmConfirm.getWidth()/2,-mBitmapmConfirm.getHeight()/2,mDefaultPaint);

        canvas.translate(-mViewWidth / 2, -mViewHeight / 2);
    }

    public void setListener(MenuListener listener) {
        mListener = listener;
    }

    // 点击事件监听器
    public interface MenuListener {
        void onCenterClicked();

        void onUpClicked();

        void onRightClicked();

        void onDownClicked();

        void onLeftClicked();
    }
}
