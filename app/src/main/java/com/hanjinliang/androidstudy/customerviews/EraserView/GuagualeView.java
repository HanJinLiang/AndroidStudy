package com.hanjinliang.androidstudy.customerviews.EraserView;

import android.content.Context;
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
import com.hanjinliang.androidstudy.R;

/**
 * 刮刮乐
 */
public class GuagualeView extends View {

    private Paint paint;
    private Bitmap decodeResourceSRC;
    private Bitmap createBitmapDST;
    // 手指路径，使用贝塞尔路线
    private Path path;
    private float perX;
    private float perY;
    private Context mContext;
    public GuagualeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        // 1、设置禁用硬件设置
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 2、设置手指画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(45);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        // 3、生成图像手指源目标
        // 源
        decodeResourceSRC =BitmapFactory.decodeResource(context.getResources(),R.drawable.girl_bg);
        //
        decodeResourceSRC=ImageUtils.scale(decodeResourceSRC,ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight());
        // 目标
        createBitmapDST = Bitmap.createBitmap(decodeResourceSRC.getWidth(), decodeResourceSRC.getHeight(),
                Bitmap.Config.ARGB_4444);

        canvas2=new Canvas(createBitmapDST);
        //绘制“乌云”
        canvas2.drawColor(Color.parseColor("#c0c0c0"));//绘制灰尘
        path = new Path();
    }
    Canvas canvas2 ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制底图
        canvas.drawBitmap(decodeResourceSRC,0,0,null);
        //画线，即实现擦乌云的效果
        drawPath();
        //将实现了擦乌云效果的为图对象，绘制在底图上面
        canvas.drawBitmap(createBitmapDST, 0, 0, null);
        super.onDraw(canvas);
    }

    private void drawPath(){
        canvas2.drawPath(path, paint);
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