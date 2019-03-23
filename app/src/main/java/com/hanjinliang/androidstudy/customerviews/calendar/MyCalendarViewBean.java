package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
