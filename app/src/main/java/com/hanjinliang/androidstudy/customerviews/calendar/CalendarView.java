package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历View
 */
public class CalendarView extends LinearLayout{
    LinearLayout mCalendarView;
    CalendarTitleView mCalendarTitleView;
    RecyclerView mRvCalendarDay;

    ArrayList<Date> mDates=new ArrayList<>();
    CalendarDayAdapter mCalendarDayAdapter;
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
        mCalendarTitleView= (CalendarTitleView) mCalendarView.findViewById(R.id.calendarTitleView);
        mCalendarTitleView.setOnCurMonthChangedListener(new CalendarTitleView.OnCurMonthChangedListener() {
            @Override
            public void onCurMonthChanged(Calendar calendar) {
                Log.e("calendar",new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                parseCalendar(calendar);
            }
        });
        mRvCalendarDay= (RecyclerView) mCalendarView.findViewById(R.id.rv_calendarDay);

        mRvCalendarDay.setLayoutManager(new GridLayoutManager(getContext(),7));//一行显示7个
        mCalendarDayAdapter=new CalendarDayAdapter(getContext(),mDates,mCalendarTitleView.getCurCalendar());
        mRvCalendarDay.setAdapter(mCalendarDayAdapter);

        parseCalendar(mCalendarTitleView.getCurCalendar());
    }

    /**
     * 分析当前月份
     * @param calendar
     */
    private void parseCalendar(Calendar calendar) {
        mDates.clear();
        Calendar tempCalendar= (Calendar) calendar.clone();
         //设置日历到当月1号
        tempCalendar.set(Calendar.DAY_OF_MONTH,1);
        //算当月1号之前有几天
        int preDayCount=tempCalendar.get(Calendar.DAY_OF_WEEK)-1;
        Log.e("preDayCount",preDayCount+"");
        //日历移动到当前页面第一天
        tempCalendar.add(Calendar.DAY_OF_MONTH,-preDayCount);

        int mTotalCount=7*6;//最多显示6行

        for(int i=0;i<mTotalCount;i++){
            mDates.add(tempCalendar.getTime());
            //往前移动一天
            tempCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        //更新需要渲染的月份
        //mCalendarDayAdapter.setCurCalendar(mCalendarTitleView.getCurCalendar());
        //更新视图
        mCalendarDayAdapter.notifyDataSetChanged();
    }


    public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayViewHolder>{
        Context context;
        ArrayList<Date> mDates;
        Calendar mCurCalendar;//当前渲染月份
        public CalendarDayAdapter(Context context, ArrayList<Date> mDates,Calendar calendar){
            this.context=context;
            this.mDates=mDates;
            mCurCalendar=calendar;
        }

        public void setCurCalendar(Calendar curCalendar) {
            mCurCalendar = curCalendar;
        }

        @Override
        public CalendarDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View dayView=LayoutInflater.from(context).inflate(R.layout.view_calendar_day_item,parent,false);
            return new CalendarDayViewHolder(dayView);
        }

        @Override
        public void onBindViewHolder(CalendarDayViewHolder holder, int position) {
            Date date=mDates.get(position);
            //显示几号
            holder.mTvDay.setText(String.valueOf(date.getDate()));

            //判断是否是当天
            Date curDate=new Date();
            if(date.getDate()==curDate.getDate()&&date.getMonth()==curDate.getMonth()&&date.getYear()==curDate.getYear()){
                //是当天
                holder.mTvDay.setTextColor(Color.RED);
            }else {
                holder.mTvDay.setTextColor(Color.BLACK);
            }
            //当前天数是渲染的月份的 正常显示  否则变灰色
            if(date.getMonth()==mCurCalendar.getTime().getMonth()){
                holder.mTvDay.setBackgroundColor(Color.WHITE);
            }else{
                holder.mTvDay.setBackgroundColor(Color.LTGRAY);
            }

        }

        @Override
        public int getItemCount() {
            return mDates==null?0:mDates.size();
        }
    }

    public class CalendarDayViewHolder extends RecyclerView.ViewHolder{
        TextView mTvDay;
        public CalendarDayViewHolder(View itemView) {
            super(itemView);
            mTvDay= (TextView) itemView.findViewById(R.id.calendar_day);
        }
    }

}
