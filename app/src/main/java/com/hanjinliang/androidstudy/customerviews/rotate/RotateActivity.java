package com.hanjinliang.androidstudy.customerviews.rotate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.hanjinliang.androidstudy.R;

/**
 * Created by monkey on 2019-04-24 14:08.
 * Describe:翻转效果
 */

public class RotateActivity extends Activity{
    Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_3d);
        mButton=findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 计算中心点（这里是使用view的中心作为旋转的中心点）
                final float centerX = v.getWidth() / 2.0f;
                final float centerY = v.getHeight() / 2.0f;

                //括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）
                final Rotate3dAnimation rotation = new Rotate3dAnimation(RotateActivity.this, 0, 180, centerX, centerY, 0f, false);

                rotation.setDuration(1000);                         //设置动画时长
                rotation.setFillAfter(false);                        //保持旋转后效果
                rotation.setInterpolator(new DecelerateInterpolator()); //设置插值器
                v.startAnimation(rotation);
            }
        });
    }
}
