package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupRecyclerViewActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    BaseSectionQuickAdapter<MySection,BaseViewHolder> mAdapter;
    ArrayList<MySection> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_view);
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new StickyDecoration(this) {
            @Override
            String getItemGroupName(int pos) {
                return datas.get(pos).header;
            }
        });
        mAdapter=new BaseSectionQuickAdapter<MySection,BaseViewHolder>(R.layout.item_app_list,R.layout.item_recycler_group,null) {


            @Override
            protected void convert(BaseViewHolder helper, MySection item) {
                helper.setText(R.id.app_name,item.getItem());
            }

            @Override
            protected void convertHead(BaseViewHolder helper, MySection item) {
                helper.setText(R.id.groupName,item.header);
                helper.addOnClickListener(R.id.groupNameClick);
            }
        };
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.groupNameClick){
                    ToastUtils.showShort(datas.get(position).header);
                }
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showLong("点击item  position=="+position);
            }
        });


        for(int i=0;i<data.size();i++){
            String value = data.get(i);
            if(i==0||!value.substring(0,1).equals(data.get(i-1).substring(0,1))){
                //和上一个不相同
                datas.add(new MySection(true,value.substring(0,1)));
            }
            datas.add(new MySection(false,value.substring(0,1),value));
        }
        mAdapter.addData(datas);
    }

    private List<String> data= Arrays.asList("A1","A2","A3",
            "B1","B2","B3","B4",
            "C1","C2","C3","C4","C5",
            "D1","D2","D3","D4","D5","D6",
            "E1","E2","E3","E4","E5","E6","E7"
            );

}
