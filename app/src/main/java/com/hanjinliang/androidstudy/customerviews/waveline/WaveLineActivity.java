package com.hanjinliang.androidstudy.customerviews.waveline;

import android.os.Bundle;

import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 *波浪线
 */
public class WaveLineActivity extends AppCompatActivity {
    WaveLineView mWaveLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_line);
        mWaveLine= (WaveLineView) findViewById(R.id.WaveLine);
        mWaveLine.setProgress(70);
    }
}
