package com.hanjinliang.androidstudy.customerviews.LineView.simple;

/**
 * Created by HanJinLiang on 2018-08-07.
 */

public class SimpleLineData {
    private String xValueText;//X轴
    private float yValue;//Y轴
    private boolean isDrawValue;//是否画值

    public SimpleLineData(String xValueText, float yValue, boolean isDrawValue) {
        this.xValueText = xValueText;
        this.yValue = yValue;
        this.isDrawValue = isDrawValue;
    }

    public String getxValueText() {
        return xValueText;
    }

    public void setxValueText(String xValueText) {
        this.xValueText = xValueText;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    public boolean isDrawValue() {
        return isDrawValue;
    }

    public void setDrawValue(boolean drawValue) {
        isDrawValue = drawValue;
    }
}
