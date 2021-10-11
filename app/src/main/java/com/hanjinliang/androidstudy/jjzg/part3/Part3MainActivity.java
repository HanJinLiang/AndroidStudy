package com.hanjinliang.androidstudy.jjzg.part3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hanjinliang.androidstudy.jjzg.part1.NotificationStudyActivity;
import com.hanjinliang.androidstudy.jjzg.part1.PaletteStudyActivity;


public class Part3MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"滑动View-layout","自定义HorizontalView"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://滑动View-layout
                start(MoveViewActivity.class);
                break;
            case 1:
                start(HorizontalViewActivity.class);
                break;
        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
