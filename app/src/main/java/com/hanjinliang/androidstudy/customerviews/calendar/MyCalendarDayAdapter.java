package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hjl on 2019-03-19 13:19.
 * Describe:
 */
public class MyCalendarDayAdapter extends RecyclerView.Adapter<MyCalendarDayAdapter.CalendarDayViewHolder>{
    Context context;
    ArrayList<MyCalendarViewBean> mDates;
    public MyCalendarDayAdapter(Context context, ArrayList<MyCalendarViewBean> mDates){
        this.context=context;
        this.mDates=mDates;
    }

    @Override
    public CalendarDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dayView= LayoutInflater.from(context).inflate(R.layout.view_my_calendar_day_item,parent,false);
        return new CalendarDayViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(CalendarDayViewHolder holder, int position) {
        Date date=mDates.get(position).getDate();
        //显示几号
        holder.mTvDay.setText(String.valueOf(date.getDate()));

        //判断是否是当天
        Date curDate=new Date();
        if(date.getDate()==curDate.getDate()&&date.getMonth()==curDate.getMonth()&&date.getYear()==curDate.getYear()){
            //是当天
            holder.todayHint.setVisibility(View.VISIBLE);
            holder.mTvDay.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.my_calendar_today_bg));
        }else {
            holder.todayHint.setVisibility(View.INVISIBLE);
            holder.mTvDay.setBackgroundDrawable(new ColorDrawable(0xffffff));
        }

        //当天是否是有Tag事件标识
        if(mDates.get(position).isTag()){
            holder.tagHint.setVisibility(View.VISIBLE);
        }else{
            holder.tagHint.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDates==null?0:mDates.size();
    }


public class CalendarDayViewHolder extends RecyclerView.ViewHolder{
    TextView mTvDay;
    ImageView tagHint;
    TextView todayHint;
    public CalendarDayViewHolder(View itemView) {
        super(itemView);
        mTvDay=  itemView.findViewById(R.id.calendar_day);
        tagHint=  itemView.findViewById(R.id.tagHint);
        todayHint=  itemView.findViewById(R.id.todayHint);
    }
}
}