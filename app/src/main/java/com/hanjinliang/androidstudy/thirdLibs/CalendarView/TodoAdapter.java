package com.hanjinliang.androidstudy.thirdLibs.CalendarView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.R;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/12/8 14:56
 */
public class TodoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TodoAdapter() {
        super(R.layout.item_app_list);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
    }
}
