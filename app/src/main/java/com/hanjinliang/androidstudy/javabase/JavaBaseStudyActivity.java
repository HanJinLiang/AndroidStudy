package com.hanjinliang.androidstudy.javabase;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.customerviews.basestudy.Interpolator.InterpolatorActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.ViewDragHelper.ViewDragHelperActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.bezier.BezierHeartActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.canvas.CanvasRotateViewActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.canvas.CanvasScaleViewActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.eventdispatch.EventDispatchTestActivity;
import com.hanjinliang.androidstudy.javabase.fanxing.FanxingActivity;

/**
 * java基础知识学习
 */
public class JavaBaseStudyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"泛型学习" };
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://泛型学习
                start(FanxingActivity.class);
                break;

        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
