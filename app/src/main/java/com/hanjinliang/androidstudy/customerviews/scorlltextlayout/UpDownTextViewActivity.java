package com.hanjinliang.androidstudy.customerviews.scorlltextlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * Created by monkey on 2019-07-02 13:36.
 * Describe:
 */

public class UpDownTextViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updowntextviewactivity);
        fun();
    }

    public void fun(){
        final AutoVerticalLinearLayout main_auto_layout =  findViewById(R.id.textView1);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                main_auto_layout.clear();
                main_auto_layout.addData("5");
//                Animation outAnim = main_auto_layout.getOutAnim();
//                // 设置动画结束操作
//                if (outAnim != null) {
//                    outAnim.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            main_auto_layout.clear();
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                }
                main_auto_layout.startAnim();
            }
        });
    }

}
