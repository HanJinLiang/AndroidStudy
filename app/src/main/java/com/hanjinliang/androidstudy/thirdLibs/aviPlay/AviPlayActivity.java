package com.hanjinliang.androidstudy.thirdLibs.aviPlay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.hanjinliang.androidstudy.R;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class AviPlayActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private IjkMediaPlayer mPlayer;
    String path = Environment.getExternalStorageDirectory().getPath() + "/test.avi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avi_play);

        initSurfaceView();
        initPlayer();
    }

    private void initSurfaceView(){
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
    }
    private void initPlayer(){
        mPlayer = new IjkMediaPlayer();
        try {
           // mPlayer.setDataSource(path);
            mPlayer.setDataSource("https://bus.idrivergo.com:4433/file-server/api/v1/files/download?path=ZGF0YS9mdHAvMjAyMS0wNS0xNC81NTAzNzUwODE2MDQyODQ0MTYvNjAwMDAxODI0Mzg0MDkwMTEzL1NCSC0wMTM1MTIzNDUzMDMtQ0gxLTIwMjEwNTE0LTAwMjA0Ny5hdmk=");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
        mPlayer.start();
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //将所播放的视频图像输出到指定的SurfaceView组件
        mPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

}
