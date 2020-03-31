package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import android.content.Context;
import android.graphics.Canvas;
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

public abstract class StickyDecoration extends RecyclerView.ItemDecoration {
    private  int itemHeight= SizeUtils.dp2px(50);
    private Context mContext;

    public StickyDecoration(Context context){
        mContext=context;
    }

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
     * 根据pos获取组名
     * @param pos
     * @return
     */
    abstract String getItemGroupName(int pos);
}
