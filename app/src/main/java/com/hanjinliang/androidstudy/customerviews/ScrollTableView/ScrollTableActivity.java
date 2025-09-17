package com.hanjinliang.androidstudy.customerviews.ScrollTableView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanjinliang.androidstudy.Common.BaseActivity;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.SearchView.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScrollTableActivity extends BaseActivity {
    ScrollTableView scrollTableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_table);
        scrollTableView=findView(R.id.scrollTableView);

        scrollTableView.setHeaderData(Arrays.asList("表头1","表头2","表头3","表头4","表头5","表头6","表头7","表头8","表头9"));
        List<List<String>> rowDataList = new ArrayList<>();
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        rowDataList.add(Arrays.asList("数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9"));
        scrollTableView.setRowData(rowDataList);
    }
}
