package com.hanjinliang.androidstudy.Common;

import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.hotfix.HotFixUtils;

import java.io.File;

/**
 * Created by monkey on 2019-05-23 11:24.
 * Describe:
 */

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //Text 分支  测试2
        Utils.init(this);

        HotFixUtils.hotfix(this,new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch.dex"));
    }
}
