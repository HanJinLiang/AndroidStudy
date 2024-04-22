package com.hanjinliang.androidstudy.systemwidget.recyclerView.expand;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Random;

public class ExpandRecyclerViewActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ExpandableItem Activity");
        setContentView(R.layout.activity_drag_recycler_view);

        findViewById(R.id.rootLayout).setBackgroundColor(Color.WHITE);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setBackgroundColor(Color.LTGRAY);
        mRecyclerView.setPadding(SizeUtils.dp2px(10),0,SizeUtils.dp2px(10),0);
        list = generateData();
        adapter = new ExpandableItemAdapter(list);

        final LinearLayoutManager manager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        mRecyclerView.setLayoutManager(manager);
//        adapter.expandAll();
        adapter.expand(0);
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 2;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        res.add(new Person("顺顺", random.nextInt(40)));
        res.add(new Person("葫芦娃", random.nextInt(40)));
//        ArrayList<MultiItemEntity> res = new ArrayList<>();
//        for (int i = 0; i < lv0Count; i++) {
//            for (int j = 0; j < lv1Count; j++) {
//                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
//                for (int k = 0; k < personCount; k++) {
//                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
//                }
//                res.add(lv1);
//            }
//        }
        return res;
    }

}
