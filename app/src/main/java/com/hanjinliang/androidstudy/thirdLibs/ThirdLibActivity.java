package com.hanjinliang.androidstudy.thirdLibs;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.thirdLibs.MPChart.MPChartDemoActivity;
import com.hanjinliang.androidstudy.thirdLibs.guide.GuideActivity;
import com.hanjinliang.androidstudy.thirdLibs.largeimage.BigImageTestActivity;
import com.hanjinliang.androidstudy.thirdLibs.rxjava.RxJavaDemoActivity;

public class ThirdLibActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"查看大图PhotoView","MPChart","RxJava","功能引导蒙版"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://查看大图
                start(BigImageTestActivity.class);
                break;
            case 1://MPChart
                start(MPChartDemoActivity.class);
                break;
            case 2://RxJava
                start(RxJavaDemoActivity.class);
                break;
            case 3://RxJava
                start(GuideActivity.class);
                break;
        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
