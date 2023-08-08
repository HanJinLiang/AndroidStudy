package com.hanjinliang.androidstudy.systemwidget.meituanHotalList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hanjinliang.androidstudy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 美团 酒店列表地图下拉效果UI
 */
public class MeituanHotalListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseQuickAdapter<String,BaseViewHolder> adapter;
    LinearLayout design_bottom_sheet;
    TextView handlerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meituan_hotal_list);
        design_bottom_sheet = findViewById(R.id.design_bottom_sheet);
        recyclerView = findViewById(R.id.recyclerView);
        handlerView = findViewById(R.id.handlerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_app_list) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.app_name,item);
            }
        };
        adapter.bindToRecyclerView(recyclerView);
        ArrayList<String> datas=new ArrayList<>();
        for(int i=0;i<20;i++){
            datas.add("酒店啦啦啦啦啦--"+i);
        }
        adapter.addData(datas);



        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(design_bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.8f);
        bottomSheetBehavior.setSkipCollapsed(true);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull @NotNull View bottomSheet, int newState) {
                LogUtils.e("onStateChanged",newState);
                if(newState == BottomSheetBehavior.STATE_HALF_EXPANDED){
                    //半展开
                    bottomSheetBehavior.setDraggable(false);
                }
            }

            @Override
            public void onSlide(@NonNull @NotNull View bottomSheet, float slideOffset) {
                LogUtils.e("onSlide",slideOffset);
            }
        });



    }
    boolean oldDraggable;

}