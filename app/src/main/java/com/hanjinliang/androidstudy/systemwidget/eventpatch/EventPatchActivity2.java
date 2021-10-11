package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hanjinliang.androidstudy.R;

public class EventPatchActivity2 extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_patch2);
        listView=  findViewById(R.id.listView);
        String[] names={"张三","李四","王五","赵六","田七","张三","李四","王五","赵六","田七","张三","李四","王五","赵六","田七"};
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names));
    }


}
