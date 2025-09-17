package com.hanjinliang.androidstudy.customerviews.ScrollTableView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class ScrollTableView extends LinearLayout {
    private final Context mContext;
    private RecyclerView rvHeader,rvFirstColumn,rvItems;
    private TableCell tvFirstHeader;
    private TableAdapter headerAdapter,fistColumnAdapter,itemAdapter;
    private List<String> headerList = new ArrayList<>();
    private final List<String> firstColumnList = new ArrayList<>();
    private final List<String> itemList = new ArrayList<>();
    private final int headerColor = 0x0ff262a2d;
    private int mdx = 0,mdy = 0;

    public ScrollTableView(Context context) {
        this(context, null, 0);
    }

    public ScrollTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        this.setOrientation(HORIZONTAL);
        this.addView(getRowHeader());
        this.addView(getFirstColumnAndItemLayout());

        headerAdapter = new TableAdapter(mContext);
        headerAdapter.setTextColor(headerColor);
        rvHeader.setAdapter(headerAdapter);
        tvFirstHeader.setHeader(true);
        headerAdapter.isHeader(true);

        fistColumnAdapter = new TableAdapter(mContext);
        fistColumnAdapter.setItemWidth(200);
        fistColumnAdapter.setTextColor(headerColor);
        rvFirstColumn.setAdapter(fistColumnAdapter);

        itemAdapter = new TableAdapter(mContext);
        rvItems.setAdapter(itemAdapter);


        rvFirstColumn.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mdx = dx;
                mdy = dy;
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rvItems.scrollBy(dx, dy);
                }
            }
        });
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mdx = dx;
                mdy = dy;
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rvFirstColumn.scrollBy(dx, dy);
                }
            }
        });
    }

    private LinearLayout getRowHeader(){
        tvFirstHeader = new TableCell(mContext,200);
        tvFirstHeader.setTextColor(headerColor);

        LinearLayout lyHeader = new LinearLayout(mContext);
        lyHeader.setOrientation(VERTICAL);

        lyHeader.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        lyHeader.addView(tvFirstHeader);
        rvFirstColumn = new RecyclerView(mContext);
        rvFirstColumn.setLayoutManager(new LinearLayoutManager(mContext));
        lyHeader.addView(rvFirstColumn);
        return lyHeader;
    }

    private HorizontalScrollView getFirstColumnAndItemLayout(){
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(VERTICAL);
        layout.setLayoutParams(new LayoutParams(-2, -1));

        rvHeader = new RecyclerView(mContext);
        rvHeader.setLayoutParams(new LayoutParams(-1, -2));
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvHeader.setLayoutManager(manager);
        layout.addView(rvHeader);

        rvItems = new RecyclerView(mContext);
        layout.addView(rvItems);

        HorizontalScrollView scrollView = new HorizontalScrollView(mContext);
        scrollView.setLayoutParams(new LayoutParams(-1, -1));
        scrollView.addView(layout);
        scrollView.setFillViewport(true);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);//取消滑到顶端的阴影
        scrollView.setHorizontalScrollBarEnabled(false);
        return scrollView;
    }

    public void setHeaderData(List<String> headerData){
        if(headerData.isEmpty())return;
        this.headerList = new ArrayList<>(headerData);
        List<String> headers = new ArrayList<>(headerData);
        tvFirstHeader.setText(headers.get(0));
        headers.remove(0);
        headerAdapter.setItemList(headers);
        rvItems.setLayoutManager(new GridLayoutManager(mContext,headerList.size()-1));
    }

    public void setRowData(List<List<String>> rowDataList){
        if(headerList.isEmpty() || rowDataList.isEmpty()) return;
        firstColumnList.clear();
        itemList.clear();
        addRowData(rowDataList);
    }

    public void addRowData(List<List<String>> rowDataList){
        List<List<String>> list = new ArrayList<>(rowDataList);
        for (List<String> rowData : list) {
            List<String> row = new ArrayList<>(rowData);
            if(row.size()>0){
                firstColumnList.add(row.get(0));
                row.remove(0);
                itemList.addAll(row);
            }
        }

        fistColumnAdapter.setItemList(firstColumnList);
        itemAdapter.setItemList(itemList);

        rvFirstColumn.scrollBy(mdx,mdy);
        rvItems.scrollBy(mdx,mdy);

        List<String> fist = new ArrayList<>(firstColumnList);
        fist.add(tvFirstHeader.getText().toString().trim());

        int firstW = getMaxWidth(fist);
        int otherW = Math.max(getMaxWidth(headerList),getMaxWidth(itemList));
        int sum = otherW * headerAdapter.getItemCount()+firstW;
        if(sum < ScreenUtils.getScreenWidth()){
            LogUtils.e("sum = ",sum,"getScreenWidth = ",ScreenUtils.getScreenWidth());
            //如果ScrollTableView小于屏幕宽度，则按比例设置宽度，宽度占满屏幕
            int sw = SizeUtils.dp2px(ScreenUtils.getScreenWidth());
            firstW = (int) (firstW*1.0f/sum * sw);
            otherW = (sw - firstW)/headerAdapter.getItemCount();
        }
        LogUtils.e("firstW = ",firstW);
        tvFirstHeader.setWidth(firstW);
        fistColumnAdapter.setItemWidth(firstW);
        headerAdapter.setItemWidth(otherW);
        itemAdapter.setItemWidth(otherW);
    }

    private int getMaxWidth(List<String> list){
        int width = 0;
        Paint  paint = new Paint();
        paint.setTextSize(14);
        for (String s : list) {
            width = (int) Math.max(width,paint.measureText(s));
        }
        return SizeUtils.dp2px(width)+60;
    }

    public int getItemCount(){
        return firstColumnList.size()*headerList.size();
    }

}
