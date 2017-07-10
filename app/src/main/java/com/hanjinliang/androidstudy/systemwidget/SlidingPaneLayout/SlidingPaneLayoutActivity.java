package com.hanjinliang.androidstudy.systemwidget.SlidingPaneLayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

public class SlidingPaneLayoutActivity extends BaseActivity {
    SlidingPaneLayout mSlidingPaneLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
        mSlidingPaneLayout=findView(R.id.sliding_pane_layout);
        mViewPager=findView(R.id.viewpager);

        mViewPager.setAdapter(new MyPageAdapter(this));

        mSlidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
    }

    public class MyPageAdapter extends PagerAdapter{

        ArrayList<ImageView>  allImageViews=new ArrayList<>();
        public  MyPageAdapter(Context context){
            for(int i=0;i<4;i++){
                ImageView imageView=new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT));
                imageView.setImageResource(R.drawable.banner);
                allImageViews.add(imageView);
            }
        }
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             container.addView(allImageViews.get(position));
            return allImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(allImageViews.get(position));
        }
    }
}
