package com.hanjinliang.androidstudy.customerviews.basestudy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.customerviews.basestudy.ViewDragHelper.ViewDragHelperActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.bezier.BezierHeartActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.canvas.CanvasRotateViewActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.canvas.CanvasScaleViewActivity;
import com.hanjinliang.androidstudy.customerviews.basestudy.eventdispatch.EventDispatchTestActivity;
import com.hanjinliang.androidstudy.thirdLibs.largeimage.BigImageTestActivity;

public class BaseStudyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"CanvasScale","CanvasRotate","Bezier贝塞尔曲线学习","事件纷发机制学习","ViewDragHelper"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://查看大图
                start(CanvasScaleViewActivity.class);
                break;
            case 1://查看大图
                start(CanvasRotateViewActivity.class);
                break;
            case 2://
                start(BezierHeartActivity.class);
                break;
            case 3://
                start(EventDispatchTestActivity.class);
                break;
            case 4:
                start(ViewDragHelperActivity.class);

        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
