package com.hanjinliang.androidstudy.customerviews.EraserView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.hanjinliang.androidstudy.R;

import java.io.InputStream;

public class EraserView extends View {

    private Paint paint;
    private Bitmap decodeResourceSRC;
    private Bitmap createBitmapDST;
    // 手指路径，使用贝塞尔路线
    private Path path;
    private float perX;
    private float perY;

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1、设置禁用硬件设置
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 2、设置手指画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(45);

        // 3、生成图像手指源目标
        // 源
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;

        decodeResourceSRC = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_bg,options);
        // 目标
        createBitmapDST = Bitmap.createBitmap(decodeResourceSRC.getWidth(), decodeResourceSRC.getHeight(),
                Bitmap.Config.ARGB_4444);

        canvas2=new Canvas(createBitmapDST);
        path = new Path();
    }
    Canvas canvas2 ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 分层绘制
        int saveLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,Canvas.ALL_SAVE_FLAG);

        // 把手指轨迹划到目标路径上
        canvas2.drawPath(path, paint);

        // 把目标图像画到画布上
        canvas.drawBitmap(createBitmapDST, 0, 0, paint);

        // 计算源图像区域
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(decodeResourceSRC, 0, 0, paint);

        paint.setXfermode(null);
        canvas.restoreToCount(saveLayer);

    }

    //使用贝塞尔曲线，使折线过度圆滑
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            // 记录手指触摸的初始化位置
            case MotionEvent.ACTION_DOWN:

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

}