package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by hjl on 2019-03-01 14:33.
 * Describe:分组的ItemDecoration
 */

public abstract class GroupLineItemDecoration extends RecyclerView.ItemDecoration {
    private  int itemHeight= SizeUtils.dp2px(50);
    Paint mBgPaint;
    Paint mTextPaint;

    private Context mContext;

    public GroupLineItemDecoration(Context context){
        mContext=context;
        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.RED);

        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(SizeUtils.sp2px(18));
        mTextPaint.setColor(Color.BLACK);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int itemCount = state.getItemCount();//总的Data的size
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for(int i=0;i<childCount;i++){
            View view = parent.getChildAt(i);
            int pos=parent.getChildAdapterPosition(view);
            if(isNewGroup(pos)){//是新的分组  或者是第一条 吸顶效果需要
                //分组Rect区域
                RectF rect=new RectF(left,view.getTop()-itemHeight,right,view.getTop());
                drawGroup(c,rect,pos);
            }
        }
    }
    //------------------单纯的绘制出header onDraw（）无须重写 ----------------------
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//        int itemCount = state.getItemCount();//总的Data的size
//        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        for(int i=0;i<childCount;i++){
//            View view = parent.getChildAt(i);
//            int pos=parent.getChildAdapterPosition(view);
//            if(i==0||isNewGroup(pos)){//是新的分组
//                //分组Rect的bottom
//                float rectBottom = Math.max(itemHeight,view.getTop());
//                if (pos + 1 < itemCount) {//防止越界
//                    //最上面悬浮移动效果
//                    if ((!getItemGroupName(pos + 1).equals(getItemGroupName(pos)))
//                            && view.getBottom() < rectBottom ) {//组内最后一个view进入了header
//                        rectBottom = view.getBottom();
//                    }
//                }
//                //分组Rect区域
//                RectF rect=new RectF(left,rectBottom-itemHeight,right,rectBottom);
//                c.drawRect(rect,mBgPaint);
//                //文字
//                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//                float top = fontMetrics.top;//为基线到字体上边框的距离
//                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
//
//                c.drawText(getItemGroupName(pos),rect.left,rect.centerY()-(top+bottom)/2,mTextPaint);
//            }
//        }
//    }
    //-----------------通过移动HeaderView实现------------------
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();//总的Data的size
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        View view = parent.getChildAt(0);
        int pos=parent.getChildAdapterPosition(view);
        //分组Rect的bottom
        float rectBottom = Math.max(itemHeight,view.getTop());
        if (pos + 1 < itemCount) {//防止越界
            //最上面悬浮移动效果
            if ((!getItemGroupName(pos + 1).equals(getItemGroupName(pos)))
                    && view.getBottom() < rectBottom ) {//组内最后一个view进入了header
                rectBottom = view.getBottom();
            }
        }
        //分组Rect区域
        RectF rect=new RectF(left,rectBottom-itemHeight,right,rectBottom);
        drawHeader(parent,rect,pos);
    }
    View groupView;
    TextView groupNameTv;
    /**
     * 绘制分组View
     * @param rect
     * @param pos
     */
    private void drawHeader(RecyclerView parent,RectF rect,final int pos){
        //通过设置topMargin 达到吸顶效果
        if(groupView==null){
            RelativeLayout parent1 = (RelativeLayout) parent.getParent();
            groupView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_group, parent1,false);
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            groupView.setLayoutParams(layoutParams);
            parent1.addView(groupView);
            groupNameTv=groupView.findViewById(R.id.groupName);
        }
        //吸顶点击效果
        groupView.findViewById(R.id.groupNameClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getItemGroupName(pos));
            }
        });
        groupNameTv.setText(getItemGroupName(pos));
        ((RelativeLayout.LayoutParams)groupView.getLayoutParams()).topMargin= (int) rect.top;
        groupView.setLayoutParams(groupView.getLayoutParams());

    }

    /**
     * 绘制分组View
     * @param c
     * @param rect
     * @param pos
     */
    private void drawGroup(Canvas c,RectF rect,int pos){
        c.drawRect(rect,mBgPaint);
        //文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离

        c.drawText(getItemGroupName(pos),rect.left,rect.centerY()-(top+bottom)/2,mTextPaint);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos=parent.getChildAdapterPosition(view);
        if(isNewGroup(pos))
        //设置item的偏移量
        outRect.top=itemHeight;
    }

    /**
     * 是否是一个新的分组
     * @param pos
     * @return
     */
    private boolean isNewGroup(int pos){
        if(pos==0){
            return true;
        }
        if(!getItemGroupName(pos-1).equals(getItemGroupName(pos))){
            return true;
        }

        return false;
    }

    /**
     * 根据pos获取组名
     * @param pos
     * @return
     */
    abstract String getItemGroupName(int pos);
}
