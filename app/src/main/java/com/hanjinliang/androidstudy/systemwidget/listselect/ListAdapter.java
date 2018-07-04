package com.hanjinliang.androidstudy.systemwidget.listselect;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2018-04-26.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    ArrayList<TestSelectBean> datas;
    RecyclerView recyclerView;
    Context mContext;
    public ListAdapter(Context context,RecyclerView recyclerView,ArrayList<TestSelectBean> datas){
        mContext=context;
        this.recyclerView=recyclerView;
        this.datas=datas;
    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ListViewHolder holder,final int position) {
        holder.mButton.setText(datas.get(position).getName());
        if(datas.get(position).isSelected()){
            holder.mCardView.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.corner_list_selected));
        }else{
            holder.mCardView.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.corner_list_unselected));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedPos==position){
                    LogUtils.d("已经选中");
                    return;
                }
                //https://www.jianshu.com/p/1ac13f74da63
                //单选模式----- 标志位
//                for(TestSelectBean testSelectBean:datas){
//                    testSelectBean.setSelected(false);
//                }
//                datas.get(position).setSelected(true);
//                notifyDataSetChanged();

                //实现单选方法二： notifyItemChanged() 定向刷新两个视图
                //如果勾选的不是已经勾选状态的Item
//                if (mSelectedPos!=position){
//                    if(mSelectedPos!=-1) {
//                        //先取消上个item的勾选状态
//                        datas.get(mSelectedPos).setSelected(false);
//                        notifyItemChanged(mSelectedPos);
//                    }
//                    //设置新Item的勾选状态
//                    mSelectedPos = position;
//                    datas.get(mSelectedPos).setSelected(true);
//                    notifyItemChanged(mSelectedPos);
//                }

                //实现单选方法三： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                if(mSelectedPos!=-1) {
                    ListViewHolder viewHolder = (ListViewHolder) recyclerView.findViewHolderForLayoutPosition(mSelectedPos);
                    if (viewHolder != null) {//还在屏幕里
                        viewHolder.mCardView.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.corner_list_unselected));
                    } else {
                        //add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                        //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                        notifyItemChanged(mSelectedPos);
                    }
                    datas.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
                }
                //设置新Item的勾选状态
                mSelectedPos = position;
                datas.get(mSelectedPos).setSelected(true);
                holder.mCardView.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.corner_list_selected));


            }
        });
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public Button mButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            mCardView=itemView.findViewById(R.id.cardView);
            mButton=itemView.findViewById(R.id.button);
        }
    }


    private int mSelectedPos = -1;//实现单选  方法二，变量保存当前选中的position
}
