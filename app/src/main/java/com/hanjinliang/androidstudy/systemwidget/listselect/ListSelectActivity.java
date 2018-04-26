package com.hanjinliang.androidstudy.systemwidget.listselect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class ListSelectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_select);
        RecyclerView recyclerView=findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this,R.anim.select_list));
        ArrayList<TestSelectBean> datas=new ArrayList<>();
        for(int i=0;i<100;i++){
            datas.add(new TestSelectBean("测试--"+i));
        }
        recyclerView.setAdapter(new ListAdapter(this,datas));
        recyclerView.scheduleLayoutAnimation();
    }
}