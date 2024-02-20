package com.hanjinliang.androidstudy.customerviews.IndicatorSeekBar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.hanjinliang.androidstudy.R;

public class IndicatorSeekBarActivity extends AppCompatActivity {
    AppCompatSeekBar seekBar;
    TextView valueMin;
    TextView valueMax;
    TextView progressText;

    private static int MAX_VALUE = 11;
    private static int MIN_VALUE = 2;
    private static int VALUE_DOUBLE = 10;

    private int selectValue = MIN_VALUE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_seekbar);
        seekBar = findViewById(R.id.seekBar);
        valueMin = findViewById(R.id.valueMin);
        valueMax = findViewById(R.id.valueMax);
        progressText = findViewById(R.id.progressText);

        seekBar.setMax((MAX_VALUE - MIN_VALUE )*VALUE_DOUBLE);

        valueMin.setText(MIN_VALUE+"");
        valueMax.setText(MAX_VALUE+"");
        progressText.setVisibility(View.INVISIBLE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progressText.getVisibility() != View.VISIBLE){
                    progressText.setVisibility(View.VISIBLE);
                }
                selectValue = Math.round(progress/10f) + MIN_VALUE;
                progressText.setText(selectValue + "周岁");
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) progressText.getLayoutParams();
                params.leftMargin = (int) ((progress/(seekBar.getMax()*1.0f))*(seekBar.getMeasuredWidth()-SizeUtils.dp2px(26)));
                progressText.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress((selectValue - MIN_VALUE) * VALUE_DOUBLE);
            }
        });
    }
}
