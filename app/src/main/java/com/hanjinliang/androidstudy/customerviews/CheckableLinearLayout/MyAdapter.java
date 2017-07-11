package com.hanjinliang.androidstudy.customerviews.CheckableLinearLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

/**
 * Created by HanJinLiang on 2017-07-11.
 */

public class MyAdapter extends BaseAdapter {
    ArrayList<String> mData;
    LayoutInflater mLayoutInflater;
    public  MyAdapter(Context context,ArrayList<String> data){
        mData=data;
        mLayoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mLayoutInflater.inflate(R.layout.item_activity_checkablelayout,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.getTextView().setText(mData.get(position));
        return convertView;
    }

    public class ViewHolder{
        View mView;
        TextView mTextView;
        public ViewHolder(View view){
            mView=view;
        }
        public TextView getTextView() {
            if(mTextView==null){
                mTextView= (TextView) mView.findViewById(R.id.textview);
            }
            return mTextView;
        }


    }
}
