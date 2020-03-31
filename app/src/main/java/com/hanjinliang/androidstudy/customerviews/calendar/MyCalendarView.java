package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历View
 */
public class MyCalendarView extends LinearLayout{
    ConstraintLayout mCalendarView;
    MyCalendarTitleView mCalendarTitleView;
    RecyclerView mRvCalendarDay;
    LinearLayoutManager mLinearLayoutManager;
    TextView returnToday;
    ArrayList<MyCalendarViewBean> mDates=new ArrayList<>();
    MyCalendarDayAdapter mCalendarDayAdapter;
    public MyCalendarView(Context context) {
        this(context,null);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        mCalendarView= (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_my_calendar_view,null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dip2px(10),dip2px(10),dip2px(10),dip2px(10));
        mCalendarView.setLayoutParams(params);
        addView(mCalendarView);
        mCalendarTitleView=   mCalendarView.findViewById(R.id.calendarTitleView);
        mCalendarTitleView.setOnCurMonthChangedListener(new MyCalendarTitleView.OnCurMonthChangedListener() {
            @Override
            public void onCurMonthChanged(Calendar calendar) {
                Log.e("calendar",new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                parseCalendar(calendar);
            }
        });
        mRvCalendarDay= mCalendarView.findViewById(R.id.rv_calendarDay);
        mLinearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRvCalendarDay.setLayoutManager(mLinearLayoutManager);//横向滑动
        mCalendarDayAdapter=new MyCalendarDayAdapter(getContext(),mDates);
        mRvCalendarDay.setAdapter(mCalendarDayAdapter);

        parseCalendar(mCalendarTitleView.getCurCalendar());

        returnToday=mCalendarView.findViewById(R.id.returnToday);
        returnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回今日
                returnToday();
            }
        });
    }

    /**
     * 返回今日
     */
    private void returnToday() {
        if(mTodayIndex==-1) {
            mCalendarTitleView.resetCurCalendar();
            parseCalendar(mCalendarTitleView.getCurCalendar());
        }else{
            //滚动到固定位置
            TopSmoothScroller topSmoothScroller=new TopSmoothScroller(getContext());
            topSmoothScroller.setTargetPosition(mTodayIndex>2?mTodayIndex-2:mTodayIndex);
            mLinearLayoutManager.startSmoothScroll(topSmoothScroller);
        }
    }
    private int mTodayIndex;
    /**
     * 分析当前月份
     * @param calendar
     */
    private void parseCalendar(Calendar calendar) {
        mTodayIndex=-1;
        mDates.clear();
        Calendar tempCalendar= (Calendar) calendar.clone();
         //设置日历到当月1号
        tempCalendar.set(Calendar.DAY_OF_MONTH,1);

        Calendar c = Calendar.getInstance();
        c.set(tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH)+1, 0); //输入类型为int类型
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        Date curDate=new Date();

        for(int i=0;i<dayOfMonth;i++){
            if(tempCalendar.getTime().getDate()==curDate.getDate()&&tempCalendar.getTime().getMonth()==curDate.getMonth()&&tempCalendar.getTime().getYear()==curDate.getYear()){
                //是当天
                mTodayIndex=i;
            }
            MyCalendarViewBean calendarViewBean=new MyCalendarViewBean();
            calendarViewBean.setDate(tempCalendar.getTime());
            mDates.add(calendarViewBean);
            //往前移动一天
            tempCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        //更新视图
        mCalendarDayAdapter.notifyDataSetChanged();

        //滚动到固定位置
        TopSmoothScroller topSmoothScroller=new TopSmoothScroller(getContext());
        if(mTodayIndex==-1){
            topSmoothScroller.setTargetPosition(0);
        }else{
            topSmoothScroller.setTargetPosition(mTodayIndex>2?mTodayIndex-2:mTodayIndex);
        }
        mLinearLayoutManager.startSmoothScroll(topSmoothScroller);
    }

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public   int dip2px( float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * https://blog.csdn.net/weimingjue/article/details/82805361
     * Recycler滚动
     */
    public class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }
    }


}
