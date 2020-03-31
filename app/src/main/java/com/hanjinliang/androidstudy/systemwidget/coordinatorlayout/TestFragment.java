package com.hanjinliang.androidstudy.systemwidget.coordinatorlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.CustomerProgressBar.TaskProgressView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2018-08-13.
 */

public class TestFragment extends Fragment {

    public static  TestFragment newInstance(String content){
        TestFragment fragment=new TestFragment();
        Bundle bundle=new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }
    String mContent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent=getArguments().getString("content");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_list_select,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_task,parent,false));
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.mTaskProgressView.setPercent(position);
                holder.mTextView.setText(mContent+position);
            }

            @Override
            public int getItemCount() {
                return 200;
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showLong("onRefresh");
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TaskProgressView mTaskProgressView;
        public TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.item_text);
            mTaskProgressView=itemView.findViewById(R.id.taskProgress);
        }
    }
}
