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

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-09-15.
 */

public class RecyclerLineViewActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView mRecyclerView3;
    RecyclerView mRecyclerView4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        for(int i=0;i<100;i++){
            mFloatArrayList.add((float) (Math.random()*80+20.0f));//20-100的随机数
        }

        mRecyclerView=findView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.addItemDecoration(new SignleBarChartItemDecoration(this,mFloatArrayList));
        mRecyclerView.addItemDecoration(new LineChartItemDecoration(this,mFloatArrayList));
        mRecyclerView.setAdapter(new MyAdapter(mFloatArrayList));



        mRecyclerView2=findView(R.id.recyclerView2);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView2.addItemDecoration(new CornerLineChartItemDecoration(this,mFloatArrayList));
        mRecyclerView2.setAdapter(new MyAdapter(mFloatArrayList));

        mRecyclerView3=findView(R.id.recyclerView3);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView3.addItemDecoration(new SignleBarChartItemDecoration(this,mFloatArrayList));
        mRecyclerView3.setAdapter(new MyAdapter(mFloatArrayList));



        for(int i=0;i<100;i++){
            mFloatArrayList2.add((float) (Math.random()*80+20.0f));//20-100的随机数
        }
        mRecyclerView4=findView(R.id.recyclerView4);
        mRecyclerView4.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView4.addItemDecoration(new MutiBarChartItemDecoration(this,mFloatArrayList,mFloatArrayList2));
        mRecyclerView4.setAdapter(new MyAdapter(mFloatArrayList));
    }

    ArrayList<Float> mFloatArrayList=new ArrayList<>();
    ArrayList<Float> mFloatArrayList2=new ArrayList<>();


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
            holder.tv_value1.setText(String.valueOf(mFloatArrayList.get(position)).substring(0,4));
            holder.tv_value2.setText(""+position);
        }

        @Override
        public int getItemCount() {
            return mFloatArrayList==null?0:mFloatArrayList.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv_content;
        public TextView tv_value1;
        public TextView tv_value2;
        public MyHolder(View itemView) {
            super(itemView);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_value1=itemView.findViewById(R.id.tv_value1);
            tv_value2=itemView.findViewById(R.id.tv_value2);
        }
    }
}
