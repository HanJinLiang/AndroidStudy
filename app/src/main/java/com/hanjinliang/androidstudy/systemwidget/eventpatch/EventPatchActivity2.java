package com.hanjinliang.androidstudy.systemwidget.eventpatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;

public class EventPatchActivity2 extends AppCompatActivity {
    ListView listView;
    Button testButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                String data = result.getData().getStringExtra("data");
//                //
//                Handle data from SecondActivity
//                LogUtils.e("data=="+data);
//            }
//        }).launch(new Intent(this,EventPatchActivity.class));、



//        registerForActivityResult(new ActivityResultContracts.TakeVideo(), bitmap -> {
//            if (bitmap!=null) {
//
//            }
//        }).launch(null);

        setContentView(R.layout.activity_event_patch2);
        listView=  findViewById(R.id.listView);
        String[] names={ "张三","李四","王五","赵六","田七","张三","李四","王五","赵六","田七","张三","李四","王五","赵六","田七"};
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names));

        testButton=findViewById(R.id.testButton);
        HandlerThread handlerThread=new HandlerThread("test");
        handlerThread.start();
        Handler handler= new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //消息处理
                LogUtils.e("蔡总牛逼=="+testButton.isPressed());
                //sendMessageDelayed(Message.obtain(),1000);
            }
        };
        handler.sendMessageDelayed(Message.obtain(),1000);
    }


}
