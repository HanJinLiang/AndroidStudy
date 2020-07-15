package com.hanjinliang.androidstudy.systemwidget.netease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;
import com.hbzhou.open.flowcamera.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 网易云歌单效果
 * 变成了淘宝首页  哈哈  主要都是用的 CoordinatorLayout效果
 */
public class NeteaseSongListActivity extends BaseActivity {

    ViewPager mViewPager;
    TabLayout tabLayout;


    CoordinatorLayout coordinatorLayout;

    LinearLayout mTopLayout;

    private String[] titles = new String[]{"最新","热门","我的"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    FmPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netease_song_list);
        coordinatorLayout=findView(R.id.coordinatorLayout);
        tabLayout=findView(R.id.tabLayout);
        mViewPager=findView(R.id.viewpager);
        mTopLayout=findView(R.id.topLayout);


        for(int i=0;i<titles.length;i++){
            fragments.add(new TabFragment());
            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.setupWithViewPager(mViewPager,false);
        pagerAdapter = new FmPagerAdapter(fragments,getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
    }

    class  FmPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        public FmPagerAdapter(ArrayList<Fragment> fragments,@NonNull FragmentManager fm) {
            super(fm);
            this.fragments=fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
           return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments==null?0:fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

//    /**
//     * 当列表没有数据  显示空视图的时候  不给滑动
//     * @param
//     */
//    private void canNestScroll(boolean canNestScroll) {
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)mTopLayout.getLayoutParams();
//        if(canNestScroll){
//            //可以滑动，实现折叠悬浮效果
//            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
//        }else {
//            //设置不能滑动
//            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//        }
//    }


}
