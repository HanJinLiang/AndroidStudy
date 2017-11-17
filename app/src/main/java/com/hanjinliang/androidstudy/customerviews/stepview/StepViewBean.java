package com.hanjinliang.androidstudy.customerviews.stepview;

import android.graphics.drawable.Drawable;

/**
 * Created by HanJinLiang on 2017-11-17.
 */

public class StepViewBean {
    private String describe;//描述
    private Drawable mDrawable;//图片
    private boolean isFinished;//是否完成

    public StepViewBean(String describe, Drawable drawable, boolean isFinished) {
        this.describe = describe;
        mDrawable = drawable;
        this.isFinished = isFinished;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
