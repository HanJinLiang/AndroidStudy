package com.hanjinliang.androidstudy.thirdLibs.flv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hanjinliang.androidstudy.R;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;


public class FlvPlayActivity extends AppCompatActivity {
    TXCloudVideoView mView;
    TXLivePlayer mLivePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flv_play);
        //https://cloud.tencent.com/document/product/881/20217

        //mPlayerView 即步骤1中添加的界面 view
         mView = findViewById(R.id.video_view);
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mView);

        String flvUrl = "http://183.214.92.119:8000/main/013512345305_2.flv";
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV

        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停
        mLivePlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 继续
        mLivePlayer.resume();
    }
}
