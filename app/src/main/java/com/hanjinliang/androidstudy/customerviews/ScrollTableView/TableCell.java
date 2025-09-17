package com.hanjinliang.androidstudy.customerviews.ScrollTableView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class TableCell extends androidx.appcompat.widget.AppCompatTextView {
    private int width = -2;
    private boolean isHeader = false;
    public TableCell(@NonNull Context context, int width) {
        super(context);
        this.width = width;
        initView();
    }
    public TableCell(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        this.setBackgroundColor(Color.WHITE);
        ViewGroup.MarginLayoutParams params = new LinearLayout.LayoutParams(width-2, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,2,2);
        this.setLayoutParams(params);
        this.setTextSize(14);
        this.setGravity(Gravity.CENTER);
        this.setPadding(10,10,10,10);

    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
        this.setBackgroundColor(isHeader? 0x0ffF5FFFA:Color.WHITE);
        this.setPadding(10,isHeader? 15:10,10,isHeader? 15:10);
    }

    @Override
    public void setWidth(int pixels) {
        this.width = pixels;
        refreshWidth();
    }

    private void refreshWidth(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = width-2;
        params.setMargins(0,0,2,2);
        this.setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isHeader){
            //获取当前控件的画笔
            TextPaint paint = getPaint();
            //设置画笔的描边宽度值
            paint.setStrokeWidth(0.5f);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        super.onDraw(canvas);
    }

}
