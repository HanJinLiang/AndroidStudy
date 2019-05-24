package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by monkey on 2019-05-15 11:42.
 * Describe:
 */

public class MySection extends SectionEntity<String> {

    public String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }



    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }
    public MySection(boolean isHeader, String header,String item) {
        super(isHeader, header);
        setItem(item);
    }

}
