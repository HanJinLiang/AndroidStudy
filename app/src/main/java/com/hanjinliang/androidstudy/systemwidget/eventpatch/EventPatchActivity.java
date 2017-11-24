package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanjinliang.androidstudy.R;

public class EventPatchActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_patch);
        mRecyclerView= (RecyclerView) findViewById(R.id.RecyclerView);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=new View(EventPatchActivity.this);
                view.setLayoutParams(new RecyclerView.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT));
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.getItemView().setBackgroundColor(position%2==0? Color.GREEN:Color.CYAN);
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      mSwipeRefreshLayout.setRefreshing(false);
                    }
                },3000)   ;
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }
    }
}
