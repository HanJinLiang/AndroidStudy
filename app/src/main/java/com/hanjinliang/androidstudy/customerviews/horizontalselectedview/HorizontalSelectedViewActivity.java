package com.hanjinliang.androidstudy.customerviews.horizontalselectedview;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * 横向选择view
 */
public class HorizontalSelectedViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_selected_view);
        initView();
    }

    HorizontalSelectedView mHorizontalSelectedView;
    private void initView() {
        mHorizontalSelectedView=findView(R.id.HorizontalSelectedView);
        ArrayList<String> datas=new ArrayList<>();
        for(int i=0;i<20;i++){
            datas.add("test"+i*10);
        }
        mHorizontalSelectedView.setDatas(datas);
        mHorizontalSelectedView.setSelectedIndex(20);
        mHorizontalSelectedView.setOnSelectIndexChangedListener(new HorizontalSelectedView.OnSelectIndexChangedListener() {
            @Override
            public void onSelectIndexChanged(int selectedIndex) {
                ToastUtils.showShort("selectedIndex=="+selectedIndex);
            }
        });
    }

    public void pre(View view){
        mHorizontalSelectedView.setSelectedIndex(mHorizontalSelectedView.getSelectedIndex()-1);
    }

    public void next(View view){
        mHorizontalSelectedView.setSelectedIndex(mHorizontalSelectedView.getSelectedIndex()+1);
    }
}
