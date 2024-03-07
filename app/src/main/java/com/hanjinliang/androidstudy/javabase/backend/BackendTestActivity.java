package com.hanjinliang.androidstudy.javabase.backend;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.data.BaseCallBackWithMessage;
import com.hanjinliang.androidstudy.data.RDataRepository;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by monkey on 2019-05-23 10:29.
 * Describe:后台开发学习
 */

public class BackendTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.uploadPic,R.id.test})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.uploadPic:
                RDataRepository.getInstance().uploadPic(ImageUtils.bitmap2Bytes(ImageUtils.getBitmap(R.drawable.banner1), Bitmap.CompressFormat.JPEG,100), new BaseCallBackWithMessage<String>() {
                    @Override
                    protected void onSuccess(String code, String message, String data) {

                    }
                });
                break;
            case R.id.test:
                RDataRepository.getInstance().selectUserCond(new BaseCallBackWithMessage<String>() {
                    @Override
                    protected void onSuccess(String code, String message, String data) {

                    }
                });

                break;
        }
    }
}
