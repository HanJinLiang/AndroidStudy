package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import androidx.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.R;

import java.util.List;

/**
 * Created by caijun on 2018/8/23.
 * 功能介绍：
 */

public class ImageViewAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public ImageViewAdapter(@Nullable List<String> data) {
        super(R.layout.item_image, data);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("点击图片-"+position);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.imageView);
    }
}
