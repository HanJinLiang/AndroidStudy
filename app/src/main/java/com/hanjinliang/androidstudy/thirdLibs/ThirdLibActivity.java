package com.hanjinliang.androidstudy.thirdLibs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.thirdLibs.CalendarView.CalendarViewActivity;
import com.hanjinliang.androidstudy.thirdLibs.MPChart.MPChartDemoActivity;
import com.hanjinliang.androidstudy.thirdLibs.aviPlay.AviPlayActivity;
import com.hanjinliang.androidstudy.thirdLibs.camera.CameraVideoMainActivity;
import com.hanjinliang.androidstudy.thirdLibs.flv.FlvPlayActivity;
import com.hanjinliang.androidstudy.thirdLibs.guide.GuideActivity;
import com.hanjinliang.androidstudy.thirdLibs.largeimage.BigImageTestActivity;
import com.hanjinliang.androidstudy.thirdLibs.rxjava.RxJavaDemoActivity;

public class ThirdLibActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"查看大图PhotoView","MPChart","RxJava","功能引导蒙版","录制短视频","FLV格式视频","avi格式视频","CalendarView"};
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
            case 4://录制短视频
                start(CameraVideoMainActivity.class);
                break;
            case 5:
                start(FlvPlayActivity.class);
                break;
            case 6:
                start(AviPlayActivity.class);
                break;
            case 7:
                start(CalendarViewActivity.class);
                break;
        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
