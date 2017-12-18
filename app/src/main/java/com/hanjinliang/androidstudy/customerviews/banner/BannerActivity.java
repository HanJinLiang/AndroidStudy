package com.hanjinliang.androidstudy.customerviews.banner;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 基于RecyclerView的Banner
 */
public class BannerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView_Indicator;
    ArrayList<String> urlList=new ArrayList<>();

    private int mDefaultIndex=10000*4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mRecyclerView= (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView_Indicator= (RecyclerView) findViewById(R.id.RecyclerView_Indicator);

        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        urlList.add(new String("http://img.zcool.cn/community/01403a591155f9a801216a3ebcd49a.jpg"));
        urlList.add(new String("http://pic34.nipic.com/20131025/13675842_142638141156_2.jpg"));
        urlList.add(new String("http://pic.58pic.com/58pic/17/30/59/32I58PICeVg_1024.jpg"));
        urlList.add(new String("http://img.zcool.cn/community/01044d570724686ac72579480f5496.jpg"));
        MyBannerAdapter myBannerAdapter=new MyBannerAdapter(urlList);
        mRecyclerView.setAdapter(myBannerAdapter);
        mRecyclerView.scrollToPosition(mDefaultIndex);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        mRecyclerView_Indicator.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final MyIndicatorAdapter myIndicatorAdapter=new MyIndicatorAdapter(urlList);
        mRecyclerView_Indicator.setAdapter(myIndicatorAdapter);




        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                boolean isMainThread=Looper.getMainLooper() == Looper.myLooper();
                LogUtils.e(isMainThread);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(++mDefaultIndex);
                        myIndicatorAdapter.setPosition(mDefaultIndex%urlList.size());
                    }
                });
            }
        },1000,3000);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int first = linearLayoutManager.findFirstVisibleItemPosition();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                myIndicatorAdapter.setPosition(first%urlList.size());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //解决连续滑动时指示器不更新的问题
                if (urlList.size() < 2) return;
                int firstReal = linearLayoutManager.findFirstVisibleItemPosition();
                LogUtils.e(firstReal);
                View viewFirst = linearLayoutManager.findViewByPosition(firstReal);
                float width = recyclerView.getWidth();
                if (width != 0 && viewFirst != null) {
                    float right = viewFirst.getRight();
                    float ratio = right / width;
                    if (ratio > 0.8) {
                        myIndicatorAdapter.setPosition(firstReal%urlList.size());
                    } else if (ratio < 0.2) {
                       myIndicatorAdapter.setPosition((firstReal+1)%urlList.size());
                    }
                }
            }
        });
    }

    private class MyBannerAdapter extends RecyclerView.Adapter {
        ArrayList<String> urlList;

        public MyBannerAdapter(ArrayList<String> urlList){
            this.urlList=urlList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView=new ImageView(parent.getContext());
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return new RecyclerView.ViewHolder(imageView) { };
    }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView imageView= (ImageView) holder.itemView;
            Glide.with(BannerActivity.this).load(urlList.get(position%urlList.size())).into(imageView);
        }

        @Override
        public int getItemCount() {
            //如果只有一张图片就不滑动了
            return urlList.size() < 2 ? 1 : Integer.MAX_VALUE;
        }

    }




    private class MyIndicatorAdapter extends RecyclerView.Adapter {

        int currentPosition = 0;

        public void setPosition(int currentPosition) {
            LogUtils.e(currentPosition);
            if(this.currentPosition==currentPosition){
                return;
            }
            this.currentPosition = currentPosition;
            notifyDataSetChanged();
        }

        ArrayList<String> urlList;

        public MyIndicatorAdapter(ArrayList<String> urlList){
            this.urlList=urlList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView=new ImageView(parent.getContext());
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(50, 50);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return new RecyclerView.ViewHolder(imageView) { };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView imageView= (ImageView) holder.itemView;
            if (position==currentPosition){
                imageView.setImageResource(R.drawable.round_red);
            }else{
                imageView.setImageResource(R.drawable.round_white);
            }
        }

        @Override
        public int getItemCount() {
            //如果只有一张图片就不滑动了
            return urlList.size();
        }

    }


}
