package com.hanjinliang.androidstudy.systemwidget.netease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;
import com.hbzhou.open.flowcamera.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 网易云歌单效果
 */
public class NeteaseSongListActivity extends BaseActivity {
    RecyclerView mRecyclerView;

    CoordinatorLayout coordinatorLayout;

    LinearLayout mTopLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netease_song_list);
        coordinatorLayout=findView(R.id.coordinatorLayout);
        mTopLayout=findView(R.id.topLayout);
        mRecyclerView=findView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.item_image);
        ArrayList<String> data=new ArrayList<>();
        int count = new Random().nextInt(2);
        LogUtil.e("count="+count);
        for(int i=0;i<count;i++){
            data.add(i+1+"");
        }
        canNestScroll(data.size()>0);
        mAdapter.addData(data);
    }

    /**
     * 当列表没有数据  显示空视图的时候  不给滑动
     * @param
     */
    private void canNestScroll(boolean canNestScroll) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)mTopLayout.getLayoutParams();
        if(canNestScroll){
            //可以滑动，实现折叠悬浮效果
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
        }else {
            //设置不能滑动
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        }
    }

    BaseQuickAdapter<String,BaseViewHolder>  mAdapter=new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_netease_song) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.index,item);
        }

    };
}
