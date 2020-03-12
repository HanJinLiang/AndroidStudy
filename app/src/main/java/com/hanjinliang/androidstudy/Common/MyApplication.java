package com.hanjinliang.androidstudy.Common;

import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.hotfix.HotFixUtils;
import com.hanjinliang.androidstudy.hotfix.TestUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

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
        try {
            HotFixUtils.hotfix(this,new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch.dex"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
