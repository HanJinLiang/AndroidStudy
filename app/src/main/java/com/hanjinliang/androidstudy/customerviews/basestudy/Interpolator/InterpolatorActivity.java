package com.hanjinliang.androidstudy.customerviews.basestudy.Interpolator;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Interpolator学习
 *
 *
 */
//    AccelerateDecelerateInterpolator   在动画开始与结束的地方速率改变比较慢，在中间的时候加速
//    AccelerateInterpolator                     在动画开始的地方速率改变比较慢，然后开始加速
//    AnticipateInterpolator                      开始的时候向后然后向前甩
//    AnticipateOvershootInterpolator     开始的时候向后然后向前甩一定值后返回最后的值
//    BounceInterpolator                          动画结束的时候弹起
//    CycleInterpolator                             动画循环播放特定的次数，速率改变沿着正弦曲线
//    DecelerateInterpolator                    在动画开始的地方快然后慢
//    LinearInterpolator                            以常量速率改变
//    OvershootInterpolator                      向前甩一定值后再回到原来位置
public class InterpolatorActivity extends BaseActivity {
    List<String> mAllType= Arrays.asList("AccelerateDecelerate","Accelerate","Anticipate","AnticipateOvershoot","Bounce","Cycle","Decelerate","Linear","Overshoot");
    List<BaseInterpolator> mAllInterpolator=Arrays.asList(new AccelerateDecelerateInterpolator(),
            new AccelerateInterpolator(),new AnticipateInterpolator(),new AnticipateOvershootInterpolator(),new BounceInterpolator(),
            new CycleInterpolator(1.0f),new DecelerateInterpolator(),new LinearInterpolator(),new OvershootInterpolator());
    RecyclerView mRecyclerView;
    TextView testView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        mRecyclerView=findView(R.id.RecyclerView);
        testView=findView(R.id.testView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                MyViewHolder holder = new MyViewHolder(new Button(InterpolatorActivity.this));
                return holder;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onClick(View v) {
                        startAnim(mAllInterpolator.get(position));
                    }
                });
                holder.tv.setText(mAllType.get(position));
            }

            @Override
            public int getItemCount() {
                return mAllType.size();
            }


        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {

        public  Button tv;

        public MyViewHolder( Button view)
        {
            super(view);
            tv = view;
        }
    }

    public void startAnim(Interpolator baseInterpolator){
        ObjectAnimator animator = ObjectAnimator.ofFloat(testView, "translationX", 0, 600);
        animator.setDuration(2000);
        animator.setInterpolator(baseInterpolator);
        animator.start();
    }


}
