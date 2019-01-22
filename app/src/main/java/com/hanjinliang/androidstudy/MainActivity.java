package com.hanjinliang.androidstudy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.customerviews.CustomerViewStudyActivity;
import com.hanjinliang.androidstudy.javabase.JavaBaseStudyActivity;
import com.hanjinliang.androidstudy.systemwidget.SystemWidgetStudyActivity;
import com.hanjinliang.androidstudy.thirdLibs.ThirdLibActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
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

        BarUtils.setColorForDrawerLayout(this,drawer, Color.parseColor("#4CAF50"));
        //BarUtils.setTranslucentForDrawerLayout(this,drawer);

        int nStraightHand = lengthOfLongestSubstring("au");
        LogUtils.e("nStraightHand","nStraightHand="+nStraightHand);
        //https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/submissions/
    }
    public int lengthOfLongestSubstring(String s) {
        if(s==null||s.length()==0){
            return 0;
        }
        int length=1;

        for(int i=0;i<s.length();i++){
            HashMap<Integer,Character> temp=new HashMap();
            for(int j=i;j<s.length();j++){

                Character value=s.charAt(j);
                if(temp.containsValue(value)){
                    length=Math.max(length,temp.size());
                    temp.clear();
                    break;
                }
                temp.put(j,value);
            }
        }
        return length;
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
}
