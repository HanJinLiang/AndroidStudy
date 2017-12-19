package com.hanjinliang.androidstudy.customerviews.PieChart;

/**
 * Created by HanJinLiang on 2017-12-19.
 */

public class PieBeans {
    private float percent;
    private String text;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PieBeans(float percent, String text, int color) {
        this.percent = percent;
        this.text = text;
        this.color = color;
    }
}
