package com.hanjinliang.androidstudy.thirdLibs.camera;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;
import com.hbzhou.open.flowcamera.CustomCameraView;
import com.hbzhou.open.flowcamera.listener.ClickListener;
import com.hbzhou.open.flowcamera.listener.FlowCameraListener;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hbzhou.open.flowcamera.CustomCameraView.BUTTON_STATE_BOTH;

/**
 * Created by monkey on 2020-06-17 11:02.
 * Describe:
 */
public class CameraVideoMainActivity extends AppCompatActivity {
    @BindView(R.id.customCameraView)
    CustomCameraView customCameraView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean granted = PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO);
        if(!granted){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 0);

            return;
        }
        setContentView(R.layout.activity_goto_camera);
        ButterKnife.bind(this);
        // 绑定生命周期 您就不用关心Camera的开启和关闭了 不绑定无法预览
        customCameraView.setBindToLifecycle(this);


        // 设置白平衡模式
//        flowCamera.setWhiteBalance(WhiteBalance.AUTO)
        // 设置只支持单独拍照拍视频还是都支持
        // BUTTON_STATE_ONLY_CAPTURE  BUTTON_STATE_ONLY_RECORDER  BUTTON_STATE_BOTH
        customCameraView.setCaptureMode(CustomCameraView.BUTTON_STATE_ONLY_RECORDER);
        // 开启HDR
//        flowCamera.setHdrEnable(Hdr.ON)
        // 设置最大可拍摄小视频时长 S
        customCameraView.setRecordVideoMaxTime(10);
        // 设置拍照或拍视频回调监听
        customCameraView.setFlowCameraListener(new FlowCameraListener() {
            @Override
            public void captureSuccess(@NonNull File file) {
                ToastUtils.showLong(file.getAbsolutePath());
                finish();
            }

            @Override
            public void recordSuccess(@NonNull File file) {
                ToastUtils.showLong(file.getAbsolutePath());
                finish();
            }

            @Override
            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                ToastUtils.showLong(message);
            }
        });

        //左边按钮点击事件
        customCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
