package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class GroupRecyclerViewActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    BaseQuickAdapter<String,BaseViewHolder> mAdapter;
    ArrayList<String> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_view);
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupLineItemDecoration(this) {
            @Override
            String getItemGroupName(int pos) {
                return datas.get(pos);
            }
        });
        mAdapter=new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_app_list) {
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                helper.setText(R.id.app_name,item);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showLong("点击item  position=="+position);
            }
        });


        for(int i=0;i<300;i++){
//            datas.add("index------"+i/3);
            datas.add("index------"+i/4);
        }
        mAdapter.addData(datas);
    }

}
