package com.hanjinliang.androidstudy.systemwidget;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

/**
 * 系统组件
 */
public class SystemWidgetStudyActivity extends ListActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            String[] pArray={" CoordinatorLayout学习","Toolbar学习","AppBarLayout学习","SlidingPaneLayout学习","重力感应器","ConstraintLayoutActivity"};
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
            }
        }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
