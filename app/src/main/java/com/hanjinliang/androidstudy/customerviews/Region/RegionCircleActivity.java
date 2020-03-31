package com.hanjinliang.androidstudy.customerviews.Region;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

public class RegionCircleActivity extends BaseActivity {
    RemoteControlMenu mRemoteControlMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_circle);
        mRemoteControlMenu=findView(R.id.RemoteControlMenu);

        mRemoteControlMenu.setListener(new RemoteControlMenu.MenuListener() {
            @Override
            public void onCenterClicked() {
                ToastUtils.showShort("onCenterClicked");
            }

            @Override
            public void onUpClicked() {
                ToastUtils.showShort("onUpClicked");
            }

            @Override
            public void onRightClicked() {
                ToastUtils.showShort("onRightClicked");
            }

            @Override
            public void onDownClicked() {
                ToastUtils.showShort("onDownClicked");
            }

            @Override
            public void onLeftClicked() {
                ToastUtils.showShort("onLeftClicked");
            }
        });
    }
}
