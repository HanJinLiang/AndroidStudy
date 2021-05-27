package com.hanjinliang.androidstudy.Common;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;


import androidx.multidex.MultiDexApplication;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by monkey on 2019-05-23 11:24.
 * Describe:
 */

public class MyApplication extends MultiDexApplication {
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
