package com.hanjinliang.androidstudy.thirdLibs.flv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.hanjinliang.androidstudy.R;
import com.tencent.rtmp.ITXLivePlayListener;
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
        init(mLivePlayer,mView);
    }
    int width,height;
    private void init(TXLivePlayer livePlayer,TXCloudVideoView txCloudVideoView){
        //关键 player 对象与界面 view
        livePlayer.setPlayerView(txCloudVideoView);

        livePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle bundle) {
                if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                    //livePlayer.pause();
                    livePlayer.setVolume(0);
                }
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                if(width!=0){//第一次设置宽高
                    ViewGroup.LayoutParams layoutParams = txCloudVideoView.getLayoutParams();
                    layoutParams.width= ScreenUtils.getScreenWidth();
                    int viewWidth = layoutParams.width;

                    int viewHeight= (int) (viewWidth*height*1.0f/width);
                    layoutParams.height=viewHeight;
                    txCloudVideoView.setLayoutParams(layoutParams);
                    LogUtils.e("layoutParams.width="+layoutParams.width+"--layoutParams.height="+layoutParams.height+"--width="+width+"--height="+height);
                }
                 width= (int) bundle.get(TXLiveConstants.NET_STATUS_VIDEO_WIDTH);
                 height= (int) bundle.get(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT);
            }
        });
        // 设置填充模式
        livePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        livePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);

        //String flvUrl = "http://183.214.92.119:8000/main/013512345305_2.flv";
        //mLivePlayer2.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
        String flvUrl2 ="rtmp://58.200.131.2:1935/livetv/hunantv";
        livePlayer.startPlay("http://183.214.92.119:8000/main/013512345303_1.flv", TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
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
