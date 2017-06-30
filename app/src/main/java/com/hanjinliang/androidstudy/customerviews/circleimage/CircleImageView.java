package com.hanjinliang.androidstudy.customerviews.circleimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by HanJinLiang on 2017-06-29.
 * 圆形ImageView
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    Paint mPaint;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    Canvas mCanvas;
    Bitmap mBitmap2;
    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mBitmap2=Bitmap.createBitmap(SizeUtils.dp2px(100),SizeUtils.dp2px(100), Bitmap.Config.ARGB_4444);
        mCanvas=new Canvas(mBitmap2);
        mCanvas.drawColor(Color.GRAY);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            //强制改变view的宽高一致，以小值为准
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBitmap.recycle();
        mBitmap=null;
    }

    Bitmap mBitmap;
    BitmapShader mBitmapShader=null;
    @Override
    protected void onDraw(Canvas canvas) {
        //灰色背景
        canvas.drawColor(Color.GRAY);
        //super.onDraw(canvas);
        //直接使用Xfreadmode 将bitmap转为圆形
//        if(mBitmap==null||mBitmap.isRecycled()){
//            mBitmap=ImageUtils.toRound(ImageUtils.drawable2Bitmap(getDrawable()));
//        }
//        canvas.drawBitmap(toRoundCorner(mBitmap),0,0,null);

        //使用BitmapShader 渲染画笔
        if(mBitmap==null||mBitmap.isRecycled()){
            mBitmap= ImageUtils.drawable2Bitmap(getDrawable());
            mBitmapShader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mBitmapShader);
        }
        //圆形
        //canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,mPaint);
        //扇形
        //canvas.drawArc(new RectF(0,0,getWidth(),getWidth()),-90,90,true,mPaint);
        //圆角矩形
        //canvas.drawRoundRect(new RectF(0,0,getWidth(),getWidth()),50,50,mPaint);
        //椭圆
        //canvas.drawOval(new RectF(0,0,getWidth(),getWidth()/2),mPaint);
        //文字
        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(50);
        canvas.drawText("hello world",0,getWidth()/2,mPaint);
    }

    public Bitmap toRoundCorner(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth()/2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
