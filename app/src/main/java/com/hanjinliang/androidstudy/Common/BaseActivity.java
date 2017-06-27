package com.hanjinliang.androidstudy.Common;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

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
