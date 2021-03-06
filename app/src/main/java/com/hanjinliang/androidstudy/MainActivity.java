package com.hanjinliang.androidstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.MapUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hanjinliang.androidstudy.customerviews.CustomerViewStudyActivity;
import com.hanjinliang.androidstudy.hotfix.TestUtils;
import com.hanjinliang.androidstudy.javabase.JavaBaseStudyActivity;
import com.hanjinliang.androidstudy.systemwidget.SystemWidgetStudyActivity;
import com.hanjinliang.androidstudy.thirdLibs.ThirdLibActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestUtils.test();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // BarUtils.setStatusBarColor4Drawer( drawer, drawer,Color.parseColor("#4CAF50"));
        //BarUtils.setTranslucentForDrawerLayout(this,drawer);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banner1);

        int allocationByteCount = bitmap.getAllocationByteCount();

        LogUtils.e("allocationByteCount=="+allocationByteCount );

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig=Bitmap.Config.RGB_565;//一个像素占用两个字节  默认是888 占用4个字节
        options.inSampleSize=2;//采集压缩  宽高都每隔inSampleSize 采集一次
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.banner1,options);

        int allocationByteCount2 = bitmap2.getAllocationByteCount();

        LogUtils.e("allocationByteCount2=="+allocationByteCount2 );

        new Thread(new Runnable() {
            @Override
            public void run() {
                new DownloadTask().execute(10);
            }
        },"SubThread").start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // 自定义View学习
            startActivity(new Intent(this, CustomerViewStudyActivity.class));
        } else if (id == R.id.nav_gallery) {
            //系统View的学习
            startActivity(new Intent(this, SystemWidgetStudyActivity.class));
        } else if (id == R.id.nav_slideshow) {
            //第三方框架的学习
            startActivity(new Intent(this, ThirdLibActivity.class));
        } else if (id == R.id.nav_manage) {
            //java基础学习
            startActivity(new Intent(this, JavaBaseStudyActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public  class DownloadTask extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i=0;i<integers[0];i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return "doInBackground result";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println(Thread.currentThread().getName()+"onPreExecute=");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //更新UI publishProgress方法触发
            System.out.println(Thread.currentThread().getName()+"onProgressUpdate="+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground方法执行结束之后
            System.out.println(Thread.currentThread().getName()+"onPostExecute="+s);
        }
    }
}
