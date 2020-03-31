package com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by HanJinLiang on 2017-07-11.
 *
 * 重写LinearLayout  实现 Checkable接口
 * ListView多选或者单选
 * http://blog.csdn.net/harvic880925/article/details/40475367
 * 注意点：
 * Item里面的CheckBox设置  foucsable  checkable 为false
 * 设置ListView的选择模式  多选还是单选
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable{
    public CheckableLinearLayout(Context context) {
        this(context,null);
    }

    public CheckableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckableLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean mChecked=false;

    @Override
    public void setChecked(boolean checked) {
        //给ListView设置choiceMode后  会自动调用此方法   只需要在这个方法里面做相应的逻辑处理即可
        if(mChecked!=checked){
            mChecked=checked;
            refreshDrawableState();
            for (int i = 0, len = getChildCount(); i < len; i++) {
                View child = getChildAt(i);
                if(child instanceof Checkable){
                    ((Checkable) child).setChecked(checked);
                }
            }
            if(checked){
                setBackgroundColor(Color.CYAN);
            }else{
                setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
