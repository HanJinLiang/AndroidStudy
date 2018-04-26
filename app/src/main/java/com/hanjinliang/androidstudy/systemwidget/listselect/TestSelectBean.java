package com.hanjinliang.androidstudy.systemwidget.listselect;

/**
 * Created by HanJinLiang on 2018-04-26.
 */

public class TestSelectBean extends BaseSelectBean {

    public TestSelectBean(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
