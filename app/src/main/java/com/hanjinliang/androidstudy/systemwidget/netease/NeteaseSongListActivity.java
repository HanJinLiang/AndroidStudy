package com.hanjinliang.androidstudy.systemwidget.netease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.Arrays;

/**
 * 网易云歌单效果
 */
public class NeteaseSongListActivity extends BaseActivity {
    RecyclerView mRecyclerView;

    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netease_song_list);
        coordinatorLayout=findView(R.id.coordinatorLayout);
        mRecyclerView=findView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mRecyclerView);

        mAdapter.addData(Arrays.asList("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"));
    }

    BaseQuickAdapter<String,BaseViewHolder>  mAdapter=new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_netease_song) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.index,helper.getLayoutPosition()+"");
        }

    };
}
