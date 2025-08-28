package com.hanjinliang.androidstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hanjinliang.androidstudy.customerviews.CustomerViewStudyActivity;
import com.hanjinliang.androidstudy.hotfix.TestUtils;
import com.hanjinliang.androidstudy.javabase.JavaBaseStudyActivity;
import com.hanjinliang.androidstudy.jjzg.JJZGMainActivity;
import com.hanjinliang.androidstudy.systemwidget.SystemWidgetStudyActivity;
import com.hanjinliang.androidstudy.thirdLibs.ThirdLibActivity;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestUtils.test();

        //测试merge
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

        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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

        } else if (id == R.id.nav_JJZG) {
            //进阶之光
            startActivity(new Intent(this, JJZGMainActivity.class));

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //内部静态类+WeakReference 解决Handler内存泄露
    public static class MyHandler extends Handler{
        WeakReference<MainActivity> activityWeakReference;
        public MyHandler(MainActivity activity){
            activityWeakReference=new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = activityWeakReference.get();
            if(mainActivity!=null){
//                mainActivity.onBackPressed();
            }
            LogUtils.e(Thread.currentThread().getName());
        }
    }
    public MyHandler handler=new MyHandler(this);
}
