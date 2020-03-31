package com.hanjinliang.androidstudy.thirdLibs.rxjava;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

/**
 * Created by HanJinLiang on 2018-06-13.
 */

public class AppInfo implements Comparable<Object>{

    private Drawable image;
    private String appName;

    public AppInfo(Drawable image, String appName) {
        this.image = image;
        this.appName = appName;
    }
    public AppInfo() {

    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    @Override
    public int compareTo(@NonNull Object o) {
        AppInfo f= (AppInfo) o;
        return getAppName().compareTo(f.getAppName());
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "image=" + image +
                ", appName='" + appName + '\'' +
                '}';
    }
}
