package com.hanjinliang.androidstudy.thirdLibs.guide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guideview.GuideView;
import com.guideview.GuideViewHelper;
import com.guideview.LightType;
import com.guideview.style.CenterBottomStyle;
import com.guideview.style.CenterTopStyle;
import com.guideview.style.LeftBottomStyle;
import com.guideview.style.LeftTopStyle;
import com.guideview.style.RightBottomStyle;
import com.guideview.style.RightTopStyle;
import com.hanjinliang.androidstudy.R;

/**
 * 引导View库
 */
public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        View deco_view1 = LayoutInflater.from(this).inflate(R.layout.view_guide, (ViewGroup) getWindow().getDecorView(), false);

        new GuideViewHelper(GuideActivity.this)
                .addView(R.id.view3, new CenterTopStyle(deco_view1))
//                .addView(tv_light2, new CenterRightStyle(deco_view2))
//                .addView(tv_light3, new LeftBottomStyle(deco_view3, 10))
//                .addView(iv_light4, new CenterTopStyle(deco_view4, 10))
                .type(LightType.Rectangle)
                .autoNext()
//                .alpha(0)
                .Blur()
                .onDismiss(new GuideView.OnDismissListener() {
                    @Override
                    public void dismiss() {
                    }
                })
                .postShow();
    }
}
