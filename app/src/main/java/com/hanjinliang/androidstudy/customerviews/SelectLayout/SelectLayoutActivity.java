package com.hanjinliang.androidstudy.customerviews.SelectLayout;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

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
//        layout_container.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                layout_container.setCurrentSelect(SelectLayout.CurrentSelect.left);
//            }
//        },1000);

    }



}
