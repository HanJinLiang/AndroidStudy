package com.hanjinliang.androidstudy.customerviews.timeselect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;


import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by caijun on 2018/1/26.
 * 功能介绍：日期选择 单个日期、单个时间、单个日期时间、双日期、双时间、双日期时间
 */

public class DateTimePickerTools {

    private Activity activity;

    private int picKerType;//1单个日期、2单个时间、3单个日期时间、4双日期、5双时间、6双日期时间
    private DatePicker datePicker;
    private DatePicker datePicker2;
    private TimePicker timePicker;
    private TimePicker timePicker2;

    private String singleDate;
    private String singleTime;
    private String singleDateTime;
    private String doubleDate;
    private String doubleTime;
    private String doubleDateTime;
    //弹窗选择
    private AlertDialog dialog;

    /**
     * 选择单个日期 格式：2018/01/01
     * @param activity
     * @param singleDate
     * @param singleDatePickerListener
     */
    public DateTimePickerTools(Activity activity, String singleDate, SingleDatePickerListener singleDatePickerListener) {
        this.activity = activity;
        this.singleDate = singleDate;
        this.singleDatePickerListener = singleDatePickerListener;
        picKerType=1;
        initView();
    }

    /**
     * 选择单个时间 格式：12:12
     * @param activity
     * @param singleTime
     * @param singleTimePickerListener
     */
    public DateTimePickerTools(Activity activity, String singleTime, SingleTimePickerListener singleTimePickerListener) {
        this.activity = activity;
        this.singleTime = singleTime;
        this.singleDatePickerListener = singleDatePickerListener;
        picKerType=2;
        initView();
    }

    /**
     * 单个日期时间选择 格式 2018/01/01 12:12
     * @param activity
     * @param singleDateTime
     * @param singleDateTimePickerListener
     */
    public DateTimePickerTools(Activity activity, String singleDateTime, SingleDateTimePickerListener singleDateTimePickerListener) {
        this.activity = activity;
        this.singleDateTime = singleDateTime;
        this.singleDateTimePickerListener = singleDateTimePickerListener;
        picKerType=3;
        initView();
    }
    /**
     * 两个日期选择 格式 2018/01/01-2019/01/01
     * @param activity
     * @param doubleDate
     * @param doubleDatePickerListener
     */
    public DateTimePickerTools(Activity activity, String doubleDate, DoubleDatePickerListener doubleDatePickerListener) {
        this.activity = activity;
        this.doubleDate = doubleDate;
        this.doubleDatePickerListener = doubleDatePickerListener;
        picKerType=4;
        initView();
    }

    /**
     * 选择两个时间
     * @param activity
     * @param doubleTime:格式为 12:00-13:00或者为null
     */
    public DateTimePickerTools(Activity activity, String doubleTime, DoubleTimePickerListener doubleTimePickerListener) {
        this.activity = activity;
        this.doubleTime = doubleTime;
        this.doubleTimePickerListener=doubleTimePickerListener;
        picKerType=5;
        initView();
    }

    /**
     * 选择两个日期时间
     * @param activity
     * @param doubleDateTime
     * @param doubleDateTimePickerListener
     */

    public DateTimePickerTools(Activity activity, String doubleDateTime, DoubleDateTimePickerListener doubleDateTimePickerListener) {
        this.activity = activity;
        this.doubleDateTime = doubleDateTime;
        this.doubleDateTimePickerListener = doubleDateTimePickerListener;
        picKerType=6;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.layout_date_time_picker, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        datePicker2 = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker2);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        timePicker2 = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker2);
        initDateTime();
        dialog=new AlertDialog.Builder(activity).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (picKerType){
                    case 1:
                        singleDatePickerListener.singleDatePicker(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                        break;
                    case 2:
                        singleTimePickerListener.singleTimePicker(timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                        break;
                    case 3:
                        singleDateTimePickerListener.singleDateTimePicker(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                        break;
                    case 4:
                        doubleDatePickerListener.doubleDatePicker(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),datePicker2.getYear(),datePicker2.getMonth(),datePicker2.getDayOfMonth());
                        break;
                    case 5:
                        doubleTimePickerListener.doubleTimePicker(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),timePicker2.getCurrentHour(),timePicker2.getCurrentMinute());
                        break;
                    case 6:
                        doubleDateTimePickerListener.doubleDateTimePicker(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute()
                                ,datePicker2.getYear(),datePicker2.getMonth(),datePicker2.getDayOfMonth(),timePicker2.getCurrentHour(),timePicker2.getCurrentMinute());
                        break;

                }
                dialog.dismiss();
            }
        }).create();
        dialog.setContentView(dateTimeLayout);
        Window dialogWindow = activity.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = activity.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
    /**
     * 初始化日期时间
     */
    private void initDateTime(){
        switch (picKerType){
            case 1:
                datePicker.setVisibility(View.VISIBLE);
                if(singleDate.isEmpty()){
                    initNullDatePicker(datePicker);
                }else{
                    initDatePicker(datePicker,singleDate);
                }
                break;
            case 2:
                timePicker.setVisibility(View.VISIBLE);
                if(singleTime.isEmpty()){
                    initNullTimePicker(timePicker);
                }else{
                    initTimePicker(timePicker,singleTime);
                }
                break;
            case 3:
                datePicker.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                if(singleDateTime.isEmpty()){
                    initNullDatePicker(datePicker);
                    initNullTimePicker(timePicker2);
                }else{
                    initDatePicker(datePicker,singleDateTime.split(" ")[0]);
                    initTimePicker(timePicker2,singleDateTime.split(" ")[1]);
                }
                break;
            case 4:
                datePicker.setVisibility(View.VISIBLE);
                datePicker2.setVisibility(View.VISIBLE);
                if(doubleDate.isEmpty()){
                    initNullDatePicker(datePicker);
                    initNullDatePicker(datePicker2);
                }else{
                    initDatePicker(datePicker,doubleDate.split("-")[0]);
                    initDatePicker(datePicker2,doubleDate.split("-")[1]);
                }
                break;
            case 5:
                timePicker.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker.setIs24HourView(true);
                timePicker2.setIs24HourView(true);
                if(doubleTime.isEmpty()){
                    initNullTimePicker(timePicker);
                    initNullTimePicker(timePicker2);
                }else{
                    initTimePicker(timePicker,doubleTime.split("-")[0]);
                    initTimePicker(timePicker2,doubleTime.split("-")[1]);
                }
                break;
            case 6:
                datePicker.setVisibility(View.VISIBLE);
                datePicker2.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker.setIs24HourView(true);
                timePicker2.setIs24HourView(true);
                resizePikcer(datePicker);
                resizePikcer(datePicker2);
                resizePikcer(timePicker);
                resizePikcer(timePicker2);
                if(doubleDateTime.isEmpty()){
                    initNullDatePicker(datePicker);
                    initNullDatePicker(datePicker2);
                    initNullTimePicker(timePicker);
                    initNullTimePicker(timePicker2);
                }else{
                    initDatePicker(datePicker,doubleDateTime.split("-")[0].split(" ")[0]);
                    initTimePicker(timePicker,doubleDateTime.split("-")[0].split(" ")[1]);

                    initDatePicker(datePicker2,doubleDateTime.split("-")[1].split(" ")[0]);
                    initTimePicker(timePicker2,doubleDateTime.split("-")[1].split(" ")[1]);

                }
                break;
        }
    }

    /**
     * 初始化null DatePicker
     */
    private void initNullDatePicker(DatePicker dp){
        Calendar calendar = Calendar.getInstance();
        dp.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),null);
    }
    /**
     * 初始化非空 DataPicker
     */
    private void initDatePicker(DatePicker dp,String date){
        String yearStr = date.split("/")[0]; // 年份
        String monthStr = date.split("/")[1]; // 月
        String dayStr = date.split("/")[2]; // 日
        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue()-1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
        dp.init(currentYear,currentMonth,currentDay,null);
    }
    /**
     * 初始化 null TimePicker
     *
     * */
    private void initNullTimePicker(TimePicker tp){
        Calendar calendar = Calendar.getInstance();
        tp.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        tp.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }
    /**
     * 初始化非空 TimePicker
     */
    private void initTimePicker(TimePicker tp,String time){
        tp.setCurrentHour(getHHMMInt(time.split(":")[0]));
        tp.setCurrentMinute(getHHMMInt(time.split(":")[1]));
    }

    /**
     * 获取时分Int
     */
    public static int getHHMMInt(String hhmm){
        if(hhmm.isEmpty()) return 0;
        else{
            if(hhmm.startsWith("0")){
                if(hhmm.equals("00")){
                    return 0;
                }else{
                    return Integer.parseInt(hhmm.replace("0",""));
                }
            }else{
                return Integer.parseInt(hhmm);
            }
        }
    }


    /**
     * 单个日期选择监听
     */
    private SingleDatePickerListener singleDatePickerListener;
    public interface SingleDatePickerListener{
        void singleDatePicker(int year, int month, int day);
    }
    /**
     * 单个时间选择监听
     */
    private SingleTimePickerListener singleTimePickerListener;
    public interface SingleTimePickerListener{
        void singleTimePicker(int hour, int min);
    }
    /**
     * 单个日期时间选择监听
     */
    private SingleDateTimePickerListener singleDateTimePickerListener;

    public interface SingleDateTimePickerListener{
        void singleDateTimePicker(int year, int month, int day, int hour, int min);
    }
    /**
     * 两个日期选择监听
     */
    private DoubleDatePickerListener doubleDatePickerListener;

    public interface DoubleDatePickerListener{
        void doubleDatePicker(int fyear, int fmonth, int fday, int syear, int smonth, int sfday);
    }
    /**
     * 两个时间选择监听
     */
    private DoubleTimePickerListener doubleTimePickerListener;

    public interface DoubleTimePickerListener{
        void doubleTimePicker(int firstHour, int firstMin, int secondHour, int secondMin);
    }
    /**
     * 两个日期时间选择监听
     */
    private DoubleDateTimePickerListener doubleDateTimePickerListener;

    public interface DoubleDateTimePickerListener{
        void doubleDateTimePicker(int fyear, int fmonth, int fday, int firstHour, int firstMin, int syear, int smonth, int sfday, int secondHour, int secondMin);
    }

    /**
     * 如果同时显示两个日期时间选择
     * 需要调整间距
     */
    /**
     * 调整FrameLayout大小
     * @param tp
     */
    private void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0; i < viewGroup.getChildCount(); i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /**
     * 调整numberpicker大小
     */
    private void resizeNumberPicker(NumberPicker np){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        np.setLayoutParams(params);
    }
}
