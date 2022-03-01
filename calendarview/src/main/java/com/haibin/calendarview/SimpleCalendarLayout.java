/*
 * Copyright (C) 2016 huanghaibin_dev <huanghaibin_dev@163.com>
 * WebSite https://github.com/MiracleTimes-Dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haibin.calendarview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;


/**
 * 自定义日历布局
 */
@SuppressWarnings("unused")
public class SimpleCalendarLayout extends LinearLayout {

    /**
     * 星期栏
     */
    WeekBar mWeekBar;

    /**
     * 自定义ViewPager，月视图
     */
    MonthViewPager mMonthView;

    /**
     * 日历
     */
    CalendarView mCalendarView;

    /**
     * 自定义的周视图
     */
    WeekViewPager mWeekPager;

    private int monthHeight;
    private int mSelectWeekOffsetY = 0;

    private float downY;
    private float mLastY;
    private float mLastX;
    private boolean isAnimating = false;


    private int mItemHeight;

    private CalendarViewDelegate mDelegate;

    public SimpleCalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * 初始化
     *
     * @param delegate delegate
     */
    final void setup(CalendarViewDelegate delegate) {
        Log.e("SimpleCalendarLayout","setup");

        this.mDelegate = delegate;
        mItemHeight = mDelegate.getCalendarItemHeight();
        initCalendarPosition(delegate.mSelectedCalendar.isAvailable() ?
                delegate.mSelectedCalendar :
                delegate.createCurrentDate());
        Calendar calendar = mDelegate.mIndexCalendar;

        monthHeight = CalendarUtil.getMonthViewHeight(calendar.getYear(), calendar.getMonth(),
                mDelegate.getCalendarItemHeight(),
                mDelegate.getWeekStart(),
                mDelegate.getMonthViewShowMode())
                + mDelegate.getWeekBarHeight();

    }

    /**
     * 初始化当前时间的位置
     *
     * @param cur 当前日期时间
     */
    private void initCalendarPosition(Calendar cur) {
        int diff = CalendarUtil.getMonthViewStartDiff(cur, mDelegate.getWeekStart());
        int size = diff + cur.getDay() - 1;
        updateSelectPosition(size);
    }

    /**
     * 当前第几项被选中，更新平移量
     *
     * @param selectPosition 月视图被点击的position
     */
    public void updateSelectPosition(int selectPosition) {
        int line = (selectPosition + 7) / 7;
        Log.e("SimpleCalendarLayout","selectPosition=="+selectPosition+"--line="+line);
        mSelectWeekOffsetY = (line - 1) * mItemHeight;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMonthView = findViewById(R.id.vp_month);
        mWeekPager = findViewById(R.id.vp_week);
        if (getChildCount() > 0) {
            mCalendarView = (CalendarView) getChildAt(0);
        }
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
                if(simpleCallback!=null){
                    simpleCallback.onCalendarOutOfRange(calendar);
                }
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                if(simpleCallback!=null){
                    simpleCallback.onCalendarSelect(calendar,isClick);
                }
                //如果点击的时候时候周视图 需要展开
//                if(mMonthView.getVisibility()!=VISIBLE){
//                    toggleStatus();
//                }

                Log.e("SimpleCalendarLayout","onCalendarSelect=="+calendar+"--isClick="+isClick);
                if(isClick){
                    initCalendarPosition(calendar);
                }
                monthHeight = CalendarUtil.getMonthViewHeight(calendar.getYear(), calendar.getMonth(),
                        mDelegate.getCalendarItemHeight(),
                        mDelegate.getWeekStart(),
                        mDelegate.getMonthViewShowMode())
                        + mDelegate.getWeekBarHeight();
            }
        });
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                if(simpleCallback!=null){
                    simpleCallback.onMonthChange(year,month);
                }
                Log.e("SimpleCalendarLayout","onMonthChange=year=="+year+"--month="+month);
                monthHeight = CalendarUtil.getMonthViewHeight(year,month,
                        mDelegate.getCalendarItemHeight(),
                        mDelegate.getWeekStart(),
                        mDelegate.getMonthViewShowMode())
                        + mDelegate.getWeekBarHeight();
                int currentHeight=getLayoutParams().height;
                if(monthHeight!=currentHeight && mMonthView.getVisibility()==VISIBLE){
                    //切换月份高度发生了变化 并且是月视图模式  动画切换
                    ValueAnimator outAnimator=ValueAnimator.ofInt(currentHeight,monthHeight);
                    outAnimator.setDuration(500);
                    outAnimator.addUpdateListener(new AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int height = (int) animation.getAnimatedValue();
                            setOutHeight(height);
                        }
                    });
                    outAnimator.start();
                }
            }
        });
        mCalendarView.setOnWeekChangeListener(new CalendarView.OnWeekChangeListener() {
            @Override
            public void onWeekChange(List<Calendar> weekCalendars) {
                if(simpleCallback!=null){
                    simpleCallback.onWeekChange(weekCalendars);
                }
                monthHeight = CalendarUtil.getMonthViewHeight(weekCalendars.get(0).getYear(),weekCalendars.get(0).getMonth(),
                        mDelegate.getCalendarItemHeight(),
                        mDelegate.getWeekStart(),
                        mDelegate.getMonthViewShowMode())
                        + mDelegate.getWeekBarHeight();
            }
        });

    }
    private SimpleCallback simpleCallback;//自定义的回调 不然会覆盖需要的事件

    public void setSimpleCallback(SimpleCallback simpleCallback){
        this.simpleCallback=simpleCallback;
    }
    public interface SimpleCallback{
        void onWeekChange(List<Calendar> weekCalendars);
        void onMonthChange(int year, int month);
        void onCalendarSelect(Calendar calendar, boolean isClick);
        void onCalendarOutOfRange(Calendar calendar);
    }

    /**
     * 展开
     *
     * @param duration 时长
     * @return 展开是否成功
     */
    public boolean expand(int duration) {
        if (isAnimating)
            return false;
        showMonth();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMonthView,
                "translationY", -mSelectWeekOffsetY, 0f);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
                hideWeek();
            }
        });
        isAnimating = true;
        objectAnimator.start();

        ValueAnimator outAnimator=ValueAnimator.ofInt(mItemHeight+mWeekBar.getHeight(),monthHeight);
        outAnimator.setDuration(duration);
        outAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                setOutHeight(height);
            }
        });
        outAnimator.start();
        return true;
    }
    public boolean expandByProgress(int progress,int duration) {
        if (isAnimating)
            return false;
        showMonth();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMonthView,
                "translationY", -mSelectWeekOffsetY, 0f);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
                hideWeek();
            }
        });
        isAnimating = true;
        objectAnimator.start();

        ValueAnimator outAnimator=ValueAnimator.ofInt(mItemHeight+mWeekBar.getHeight(),monthHeight);
        outAnimator.setDuration(duration);
        outAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                setOutHeight(height);
            }
        });
        outAnimator.start();
        return true;
    }


    /**
     * 收缩
     *
     * @param duration 时长
     * @return 成功或者失败
     */
    public boolean shrink(int duration) {
        if (isAnimating) {
            return false;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMonthView,
                "translationY", 0f,-mSelectWeekOffsetY);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
                showWeek();
            }
        });
        isAnimating = true;
        objectAnimator.start();

        ValueAnimator outAnimator=ValueAnimator.ofInt( mMonthView.getHeight()+mWeekBar.getHeight(),mItemHeight+mWeekBar.getHeight());
        outAnimator.setDuration(duration);
        outAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                setOutHeight(height);
            }
        });
        outAnimator.start();
        return true;
    }
    private void setOutHeight(int height){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height=height;
        setLayoutParams(params);
    }
    /**
     * 初始化状态
     */
    final void initStatus() {
        Log.e("SimpleCalendarLayout","initStatus");
        showWeek();
    }

    /**
     * 隐藏周视图
     */
    private void hideWeek() {
        mWeekPager.setVisibility(GONE);
        mMonthView.setVisibility(VISIBLE);
    }

    /**
     * 显示周视图
     */
    private void showWeek() {
        if (mWeekPager != null && mWeekPager.getAdapter() != null) {
            mWeekPager.getAdapter().notifyDataSetChanged();
            mWeekPager.setVisibility(VISIBLE);
        }
        mMonthView.setVisibility(GONE);
    }
    
    void showMonth(){
        if (mMonthView.getVisibility() != VISIBLE) {
            mWeekPager.setVisibility(GONE);
            mMonthView.setVisibility(VISIBLE);
        }
    }

    private int getCalendarViewHeight() {
        return mMonthView.getVisibility() == VISIBLE ? mDelegate.getWeekBarHeight() + mMonthView.getHeight() :
                mDelegate.getWeekBarHeight() + mDelegate.getCalendarItemHeight();
    }
    
    public void toggleStatus(){
        if(mMonthView.getVisibility() == VISIBLE ){
            shrink(500);
        }else {
            expand(500);
        }
    }

}
