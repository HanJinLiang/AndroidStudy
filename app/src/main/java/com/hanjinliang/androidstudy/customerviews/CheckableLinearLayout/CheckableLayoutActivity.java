package com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class CheckableLayoutActivity extends BaseActivity {
    ListView mListView;
    ArrayList<String> mData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkable_layout);
        mListView=findView(R.id.listView);
        for(int i=0;i<100;i++){
            mData.add("测试数据"+i);
        }
        MyAdapter myAdapter=new MyAdapter(this,mData);
        mListView.setAdapter(myAdapter);

        findView(R.id.selectAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<mData.size();i++)
                    mListView.setItemChecked(i,true);
            }
        });

        findView(R.id.getSelected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray sparseBooleanArray=mListView.getCheckedItemPositions();
                for(int i=0;i<sparseBooleanArray.size();i++){
                   boolean isChcek=sparseBooleanArray.get(i);
                    Log.e("SparseBooleanArray","SparseBooleanArray"+isChcek);
                }
            }
        });
    }
}
