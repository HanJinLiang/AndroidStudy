package com.hanjinliang.androidstudy.systemwidget;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.systemwidget.ConstraintLayout.ConstraintLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.Sensor.SensorTestActivity;
import com.hanjinliang.androidstudy.systemwidget.SlidingPaneLayout.SlidingPaneLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.AppBarLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.appbarlayout.ToolBarActivity;
import com.hanjinliang.androidstudy.systemwidget.coordinatorlayout.CoordinatorLayoutActivity;
import com.hanjinliang.androidstudy.systemwidget.eventpatch.EventPatchActivity;
import com.hanjinliang.androidstudy.systemwidget.eventpatch.EventPatchActivity2;
import com.hanjinliang.androidstudy.systemwidget.listselect.ListSelectActivity;
import com.hanjinliang.androidstudy.systemwidget.netease.NeteaseSongListActivity;
import com.hanjinliang.androidstudy.systemwidget.recyclerView.DragRecyclerViewActivity;
import com.hanjinliang.androidstudy.systemwidget.recyclerView.GroupRecyclerViewActivity;
import com.hanjinliang.androidstudy.systemwidget.recyclerView.screenshot.RecyclerScreenShotActivity;
import com.hanjinliang.androidstudy.systemwidget.viewpager.ViewPagerStudyActivity;

/**
 * 系统组件
 */
public class SystemWidgetStudyActivity extends ListActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            String[] pArray={" CoordinatorLayout学习","Toolbar学习","AppBarLayout学习","SlidingPaneLayout学习","重力感应器","ConstraintLayoutActivity"
            ,"滑动事件冲突","滑动事件冲突2 同方向","ViewPager学习","列表单选和多选","拖动recyclerView","分组recyclerView","recyclerView截图","网易云歌单页效果"};
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
        }

        @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            switch (position){
                case 0://
                    start(CoordinatorLayoutActivity.class);
                    break;
                case 1://
                    start(ToolBarActivity.class);
                    break;
                case 2://
                    start(AppBarLayoutActivity.class);
                    break;
                case 3://
                    start(SlidingPaneLayoutActivity.class);
                    break;
                case 4://
                    start(SensorTestActivity.class);
                    break;
                case 5://
                    start(ConstraintLayoutActivity.class);
                    break;
                case 6://
                    start(EventPatchActivity.class);
                    break;
                case 7://
                    start(EventPatchActivity2.class);
                    break;
                case 8://
                    start(ViewPagerStudyActivity.class);
                    break;
                case 9://
                    start(ListSelectActivity.class);
                    break;
                case 10://
                    start(DragRecyclerViewActivity.class);
                    break;
                case 11://
                    start(GroupRecyclerViewActivity.class);
                    break;
                case 12://
                    start(RecyclerScreenShotActivity.class);
                    break;
                case 13://
                    start(NeteaseSongListActivity.class);
                    break;
            }
        }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
