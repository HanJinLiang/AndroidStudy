package com.hanjinliang.androidstudy.customerviews.Region;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by HanJinLiang on 2017-07-11.
 */

public class RegionCircleView extends View {
    private int mWidth,mHeight;

    Path circlePath;
    Region circleRegion;
    Paint mPaint;
    public RegionCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL);


        circlePath=new Path();
        circleRegion=new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;

        circlePath.addCircle(mWidth/2,mHeight/2,300, Path.Direction.CCW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(0, 0, w, h);
        circleRegion.setPath(circlePath,globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(circlePath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                // ▼点击区域判断
                if (circleRegion.contains(x,y)){
                    Toast.makeText(this.getContext(),"圆被点击",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
