package com.hanjinliang.androidstudy.jjzg.part1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class Part1MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] pArray={"3种Notification","Palette的应用"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pArray));//绑定数据源
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0://3种Notification
                start(NotificationStudyActivity.class);
                break;
            case 1://Palette的应用
                start(PaletteStudyActivity.class);
                break;
        }
    }

    public void start(Class<?> cls){
        startActivity(new Intent(this,cls));
    }
}
