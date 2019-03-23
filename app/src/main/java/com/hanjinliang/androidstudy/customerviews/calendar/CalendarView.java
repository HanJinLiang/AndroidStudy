package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历View
 */
public class CalendarView extends LinearLayout{
    LinearLayout mCalendarView;
    CalendarTitleView mCalendarTitleView;
    ViewPager mViewPager;


    public CalendarView(Context context) {
        this(context,null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        mCalendarView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_view,null);
        mCalendarView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mCalendarView);
        mCalendarTitleView=  mCalendarView.findViewById(R.id.calendarTitleView);
        mCalendarTitleView.setOnCurMonthChangedListener(new CalendarTitleView.OnCurMonthChangedListener() {
            @Override
            public void onCurMonthChanged(int offset) {
               mViewPager.setCurrentItem(mViewPager.getCurrentItem()+offset,true);
            }
        });
        mViewPager=mCalendarView.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 20000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getItemPosition(Object object) {
                // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
                return POSITION_NONE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                RecyclerView recyclerView = initMonthView(position);
                container.addView(recyclerView);
                return recyclerView;
            }
        });
        mViewPager.setCurrentItem(10000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCalendarTitleView.updateCurMonth(mCalendarHashMap.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
     HashMap<Integer,Calendar> mCalendarHashMap=new HashMap<>();
     private RecyclerView initMonthView(int position){
         CalendarMonthView calendarMonthView=new CalendarMonthView(getContext());
         Calendar tempCalendar;
         if(mCalendarHashMap.containsKey(position)){
             tempCalendar=mCalendarHashMap.get(position);
         }else {
             tempCalendar= Calendar.getInstance();
             tempCalendar.add(Calendar.MONTH, position - 10000);
             mCalendarHashMap.put(position,tempCalendar);
         }
         calendarMonthView.initData(tempCalendar, new OnDayClickListener() {
             @Override
             public void onItemDayClickListener() {
                 mViewPager.getAdapter().notifyDataSetChanged();
             }
         });
         return  calendarMonthView;
     }


    /**
     * 点击回调
     */
    public interface OnDayClickListener {
        void onItemDayClickListener( );
    }
}
