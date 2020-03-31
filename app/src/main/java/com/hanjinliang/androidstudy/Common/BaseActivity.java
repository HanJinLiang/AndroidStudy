package com.hanjinliang.androidstudy.Common;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by HanJinLiang on 2017-06-26.
 */

public class BaseActivity extends AppCompatActivity {
    //模仿ViewHolder机制.保存View.避免多次去findviewbyid同一个view
    private SparseArray<View> mViews=new SparseArray<>();

    /**
     *
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V findView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = super.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (V) view;
    }

}
