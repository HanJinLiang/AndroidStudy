package com.hanjinliang.androidstudy.customerviews.ScrollTableView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {
    private final Context mContext;
    private List<String> mItemList;
    private int itemWidth = 360;
    private boolean isHeader = false;
    private int textColor = 0x0ff61686E;

    public TableAdapter(Context context) {
        this.mContext = context;
        this.mItemList = new ArrayList<>();
    }

    public void setItemWidth(int width) {
        this.itemWidth = width;
        this.notifyDataSetChanged();
    }

    public void setTextColor(int headerColor) {
        this.textColor = headerColor;
        this.notifyDataSetChanged();
    }

    public void setItemList(List<String> itemList) {
        this.mItemList = itemList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TableCell view = new TableCell(mContext,itemWidth);
        view.setHeader(isHeader);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String item = mItemList.get(position);
        TableCell tv = (TableCell)holder.itemView;
        tv.setText(item);
        tv.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void isHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
