package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by monkey on 2019-03-23 16:52.
 * Describe:日历控件每一个月  是用的是RecyclerView
 */

public class CalendarMonthView extends LinearLayout {
    LinearLayout mCalendarMonthView;
    TextView mTvCurMonth;//当前月
    RecyclerView mMonthRecyclerView;
    public CalendarMonthView(Context context) {
        this(context,null);
    }

    public CalendarMonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarMonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void initView(){
        mCalendarMonthView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_month_view,null);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mCalendarMonthView.setLayoutParams(params);
        addView(mCalendarMonthView);

        mTvCurMonth= mCalendarMonthView.findViewById(R.id.calendar_title_current_month);

        mMonthRecyclerView= mCalendarMonthView.findViewById(R.id.monthRecyclerView);
        //设置LayoutManager
        mMonthRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));//一行显示7个
    }

    String[] monthArr=new String[]{"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};


    /**
     * 设置数据
     * @param calendar
     */
    public void initData(Calendar calendar,CalendarView.OnDayClickListener onDayClickListener){
        int monthIndex = calendar.get(Calendar.MONTH);
        mTvCurMonth.setText(monthArr[monthIndex]);
        CalendarDayAdapter calendarDayAdapter=new CalendarDayAdapter(getContext(),  parseCalendar(calendar),calendar);
        calendarDayAdapter.setOnDayClickListener(onDayClickListener);

        mMonthRecyclerView.setAdapter(calendarDayAdapter);
    }

    /**
     * 分析当前月份
     */
    private ArrayList<Date> parseCalendar(Calendar calendar) {
        ArrayList<Date> dates=new ArrayList<>();
        Calendar tempCalendar= (Calendar) calendar.clone();
        //设置日历到当月1号
        tempCalendar.set(Calendar.DAY_OF_MONTH,1);
        //算当月1号之前有几天
        int preDayCount=tempCalendar.get(Calendar.DAY_OF_WEEK)-2;//周一开始  -1就是周日开始本周
        //当本月1号为星期日时候 从周一到周日布局  需要往前多6天
        if(preDayCount==-1){
            preDayCount=6;
        }

        //一个月多少天
        Calendar c = Calendar.getInstance();
        c.set(tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH)+1, 0); //输入类型为int类型
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        Log.e("preDayCount",preDayCount+"--dayOfMonth="+dayOfMonth);

        //日历移动到当前页面第一天
        tempCalendar.add(Calendar.DAY_OF_MONTH,-preDayCount);


        for(int i=0;i<dayOfMonth+preDayCount;i++){
            dates.add(tempCalendar.getTime());
            //往前移动一天
            tempCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        //更新视图
        return dates;
    }
}
