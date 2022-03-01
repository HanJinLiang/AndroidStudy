package com.hanjinliang.androidstudy.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;


import androidx.multidex.MultiDexApplication;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by monkey on 2019-05-23 11:24.
 * Describe:
 */

public class MyApplication extends MultiDexApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorAccent, android.R.color.white);//全局设置主题颜色
                // return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                return new MaterialHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   //
                   ToastUtils.showLong("handleMessage");
                   break;
           }
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //Text 分支  测试2
        Utils.init(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                MyHandler myHandler = new MyHandler();
                myHandler.sendEmptyMessage(1);
            }
        }).start();

        new MyAsyncTask().execute("1");
        //HotFixUtils.hotfix(this,new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch.dex"));
    }
     public static class MyAsyncTask extends AsyncTask<String,Integer,String>{



         @Override
         protected String doInBackground(String... strings) {
             int count=Integer.parseInt(strings[0]);
             try {
                 for(int i=0;i<10;i++){
                     Thread.sleep(1000);
                     count++;
                     publishProgress(count);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             return count+"";
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             LogUtils.e("onPreExecute");
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             ToastUtils.showLong("结束 结果--"+s);
         }

         @Override
         protected void onProgressUpdate(Integer... values) {
             super.onProgressUpdate(values);
             ToastUtils.showLong("onProgressUpdate--"+values[0]);
         }
     }
}
