package com.hanjinliang.androidstudy.customerviews.linerecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-15.
 * 利用RecyclerView实现的曲线图  两个柱状图
 */
public class MutiBarChartItemDecoration extends RecyclerView.ItemDecoration {
    Paint mPaint;
    Paint mPaint2;
    Context mContext;
    ArrayList<Float> mData;
    ArrayList<Float> mData2;
    private float mMaxData,mMaxData2;
    private int mChartHeight;
    private float mPaddingWidthRatio=0.1f;//柱状图站宽度
    private float mBarWidthRatio;//柱状图站宽度
    public MutiBarChartItemDecoration(Context context, ArrayList<Float> mData,ArrayList<Float> mData2) {
        this.mData=mData;
        this.mData2=mData2;
        mContext=context;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#6E7DBE"));
        mPaint.setStyle(Paint.Style.FILL);

        mPaint2=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.parseColor("#DADEED"));
        mPaint2.setStyle(Paint.Style.FILL);

        parseData();
    }

    /**
     * 获取到最大值和最小值
     */
    private void parseData() {
        mMaxData=mData.get(0);
        for(Float integer:mData){
            if(integer>mMaxData){
                mMaxData=integer;
            }
        }
        mMaxData=(mMaxData/10+1)*10;

        mMaxData2=mData2.get(0);
        for(Float integer:mData2){
            if(integer>mMaxData2){
                mMaxData2=integer;
            }
        }
        mMaxData2=(mMaxData2/10+1)*10;

        mBarWidthRatio=(1-3*mPaddingWidthRatio)/2;
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
            Float data2 = mData2.get(position);
            float pointY =(1-data/mMaxData)*mChartHeight+view.getPaddingTop();
            float pointY2 =(1-data2/mMaxData2)*mChartHeight+view.getPaddingTop();
            LogUtils.e("childCount="+childCount+"---i="+i+"------position="+position);


            int viewRealWidth=view.getWidth()-view.getPaddingLeft()-view.getPaddingRight();
            c.drawRect(view.getLeft()+view.getPaddingLeft()+viewRealWidth*mPaddingWidthRatio,pointY
            ,view.getLeft()+view.getPaddingLeft()+viewRealWidth*(mPaddingWidthRatio+mBarWidthRatio),mChartHeight+view.getPaddingTop(),mPaint);

            c.drawRect(view.getLeft()+view.getPaddingLeft()+viewRealWidth*(mPaddingWidthRatio*2+mBarWidthRatio),pointY2
                    ,view.getLeft()+view.getPaddingLeft()+viewRealWidth*(mPaddingWidthRatio*2+mBarWidthRatio*2),mChartHeight+view.getPaddingTop(),mPaint2);
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
