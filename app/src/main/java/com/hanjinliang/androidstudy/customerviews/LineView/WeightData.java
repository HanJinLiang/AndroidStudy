package com.hanjinliang.androidstudy.customerviews.LineView;

/**
 * Created by HanJinLiang on 2017-07-21.
 */
public class WeightData {
    private String desc;
    private float value;

    public WeightData(String desc, float value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
