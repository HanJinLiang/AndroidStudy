package com.hanjinliang.androidstudy.customerviews.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.customerviews.horizontalselectedview.HorizontalSelectedView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2018-03-18.
 * 自定义日历View
 */
public class CalendarView extends LinearLayout implements View.OnClickListener {

    private int mMonthPageCount=12*10;//

    ConstraintLayout mCalendarView;
    WrapContentHeightViewPager mViewPager;
    TextView preMonth;
    TextView nextMonth;

    HorizontalSelectedView mSelectedView;

    String[] monthArr=new String[]{"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
    //最大日期
    Calendar mMaxCalender=Calendar.getInstance();

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
        mCalendarView= (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_view,null);
        mCalendarView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mCalendarView);
        preMonth=mCalendarView.findViewById(R.id.preMonth);
        nextMonth=mCalendarView.findViewById(R.id.nextMonth);
        preMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);

        initTitleYearView();

        mViewPager=mCalendarView.findViewById(R.id.viewPager);
        mViewPager.setPageTransformer(false,new AlphaPageTransformer());
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mMonthPageCount;
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
                CalendarMonthView calendarMonthView = initMonthView(position);
                container.addView(calendarMonthView);
                return calendarMonthView;
            }
        });
         mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Calendar calendar = getCalendar(position);
                showTitleInfo(calendar);
                refreshYearView(calendar);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(mMonthPageCount-1);
    }

    /**
     * 刷新年份视图
     * @param calendar
     */
    private void refreshYearView(Calendar calendar) {
        int currentYear = calendar.get(Calendar.YEAR);
        for(int i=mAllYears.size()-1;i>=0;i--){
            if((currentYear+"").equals(mAllYears.get(i))){
                //找到当前显示的年
                mSelectedView.setSelectedIndex(i);
                return;
            }
        }
    }

    ArrayList<String> mAllYears=new ArrayList<>();
    /**
     * 初始化顶部的年份控件
     */
    private void initTitleYearView() {
        mSelectedView=mCalendarView.findViewById(R.id.horizontalSelectedView);
        mAllYears.clear();
        Calendar calendar = (Calendar) mMaxCalender.clone();
        for(int i=mMonthPageCount/12;i>0;i--){//遍历得到年数
            mAllYears.add(0,calendar.get(Calendar.YEAR)+"");
            calendar.add(Calendar.YEAR,-1);
        }
        mSelectedView.setDatas(mAllYears);

        mSelectedView.setOnSelectIndexChangedListener(new HorizontalSelectedView.OnSelectIndexChangedListener() {
            @Override
            public void onSelectIndexChanged(int selectedIndex) {
                LogUtils.e("selectedIndex--"+selectedIndex);
                //得到选择年份的差值
                int offsetYear = selectedIndex - mAllYears.size()+1;
                //设置ViewPager当前显示页数  最后一页index+offset年份*12
                mViewPager.setCurrentItem(mMonthPageCount-1+offsetYear*12,true);
            }
        });
    }

    /**
     * 显示标题信息
     * @param calendar
     */
    private void showTitleInfo(Calendar calendar){
        if(calendar==null)return;
        int monthIndex = calendar.get(Calendar.MONTH);
        if(monthIndex==0){
            preMonth.setText(monthArr[11]);
            nextMonth.setText(monthArr[monthIndex+1]);
        }else if(monthIndex==11){
            preMonth.setText(monthArr[monthIndex-1]);
            nextMonth.setText(monthArr[0]);
        }else{
            preMonth.setText(monthArr[monthIndex-1]);
            nextMonth.setText(monthArr[monthIndex+1]);
        }

        if( mViewPager.getCurrentItem()+1==mMonthPageCount){
            //是ViewPager的最后一页
            nextMonth.setVisibility(GONE);
        }else{
            nextMonth.setVisibility(VISIBLE);
        }
    }

    /**
     * 根据ViewPager位置获取到日历
     * @param position
     * @return
     */
    private Calendar getCalendar(int position){
        Calendar tempCalendar;
        if(mCalendarHashMap.containsKey(position)){
            tempCalendar=mCalendarHashMap.get(position);
        }else {
            tempCalendar= (Calendar) mMaxCalender.clone();
            tempCalendar.add(Calendar.MONTH, position -(mMonthPageCount-1));
            mCalendarHashMap.put(position,tempCalendar);
        }
        return tempCalendar;
    }

     HashMap<Integer,Calendar> mCalendarHashMap=new HashMap<>();
     private CalendarMonthView initMonthView(int position){
         CalendarMonthView calendarMonthView=new CalendarMonthView(getContext());
         calendarMonthView.initData(getCalendar(position), new OnDayClickListener() {
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
        void onItemDayClickListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.preMonth://上个月
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1,true);
                break;
            case R.id.nextMonth://下个月
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1,true);
                break;
        }
    }
    public Date mSelectStartDate,mSelectEndDate;

    public Date getSelectStartDate() {
        return mSelectStartDate;
    }

    public void setSelectStartDate(Date selectStartDate) {
        mSelectStartDate = selectStartDate;
    }

    public Date getSelectEndDate() {
        return mSelectEndDate;
    }

    public void setSelectEndDate(Date selectEndDate) {
        mSelectEndDate = selectEndDate;
    }

    /**
     * 刷新UI显示
     */
    public void refreshUI(){
        mViewPager.getAdapter().notifyDataSetChanged();
    }
}
