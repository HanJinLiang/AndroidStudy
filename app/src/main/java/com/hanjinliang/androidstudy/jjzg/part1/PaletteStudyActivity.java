package com.hanjinliang.androidstudy.jjzg.part1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.palette.graphics.Palette;

import com.blankj.utilcode.util.ImageUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Palette的应用
 */
public class PaletteStudyActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jjzg_activity_palette_study);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);

        Bitmap bitmap = ImageUtils.getBitmap(R.drawable.banner3);
        Bitmap bitmap1 = ImageUtils.getBitmap(R.drawable.banner1);
        Bitmap bitmap2 = ImageUtils.getBitmap(R.drawable.banner2);
        Bitmap bitmap3 = ImageUtils.getBitmap(R.drawable.banner4);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);

        setTitle("Palette的应用");

        getBitmapColor(bitmaps.get(0), toolbar);
        imageView.setImageBitmap(bitmaps.get(0));
        findViewById(R.id.changePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = new Random().nextInt(4);
                getBitmapColor(bitmaps.get(index), toolbar);
                imageView.setImageBitmap(bitmaps.get(index));
            }
        });

    }

    /**
     * 获取图片主色调
     *
     * @param bitmap
     * @return
     */
    public static void getBitmapColor(final Bitmap bitmap, View view) {
        Palette.from(bitmap).maximumColorCount(10).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                List<Palette.Swatch> list = palette.getSwatches();
                int colorSize = 0;
                Palette.Swatch maxSwatch = null;
                for (int i = 0; i < list.size(); i++) {
                    Palette.Swatch swatch = list.get(i);
                    if (swatch != null) {
                        int population = swatch.getPopulation();
                        if (colorSize < population) {
                            colorSize = population;
                            maxSwatch = swatch;
                        }
                    }
                }
                Palette.Swatch s = palette.getDominantSwatch();//独特的一种
                Palette.Swatch s1 = palette.getVibrantSwatch();       //获取到充满活力的这种色调
                Palette.Swatch s2 = palette.getDarkVibrantSwatch();    //获取充满活力的黑
                Palette.Swatch s3 = palette.getLightVibrantSwatch();   //获取充满活力的亮
                Palette.Swatch s4 = palette.getMutedSwatch();           //获取柔和的色调
                Palette.Swatch s5 = palette.getDarkMutedSwatch();      //获取柔和的黑
                Palette.Swatch s6 = palette.getLightMutedSwatch();    //获取柔和的亮

                if (s1 != null) {
                    //设值shape中的color的颜色
//                    ColorDrawable background = (ColorDrawable) view.getBackground();
//                    background.setColor(s1.getRgb());
                    view.setBackgroundColor(maxSwatch.getRgb());

                }
            }
        });

    }
}