package com.hanjinliang.androidstudy.systemwidget.viewpager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by HanJinLiang on 2017-12-21.
 * 测试用Fragment
 */

public class TestFragment extends Fragment {
    private static TestFragment mInstance;
    public static TestFragment  newInstance(String content){
//        if(mInstance==null){
//            mInstance=new TestFragment();
//        }
        mInstance=new TestFragment();
        Bundle bundle=new Bundle();
        bundle.putString("content",content);
        mInstance.setArguments(bundle);
        return mInstance;
    }


    String content;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content=getArguments().getString("content");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(getContext());
        textView.setLayoutParams(new ViewPager.LayoutParams( ));
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);
        return textView;
    }

}
