
package com.hanjinliang.androidstudy.jjzg.part3;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/9/2 11:31
 * 横向滚动的自定义View
 */
public class HorizontalViewActivity extends BaseActivity {
    private ListView listView00;
    private ListView listView01;
    private ListView listView02;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjzg_horizontal_view);
        initView();

    }

    private void initView() {
        listView00 = (ListView) findViewById(R.id.lv_00);
        listView01 = (ListView) findViewById(R.id.lv_01);
        listView02 = (ListView) findViewById(R.id.lv_02);

        String[] strs1 = {"1","2","4","5","6","1","2","4","5","6","1","2","4","5","6","6","1","2","4","5","6"};
        String[] strs2 = {"A","B","C","D","E","A","B","C","D","E","6","1","2","4","5","6","A","B","C","D","E"};
        String[] strs3 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o"};
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item ,strs1);
        listView00.setAdapter(adapter1);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item ,strs2);
        listView01.setAdapter(adapter2);

        ArrayAdapter adapter3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item ,strs3);
        listView02.setAdapter(adapter3);

    }



}
