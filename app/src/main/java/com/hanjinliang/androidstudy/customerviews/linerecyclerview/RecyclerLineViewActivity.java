package com.hanjinliang.androidstudy.customerviews.linerecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.wheelpicker.LineChartItemDecoration;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-15.
 */

public class RecyclerLineViewActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView=findView(R.id.recyclerView);

        for(int i=0;i<100;i++){
            mFloatArrayList.add((float) (Math.random()*80+20.0f));//20-100的随机数
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.addItemDecoration(new LineChartItemDecoration(this,mFloatArrayList));
        mRecyclerView.setAdapter(new MyAdapter(mFloatArrayList));


    }

    ArrayList<Float> mFloatArrayList=new ArrayList<>();


    public class MyAdapter extends RecyclerView.Adapter<MyHolder>{
        ArrayList<Float> mFloatArrayList;
        public MyAdapter(ArrayList<Float> mFloatArrayList) {
            this.mFloatArrayList=mFloatArrayList;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_line, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv_value1.setText(""+mFloatArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return mFloatArrayList==null?0:mFloatArrayList.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv_content;
        public TextView tv_value1;
        public MyHolder(View itemView) {
            super(itemView);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_value1=itemView.findViewById(R.id.tv_value1);
        }
    }
}
