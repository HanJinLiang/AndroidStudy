package com.hanjinliang.androidstudy.customerviews.TimeTextView;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;

/**
 * Created by HanJinLiang on 2018-02-28.
 */
public class TimeCountView extends AppCompatTextView {
    public TimeCountView(Context context) {
        this(context,null);
    }

    public TimeCountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(isRun) {
                        setText(getTimeStr(mCountTime--));
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };
    private long mCountTime;
    private boolean isRun;
    /**
     * 开始倒计时   单位秒
     * @param startTime  开始时间 单位秒
     * @param endTime    结束时间 单位秒
     */
    public void startCount(long startTime,long endTime){
        isRun=true;
        mCountTime=endTime-startTime;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);
    }

    /**
     * 结束倒计时
     */
    public void endCount(){
        isRun=false;
        mHandler.removeMessages(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //防止忘记调用endCount
        endCount();
    }

    /**
     * 得到字符串
     * @param countTime
     * @return
     */
    private String getTimeStr(long countTime){
        if(countTime<0){
            return "00:00:00";
        }else if(countTime>=0&&countTime<3600*24){//小于24小时
            return (countTime/3600<10?("0"+countTime/3600):(""+countTime/3600))+":"+
                    ((countTime%3600)/60<10?("0"+(countTime%3600)/60):(""+(countTime%3600)/60))+":"+
                    ((countTime%3600)%60<10?("0"+(countTime%3600)%60):(""+(countTime%3600)%60)) ;
        }else if (countTime>=3600*24){
            return countTime/(3600*24)+"天"+((countTime%(3600*24))/3600)+"小时";
        }
        return "";
    }

}
