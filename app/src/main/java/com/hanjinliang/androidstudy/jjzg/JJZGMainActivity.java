package com.hanjinliang.androidstudy.jjzg;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.jjzg.part1.Part1MainActivity;

public class JJZGMainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"Part1_Android新特性","Part2_MaterialDesign","Part3_View自定义","Part4_多线程","Part5_网络编程","Part6_设计模式","Part7_时间总线"
        ,"Part8_RxJava基本用法","Part9_注解与依赖注入","Part10_应用架构设计","Part11_系统架构与MediaPlayer"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://Part1_Android新特性
                start(Part1MainActivity.class);
                break;
            case 1://Part2_MaterialDesign
                break;
            case 2://Part3_View自定义
                break;
            case 3://Part4_多线程
                break;
            case 4://Part5_网络编程
                break;
            case 5://Part6_设计模式
                break;
            case 6://Part7_时间总线
                break;
            case 7://Part8_RxJava基本用法
                break;
            case 8://Part9_注解与依赖注入
                break;
            case 9://Part10_应用架构设计
                break;
            case 10://Part11_系统架构与MediaPlayer
                break;

        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
