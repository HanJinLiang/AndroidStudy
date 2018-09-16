package com.hanjinliang.androidstudy.customerviews.linerecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-15.
 * 利用RecyclerView实现的曲线图
 */
public class LineChartItemDecoration extends RecyclerView.ItemDecoration {
    Paint mPaint;
    Paint mCirclePaint;
    Context mContext;
    ArrayList<Float> mData;
    private float mMaxData,mMinData;
    private int mChartHeight;
    public LineChartItemDecoration(Context context,ArrayList<Float> mData) {
        this.mData=mData;
        mContext=context;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#6E7DBE"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(1));

        mCirclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#ffffff"));
        mCirclePaint.setStyle(Paint.Style.FILL);
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
            c.drawCircle(view.getLeft()+view.getWidth()/2, pointY, dip2px(5), mPaint);
            LogUtils.e("childCount="+childCount+"---i="+i+"------position="+position);
             //画前后的折线
            if(position==0){//第一项
                float nextY =(1-(((data+ mData.get(position+1))/2 - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                c.drawLine(view.getLeft()+view.getWidth()/2, pointY,view.getRight(),nextY,mPaint);
            }else if(position==mData.size()-1){//最后一项
                float lastY =(1-(((data+ mData.get(position-1))/2 - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                c.drawLine(view.getLeft()+view.getWidth()/2,pointY ,view.getLeft(),lastY,mPaint);
            }else{//根绝中间圆点  前后画折线、
                float lastY =(1-(((data+ mData.get(position-1))/2 - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                c.drawLine(view.getLeft()+view.getWidth()/2,pointY ,view.getLeft(),lastY,mPaint);
                float nextY =(1-(((data+ mData.get(position+1))/2 - mMinData)/(mMaxData - mMinData)))*mChartHeight+view.getPaddingTop();
                c.drawLine(view.getLeft()+view.getWidth()/2, pointY,view.getRight(),nextY,mPaint);
            }

            //画一个白色的
            c.drawCircle(view.getLeft()+view.getWidth()/2, pointY, dip2px(4), mCirclePaint);


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
