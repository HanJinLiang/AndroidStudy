package com.hanjinliang.androidstudy.systemwidget.netease;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.R;
import com.hbzhou.open.flowcamera.util.LogUtil;

import java.util.ArrayList;
import java.util.Random;


public class TabFragment extends Fragment {
    RecyclerView mRecyclerView;
    BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAdapter=new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_netease_song) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.index,item);
            }
        };

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.item_image);


        ArrayList<String> data=new ArrayList<>();
        int count = new Random().nextInt(100);
        LogUtil.e("count="+count);
        for(int i=0;i<count;i++){
            data.add(i+1+"");
        }
        mAdapter.addData(data);
    }


}
