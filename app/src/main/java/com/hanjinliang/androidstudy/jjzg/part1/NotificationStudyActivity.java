package com.hanjinliang.androidstudy.jjzg.part1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.hanjinliang.androidstudy.R;

/**
 * 1.1.4 3种Notification
 */
public class NotificationStudyActivity extends AppCompatActivity {
    private static final int YOUR_NOTIFICATION_ID = 0x002;
    private static final String CHANNEL_ID = "YOUR_NOTIFY_ID";
    private static final String YOUR_CHANNEL_NAME = "YOUR_NOTIFY_NAME";

    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jjzg_activity_notification_study);
        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        findViewById(R.id.normalNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalNotification();
            }
        });
        findViewById(R.id.foldNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoldNotification();
            }
        });
        findViewById(R.id.hangNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHangNotification();
            }
        });
    }

    public void showNormalNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentTitle("普通通知");
        Notification notification = builder.build();

        notificationManager.notify(100,notification);
    }

    public void showFoldNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentTitle("折叠式通知");
        Notification notification = builder.build();

        //用RemoteViews来创建自定义的Notification视图
        //指定展开时的视图
        notification.bigContentView= new RemoteViews(getPackageName(),R.layout.jjzg_view_fold);

        notificationManager.notify(1,notification);
    }

    public void showHangNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        Intent fullScreenIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 100 ,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        builder.setTicker("悬挂式通知");
        builder.setContentTitle("悬挂式通知");
        //设置点击跳转
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setFullScreenIntent(fullScreenPendingIntent,true);

        notificationManager.notify(0,builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "test", importance);
            channel.setDescription("Description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}