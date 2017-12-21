package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * ViewPager基本用法
 */
public class ViewPagerBaseActivity extends AppCompatActivity {

    int[]  mPics=new int[]{R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};
    ArrayList<View> mViews=new ArrayList<>();
    ViewPager mViewPager;
    TextView mBannerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_base);
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mBannerTitle= (TextView) findViewById(R.id.bannerTitle);
        initView();
        handler.sendEmptyMessageDelayed(1, 3000);
    }
    private int count=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==1) {
               if(count>=mViews.size()){
                   count=0;
               }
               mViewPager.setCurrentItem(count++);
           }
            handler.sendEmptyMessageDelayed(1, 3000);
        }
    };


    private void initView() {
        LayoutInflater inflater=LayoutInflater.from(this);
        for(int i=0;i<mPics.length;i++){
            View view=inflater.inflate(R.layout.item_viewpager_base,null);
            ImageView imageView= (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(mPics[i]);
            mViews.add(view);
        }

        View view=inflater.inflate(R.layout.item_viewpager_base,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(mPics[(mPics.length-1)]);
        mViews.add(0,view);

        View view1=inflater.inflate(R.layout.item_viewpager_base,null);
        ImageView imageView1= (ImageView) view1.findViewById(R.id.image);
        imageView1.setImageResource(mPics[0]);
        mViews.add(view1);


        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPics.length+2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e(position);
                count=position;
                if(position==0){
                    mBannerTitle.setText("当前广告位---"+mPics.length);
                }else if(position==mViews.size()-1){
                    mBannerTitle.setText("当前广告位---"+1);
                }else{
                    mBannerTitle.setText("当前广告位---"+(position));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state!=ViewPager.SCROLL_STATE_IDLE){
                    return;//没有停止  直接返回
                }

                if(count==0){//第一张
                    mViewPager.setCurrentItem(mViews.size()-2,false);
                }else if(count==mViews.size()-1){
                    mViewPager.setCurrentItem(1,false);
                }
            }
        });

        mViewPager.setCurrentItem(count,false);
    }
}
