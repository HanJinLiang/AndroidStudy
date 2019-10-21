package com.hanjinliang.androidstudy.customerviews.banner;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.systemwidget.eventpatch.EventPatchActivity;

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

    BannerView mBannerView;

    private int mDefaultIndex=10000*4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mRecyclerView= (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView_Indicator= (RecyclerView) findViewById(R.id.RecyclerView_Indicator);

        final LinearLayoutManager linearLayoutManager=new SmoothLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        urlList.add(new String("http://img.zcool.cn/community/01403a591155f9a801216a3ebcd49a.jpg"));
        urlList.add(new String("http://pic34.nipic.com/20131025/13675842_142638141156_2.jpg"));
        urlList.add(new String("http://pic.58pic.com/58pic/17/30/59/32I58PICeVg_1024.jpg"));
        urlList.add(new String("http://img.zcool.cn/community/01044d570724686ac72579480f5496.jpg"));

        MyBannerAdapter myBannerAdapter=new MyBannerAdapter();
        mRecyclerView.setAdapter(myBannerAdapter);
        mRecyclerView.scrollToPosition(mDefaultIndex);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        mRecyclerView_Indicator.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final MyIndicatorAdapter myIndicatorAdapter=new MyIndicatorAdapter(urlList);
        mRecyclerView_Indicator.setAdapter(myIndicatorAdapter);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //在非UI线程
                boolean isMainThread=Looper.getMainLooper() == Looper.myLooper();
                LogUtils.e(isMainThread);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isAutoPlay) {
                            mRecyclerView.smoothScrollToPosition(++mDefaultIndex);
                            myIndicatorAdapter.setPosition(mDefaultIndex % urlList.size());
                        }
                    }
                });
            }
        },1000,3000);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition() %urlList.size();
                    //得到指示器红点的位置
                    myIndicatorAdapter.setPosition(position);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        mBannerView=findViewById(R.id.bannerView);
        mBannerView.setPicList(urlList);
    }

    boolean isAutoPlay;
    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("onResume");
        isAutoPlay=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("onResume");
        isAutoPlay=false;
    }

    private class MyBannerAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LogUtils.e("onCreateViewHolder");
            ImageView imageView=new ImageView(BannerActivity.this);
            //VERTICAL 时候  宽高一定要注意  否则会导致不断的执行 onCreateViewHolder onBindViewHolder 最终越界
            //RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ScreenUtils.getScreenWidth(), SizeUtils.dp2px(200));
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return new MyViewHolder(imageView);
    }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LogUtils.e("onBindViewHolder");
            ImageView imageView=( (MyViewHolder) holder).getItemView();
            Glide.with(BannerActivity.this).load(urlList.get(position%urlList.size())).into(imageView);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
            //return 40;
        }

    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemView;
        public MyViewHolder(ImageView itemView) {
            super(itemView);
            this.itemView=itemView;
        }

        public ImageView getItemView() {
            return itemView;
        }

        public void setItemView(ImageView itemView) {
            this.itemView = itemView;
        }
    }




    private class MyIndicatorAdapter extends RecyclerView.Adapter {

        int currentPosition = 0;

        public void setPosition(int currentPosition) {
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


    /**
     * 调整RecyclerView 滚动的时间
     */
    public class SmoothLinearLayoutManager extends LinearLayoutManager {

        public SmoothLinearLayoutManager(Context context) {
            super(context);
        }

        public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 0.5f; //时间  单位是秒
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }

    }

}
