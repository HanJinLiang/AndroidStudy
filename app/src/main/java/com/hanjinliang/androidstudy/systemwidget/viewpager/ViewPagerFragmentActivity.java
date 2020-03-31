package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.SizeUtils;
import com.google.android.material.tabs.TabLayout;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerFragmentActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    ArrayList<Fragment> mAllFragment=new ArrayList<>();
    ArrayList<String> mAllTitle=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);
        mViewPager= (ViewPager) findViewById(R.id.content_layout);
        mTabLayout= (TabLayout) findViewById(R.id.tabLayout);
        
        initView();
    }

    private void initView() {
        mAllFragment.add(TestFragment.newInstance("TestFragment 1"));
        mAllFragment.add(TestFragment.newInstance("TestFragment 2"));
        mAllFragment.add(TestFragment.newInstance("TestFragment 3"));
        mAllFragment.add(TestFragment.newInstance("TestFragment 4"));

        mAllTitle.add("Test1");
        mAllTitle.add("Test2");
        mAllTitle.add("Test3");
        mAllTitle.add("Test4");

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mAllFragment.get(position);
            }

            @Override
            public int getCount() {
                return mAllFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mAllTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        mTabLayout.setSelectedTabIndicatorHeight(SizeUtils.dp2px(5));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置模式 MODE_FIXED表示根据屏幕宽度等分 MODE_SCROLLABLE表示自适应宽度 滚动
        mTabLayout.getTabAt(0).setIcon(R.drawable.icon_header);
        //mTabLayout.setLayoutMode(TabLayout.LAYOUT_MODE_CLIP_BOUNDS);
    }
}
