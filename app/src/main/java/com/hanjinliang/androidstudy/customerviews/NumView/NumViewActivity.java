package com.hanjinliang.androidstudy.customerviews.NumView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.hanjinliang.androidstudy.R;

public class NumViewActivity extends AppCompatActivity {
    SeekBar mSeekBar;
    NumView mNumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_view);
        mSeekBar= (SeekBar) findViewById(R.id.seekBar);
        mNumView= (NumView) findViewById(R.id.numView);

        mNumView.setNum(5);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mNumView.setNum(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
