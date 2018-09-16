package com.hanjinliang.androidstudy.customerviews.linerecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-15.
 * 利用RecyclerView实现的曲线图
 */
public class CornerLineChartItemDecoration extends RecyclerView.ItemDecoration {
    Paint mPaint;
    Context mContext;
    ArrayList<Float> mData;
    private float mMaxData,mMinData;
    private int mChartHeight;
    Path path=new Path();
    Path itemBgPath=new Path();
    public CornerLineChartItemDecoration(Context context, ArrayList<Float> mData) {
        this.mData=mData;
        mContext=context;

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#6E7DBE"));
        mPaint.setStyle(Paint.Style.FILL);
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(1));
        parseData();
    }

    /**
     * 获取到最大值和最小值
     */
    private void parseData() {
        mMaxData=mData.get(0);
        mMinData=mData.get(0);
        for(Float integer:mData){
            if(integer>mMaxData){
                mMaxData=integer;
            }
            if(integer<mMinData){
                mMinData=integer;
            }
        }
        LogUtils.e("mMaxData=="+mMaxData+"--mMinData="+mMinData);
        mMaxData=(mMaxData/10+1)*10;
        mMinData=(mMinData/10)*10;
        LogUtils.e("mMaxData=="+mMaxData+"--mMinData="+mMinData);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        mChartHeight=parent.getHeight()-dip2px(110);//图标区域总高度
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++) {//拿到每一个item 直接在每个item上面绘制就行
            View view = parent.getChildAt(i);
            //真实位置
            int position = parent.getChildAdapterPosition(view);
            Float data = mData.get(position);
            float pointY =(1-((data - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();

            //画前后的折线
            if(position==0){//第一项
                float nextPointY =(1-((mData.get(position+1) - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                path.reset();
                path.moveTo(view.getLeft()+view.getWidth()/2, pointY);
                path.cubicTo(view.getLeft()+view.getWidth(), pointY,view.getLeft()+view.getWidth(),nextPointY,view.getRight()+view.getWidth()/2,nextPointY);

                path.lineTo(view.getWidth(),mChartHeight+view.getPaddingTop());
                path.lineTo(view.getLeft()+view.getWidth()/2,mChartHeight+view.getPaddingTop());
                path.close();
                c.drawPath(path,mPaint);

            }else if(position==mData.size()-1){//最后一项
                float lastPointY =(1-((mData.get(position-1) - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                path.reset();
                path.moveTo(view.getLeft()-view.getWidth()/2,lastPointY);
                path.cubicTo(view.getLeft(),lastPointY,view.getLeft(),pointY,view.getLeft()+view.getWidth()/2,pointY);

                path.lineTo(view.getLeft()+view.getWidth()/2,mChartHeight+view.getPaddingTop());
                path.lineTo(view.getLeft(),mChartHeight+view.getPaddingTop());
                path.close();
                c.drawPath(path,mPaint);

            }else  {//根绝中间圆点  前后画折线、
                float nextPointY =(1-((mData.get(position+1) - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                float lastPointY =(1-((mData.get(position-1) - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();

                path.reset();
                path.moveTo(view.getLeft()-view.getWidth()/2,lastPointY);
                path.cubicTo(view.getLeft(),lastPointY,view.getLeft(),pointY,view.getLeft()+view.getWidth()/2,pointY);
                path.cubicTo(view.getLeft()+view.getWidth(), pointY,view.getLeft()+view.getWidth(),nextPointY,view.getRight()+view.getWidth()/2,nextPointY);

                path.lineTo(view.getRight(),mChartHeight+view.getPaddingTop());
                path.lineTo(view.getLeft(),mChartHeight+view.getPaddingTop());

                //每一个item的布局
                RectF rect=new RectF(view.getLeft(),view.getTop(),view.getRight(),view.getBottom());
                //每个区域只绘制自己item范围没的曲线
                itemBgPath.reset();
                itemBgPath.addRect(rect, Path.Direction.CW);
                //两个path取交集
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    path.op(itemBgPath, Path.Op.INTERSECT);
                }else{
                    /**
                     * Api 19下采用画布的clip实现
                     */
                    c.clipPath(itemBgPath, Region.Op.INTERSECT);
                }

                path.close();
                c.drawPath(path,mPaint);

            }
        }
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px( float dipValue) {
        float scale =mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
