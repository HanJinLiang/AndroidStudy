package com.hanjinliang.androidstudy.customerviews.calendar;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历View
 */
public class MyCalendarViewBean implements Serializable{
   private Date date;

   private boolean isTag;

    public boolean isTag() {
        return isTag;
    }

    public void setTag(boolean tag) {
        isTag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
