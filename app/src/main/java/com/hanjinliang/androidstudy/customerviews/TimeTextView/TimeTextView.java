package com.hanjinliang.androidstudy.customerviews.TimeTextView;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by HanJinLiang on 2018-02-28.
 */
public class TimeTextView extends AppCompatTextView {
    public TimeTextView(Context context) {
        this(context,null);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    setText(getTimeWaitting(mStartTime));
                    mHandler.sendEmptyMessageDelayed(0,1000);
                    break;
            }
        }
    };
    private String mStartTime;
    public void startCount(String startTime){
        mStartTime=startTime;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(0);
    }

    /**
     * 计算时间间隔
     * @param startTime
     * @return
     */
    public static String getTimeWaitting(String startTime){
        if(startTime.isEmpty()){
            return "";
        }
        if(startTime.equals("0")){
            return "0";
        }
        long startTimeSec=toLong(startTime)/1000;
        long currentTimeSec=System.currentTimeMillis()/1000;
        long waittingSec=currentTimeSec-startTimeSec;
        if(waittingSec<0){
            return "00:00:00";
        }else if(waittingSec>=0&&waittingSec<3600*24){//小于24小时
            return (waittingSec/3600<10?("0"+waittingSec/3600):(""+waittingSec/3600))+":"+
                    ((waittingSec%3600)/60<10?("0"+(waittingSec%3600)/60):(""+(waittingSec%3600)/60))+":"+
                    ((waittingSec%3600)%60<10?("0"+(waittingSec%3600)%60):(""+(waittingSec%3600)%60)) ;
        }else if (waittingSec>=3600*24){
            return waittingSec/(3600*24)+"天"+((waittingSec%(3600*24))/3600)+"小时";
        }
        return "";
    }

    public static long toLong(String obj) {
        if(obj==null||obj.equals("")){
            return 0;
        }
        long l=0;
        try {
            l=Long.parseLong(obj);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return l;
    }
}
