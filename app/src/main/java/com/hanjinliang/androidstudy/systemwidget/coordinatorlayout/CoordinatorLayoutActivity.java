package com.hanjinliang.androidstudy.systemwidget.coordinatorlayout;

import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * CoordinatorLayout   协调布局
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {
    ViewPager mViewPager;
    ArrayList<TestFragment> mFragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);
        mViewPager=findViewById(R.id.viewPager);
        mFragments.add(TestFragment.newInstance("1-----"));
        mFragments.add(TestFragment.newInstance("2-----"));
        mFragments.add(TestFragment.newInstance("3-----"));
        mFragments.add(TestFragment.newInstance("4-----"));
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });


    }
}
