package com.hanjinliang.androidstudy.customerviews.signView;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ViewUtils;
import com.hanjinliang.androidstudy.R;

import java.io.File;

public class SignView extends View {

    private Paint paint;
    // 手指路径，使用贝塞尔路线
    private Path path;
    private float perX;
    private float perY;
    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1、设置禁用硬件设置
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 2、设置手指画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(SizeUtils.dp2px(4));

        path = new Path();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#F5F5F5"));
        // 把手指轨迹划到目标路径上
        canvas.drawPath(path, paint);
    }

    //使用贝塞尔曲线，使折线过度圆滑
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 记录手指触摸的初始化位置
            case MotionEvent.ACTION_DOWN:
                if(signListener!=null && path.isEmpty()){//开始签名回调
                    signListener.startSign();
                }
                path.moveTo(event.getX(), event.getY());
                perX = event.getX();
                perY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (perX + event.getX()) / 2;
                float endY = (perY + event.getY()) / 2;
                path.quadTo(perX, perY, endX, endY);
                perX = event.getX();
                perY = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 清空签名
     */
    public void clearSign(){
        path.reset();//重置
        postInvalidate();
    }

    /**
     * 保存签名
     */
    public void saveSign(){
        Bitmap bitmap = ImageUtils.view2Bitmap(this);
        saveImageToGallery(getContext(),bitmap);
    }

    /**
     * 保存图片至相册
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        if(bmp==null||bmp.isRecycled()){
            return;
        }
        if (Build.VERSION.SDK_INT < 29) {
            if(PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                File saveFile = ImageUtils.save2Album(bmp,"tongche",Bitmap.CompressFormat.JPEG);
                if(saveFile!=null){
                    Toast.makeText(context, "截图已保存至相册", Toast.LENGTH_SHORT).show();
                }
            }else {
                PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).request();
            }
        }else {
            File saveFile = ImageUtils.save2Album(bmp,"tongche",Bitmap.CompressFormat.JPEG);
            if(saveFile!=null){
                Toast.makeText(context, "截图已保存至相册", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private SignListener signListener;
    public void setSignListener(SignListener signListener) {
        this.signListener = signListener;
    }

    /**
     * 签名回调
     */
    public interface SignListener{
        void startSign();
    }


}