package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历头部布局
 */

public class CalendarTitleView extends LinearLayout  implements View.OnClickListener {
    LinearLayout mCalendarTitleView;
    TextView mTvPerMonth;//上个月
    TextView mTvCurMonth;//当前月
    TextView mTvNextMonth;//下个月

    Calendar mCurCalendar;

    private String mCurMonthFormat="yyyy-MM";
    public CalendarTitleView(Context context) {
        this(context,null);
    }

    public CalendarTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initView();
    }


    private void initData() {
        mCurCalendar=Calendar.getInstance();
    }

    private void initView() {
        mCalendarTitleView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_title,null);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip2px(50));
        mCalendarTitleView.setLayoutParams(params);
        addView(mCalendarTitleView);

        mTvPerMonth= (TextView) mCalendarTitleView.findViewById(R.id.calendar_title_pre_month);
        mTvCurMonth= (TextView) mCalendarTitleView.findViewById(R.id.calendar_title_current_month);
        mTvNextMonth= (TextView) mCalendarTitleView.findViewById(R.id.calendar_title_next_month);

        updateCurMonth();

        mTvPerMonth.setOnClickListener(this);
        mTvNextMonth.setOnClickListener(this);
    }

    /**
     * 显示当前月时间
     */
    public void updateCurMonth() {
        SimpleDateFormat format=new SimpleDateFormat(mCurMonthFormat);
        mTvCurMonth.setText(format.format(mCurCalendar.getTime()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calendar_title_pre_month://上个月
                monthChange(-1);
                break;
            case R.id.calendar_title_next_month://下个月
                monthChange(1);
                break;
        }
    }

    /**
     * 月份变化 -1表示减小  +1 表示增大
     * @param monthChange
     */
    private void monthChange(int monthChange) {
        mCurCalendar.add(Calendar.MONTH,monthChange);
        updateCurMonth();
        if(mOnCurMonthChangedListener!=null){
            mOnCurMonthChangedListener.onCurMonthChanged(mCurCalendar);
        }
    }

    private OnCurMonthChangedListener mOnCurMonthChangedListener;
    public void setOnCurMonthChangedListener(OnCurMonthChangedListener onCurMonthChangedListener) {
        mOnCurMonthChangedListener = onCurMonthChangedListener;
    }

    public interface OnCurMonthChangedListener{
        void onCurMonthChanged(Calendar calendar);
    }

    public Calendar getCurCalendar() {
        return mCurCalendar;
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
}
