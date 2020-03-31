package com.hanjinliang.androidstudy.customerviews.EraserView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class EraserViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_eraser_view);
    }
}
