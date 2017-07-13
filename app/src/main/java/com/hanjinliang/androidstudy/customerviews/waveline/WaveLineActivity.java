package com.hanjinliang.androidstudy.customerviews.waveline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hanjinliang.androidstudy.R;

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
