package com.hanjinliang.androidstudy.systemwidget.listselect;

import android.os.Bundle;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class ListSelectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_select);
        RecyclerView recyclerView=findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        //recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this,R.anim.select_list));
        ArrayList<TestSelectBean> datas=new ArrayList<>();
        for(int i=0;i<100;i++){
            datas.add(new TestSelectBean("测试--"+i));
        }
        recyclerView.setAdapter(new ListAdapter(this,recyclerView,datas));
        recyclerView.scheduleLayoutAnimation();
    }
}
