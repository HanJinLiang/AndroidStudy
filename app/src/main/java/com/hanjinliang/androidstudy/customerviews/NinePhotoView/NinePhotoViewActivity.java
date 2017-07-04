package com.hanjinliang.androidstudy.customerviews.NinePhotoView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.blankj.utilcode.util.LogUtils;
import com.darsh.multipleimageselect.models.Image;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.thirdLibs.largeimage.ViewBigImageActivity;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;

public class NinePhotoViewActivity extends TakePhotoActivity {
    NinePhotoView mPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_nine_photo_view);
        mPhotoView= (NinePhotoView) findViewById(R.id.photoview);

        mPhotoView.setOnClickListener(new NinePhotoView.OnClickListener() {
            @Override
            public void onTakePhotoClick() {
                configCompress(getTakePhoto());
                getTakePhoto().onPickMultiple(9);
            }

            @Override
            public void onOpenCameraClick() {
                configCompress(getTakePhoto());
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromCapture(imageUri);

            }

            @Override
            public void onItemClick(int index, String imgPath) {
                //查看大图
                Bundle bundle = new Bundle();
                bundle.putInt("code",index);
                bundle.putBoolean("isLocal",true);
                bundle.putStringArrayList("imageuri",mPhotoView.getImagePathList());
                startActivity(new Intent(NinePhotoViewActivity.this, ViewBigImageActivity.class).putExtras(bundle));
            }
        });
    }

    /**
     * 设置压缩
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto){
        int maxSize= 102400;
        int width= 800;
        int height= 800;
        boolean showProgressBar=true;
        boolean enableRawFile =true;
            CompressConfig  config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        takePhoto.onEnableCompress(config,showProgressBar);


    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<String>  imgs=new ArrayList<>();
        for(TImage image:result.getImages()){
            imgs.add(image.getCompressPath());
        }
        mPhotoView.addPhotos(imgs);
    }
}
