package com.hanjinliang.androidstudy.customerviews.banner;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by monkey on 2019-08-22 13:42.
 * Describe:广告Banner
 */
public class BannerView extends LinearLayout {
    View mBannerView;
    RecyclerView mBannerRecyclerView;

    ArrayList<String> picList=new ArrayList<>();
    MyBannerAdapter mBannerAdapter;

    private Timer mTimer;

    private int mFirstVisibleItemPosition;
    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBannerView=LayoutInflater.from(context).inflate(R.layout.view_banner,this,false);
        addView(mBannerView);

        mBannerRecyclerView= mBannerView.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager=new SmoothLinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mBannerRecyclerView.setLayoutManager(linearLayoutManager);

        mBannerAdapter=new MyBannerAdapter();
        mBannerRecyclerView.setAdapter(mBannerAdapter);
        mBannerRecyclerView.scrollToPosition(10000*4);
        mBannerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE) {
                    mFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                }
            }
        });
        new PagerSnapHelper().attachToRecyclerView(mBannerRecyclerView);

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

    private class MyBannerAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LogUtils.e("onCreateViewHolder");
            ImageView imageView=new ImageView(getContext());
            //VERTICAL 时候  宽高一定要注意  否则会导致不断的执行 onCreateViewHolder onBindViewHolder 最终越界
            //RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ScreenUtils.getScreenWidth(), SizeUtils.dp2px(200));
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return new BannerView.MyViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
            LogUtils.e("onBindViewHolder");
            ImageView imageView=( (BannerView.MyViewHolder) holder).getItemView();
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("position=="+position);
                }
            });

            Glide.with(BannerView.this.getContext()).load(picList.get(position%picList.size())).into(imageView);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
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

    public void setPicList(ArrayList<String> picList) {
        this.picList = picList;
        mBannerAdapter.notifyDataSetChanged();
        startPlay();
    }

    public void startPlay(){
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mBannerRecyclerView.smoothScrollToPosition(mFirstVisibleItemPosition+1);
            }
        },1000,3000);
    }

    public void stopPlay(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
    }
}
