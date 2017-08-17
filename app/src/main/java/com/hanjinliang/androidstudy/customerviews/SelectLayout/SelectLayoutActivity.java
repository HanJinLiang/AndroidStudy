package com.hanjinliang.androidstudy.customerviews.SelectLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

public class SelectLayoutActivity extends AppCompatActivity {

    SelectLayout layout_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_layout);

        layout_container= (SelectLayout) findViewById(R.id.layout_container);


        layout_container.setOnSelectChangedListener(new SelectLayout.OnSelectChangedListener() {
            @Override
            public void onSelectChange(SelectLayout.CurrentSelect current) {
                if(current==SelectLayout.CurrentSelect.left){
                    ToastUtils.showShort("left");
                }else if(current==SelectLayout.CurrentSelect.center){
                    ToastUtils.showShort("center");
                }else if(current==SelectLayout.CurrentSelect.right){
                    ToastUtils.showShort("right");
                }
            }

            @Override
            public void onSelectClick() {
                ToastUtils.showShort("选中被点击");
            }
        });
    }



}
