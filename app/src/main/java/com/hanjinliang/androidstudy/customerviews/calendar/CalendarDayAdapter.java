package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by monkey on 2019-03-23 16:54.
 * Describe:日历控件Adapter
 */

public class CalendarDayAdapter  extends RecyclerView.Adapter<CalendarDayAdapter.CalendarDayViewHolder>{
    public static Date mSelectStartDate,mSelectEndDate;
    Context context;
    ArrayList<Date> mDates;
    Calendar mCurCalendar;//当前渲染月份
    CalendarView.OnDayClickListener mOnDayClickListener;
    public CalendarDayAdapter(Context context, ArrayList<Date> mDates,Calendar calendar){
        this.context=context;
        this.mDates=mDates;
        mCurCalendar=calendar;
    }

    public void setOnDayClickListener(CalendarView.OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    @Override
    public CalendarDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View dayView= LayoutInflater.from(context).inflate(R.layout.view_calendar_day_item,parent,false);
        return new CalendarDayViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(CalendarDayViewHolder holder, int position) {
        Date date=mDates.get(position);
        //显示几号
        holder.mTvDay.setText(String.valueOf(date.getDate()));

        //判断是选中的
        if (mSelectStartDate!=null&&date.getDate() == mSelectStartDate.getDate() && date.getMonth() == mSelectStartDate.getMonth() && date.getYear() == mSelectStartDate.getYear()) {//开始
            holder.mTvDay.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_bg3));
        }else if(mSelectEndDate!=null&&date.getDate() == mSelectEndDate.getDate() && date.getMonth() == mSelectEndDate.getMonth() && date.getYear() == mSelectEndDate.getYear()){//结束
            holder.mTvDay.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_bg3));
        }else if(mSelectStartDate!=null&&mSelectEndDate!=null&&
                date.compareTo(mSelectStartDate)>0&& date.compareTo(mSelectEndDate)<0){//选择的区间里面
            holder.mTvDay.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_bg2));
        }else{//其他
            holder.mTvDay.setBackgroundDrawable(new ColorDrawable(0xffffff));
        }


        //判断是否是当天
        Date curDate=new Date();
        if(date.getDate()==curDate.getDate()&&date.getMonth()==curDate.getMonth()&&date.getYear()==curDate.getYear()){
            //是当天
            holder.mTvDay.setTextColor(Color.RED);
        }else {
            holder.mTvDay.setTextColor(Color.BLACK);
        }
        //当前天数是渲染的月份的 正常显示  否则变灰色
        LogUtils.e("date.getMonth() =="+date.getMonth()+"===mCurCalendar.getTime().getMonth()="+mCurCalendar.getTime().getMonth());
        if(date.getMonth()==mCurCalendar.getTime().getMonth()){
            holder.itemView.setVisibility(View.VISIBLE);
        }else{
            holder.itemView.setVisibility(View.GONE);
        }
        holder.itemView.setTag(date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date= (Date) v.getTag();
                if (mSelectStartDate == null) {//没有起始时间
                    mSelectStartDate = date;
                } else {
                    if(mSelectEndDate==null){//没有结束时间
                        if (date.compareTo(mSelectStartDate) == 0) {//点击是同一天
                            mSelectEndDate = null;//结束时间设置为空
                        } else if (date.compareTo(mSelectStartDate) > 0) {//点击开始时间之后的  设置为结束时间
                            mSelectEndDate = date;
                        } else if (date.compareTo(mSelectStartDate) < 0) {//点击开始时间之前的  之前开始时间设置为结束  点击的时间为开始
                            mSelectEndDate = mSelectStartDate;
                            mSelectStartDate = date;
                        }
                    }else{//开始时间  结束时间都有了  重新开始选
                        mSelectStartDate = date;
                        mSelectEndDate=null;
                    }
                }
                if (mOnDayClickListener != null) {
                    mOnDayClickListener.onItemDayClickListener();
                }
                Log.e("mSelectStartDate",mSelectStartDate==null?"null":new SimpleDateFormat("yyyy-MM-dd").format(mSelectStartDate));
                Log.e("mSelectEndDate",mSelectEndDate==null?"null":new SimpleDateFormat("yyyy-MM-dd").format(mSelectEndDate));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDates==null?0:mDates.size();
    }

public class CalendarDayViewHolder extends RecyclerView.ViewHolder{
    TextView mTvDay;
    public CalendarDayViewHolder(View itemView) {
        super(itemView);
        mTvDay= (TextView) itemView.findViewById(R.id.calendar_day);
    }
}
}
