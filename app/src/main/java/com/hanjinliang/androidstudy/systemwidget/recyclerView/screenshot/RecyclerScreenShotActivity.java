package com.hanjinliang.androidstudy.systemwidget.recyclerView.screenshot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.javabase.annotion.BindView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * recyclerView 截屏
 */
public class RecyclerScreenShotActivity extends AppCompatActivity {
    // 要申请的权限
     private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_screenshot);
        ButterKnife.bind(this);
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter adapter= new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_app_list) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.app_name,item);
            }
        };
        adapter.bindToRecyclerView(mRecyclerView);
        ArrayList<String> datas=new ArrayList<>();
        for(int i=0;i<20;i++){
            datas.add("index--"+i);
        }
        adapter.addData(datas);

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                      // 检查该权限是否已经获取
                       int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                 // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                      if (i != PackageManager.PERMISSION_GRANTED) {
                           // 如果没有授予该权限，就去提示用户请求
                          ActivityCompat.requestPermissions(this, permissions, 321);
                            }
                }

    }



    @OnClick(R.id.save)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.save:
                Bitmap bitmap =  ImageUtils.view2Bitmap(mRecyclerView);
//                Bitmap bitmap = shotRecyclerView(mRecyclerView);
                boolean save=ImageUtils.save(bitmap, Environment.getExternalStorageDirectory().getPath()+"/test.jpg", Bitmap.CompressFormat.JPEG);
                ToastUtils.showShort("save-"+save);
                break;
        }
    }


    /**
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }
}
