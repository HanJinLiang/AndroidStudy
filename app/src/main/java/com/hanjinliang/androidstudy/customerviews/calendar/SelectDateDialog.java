package com.hanjinliang.androidstudy.customerviews.calendar;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hanjinliang.androidstudy.R;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by monkey on 2019-03-26 11:12.
 * Describe:日期选择对话框
 */

public class SelectDateDialog extends Dialog {
    private OnDateSetListener mOnDateSetListener;

    public SelectDateDialog(@NonNull Context context) {
        super(context);
        initView();
    }


    public SelectDateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected SelectDateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);
    }



    /**
     * 日期选择回调
     */
    public interface OnDateSetListener {
        void onDateSet(Date startDate,Date endDate);
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        mOnDateSetListener = onDateSetListener;
    }
}
