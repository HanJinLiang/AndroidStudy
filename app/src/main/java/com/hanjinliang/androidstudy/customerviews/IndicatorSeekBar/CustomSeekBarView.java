package com.hanjinliang.androidstudy.customerviews.IndicatorSeekBar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet; 
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.hanjinliang.androidstudy.R;


/**
 * Created by liuwenhao on 2/19/24
 */
public class CustomSeekBarView extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private TextPaint textPaint;
    private TextPaint mValuePaint;
    private TextPaint mLeftPaint;
    private float progress;
    private float maxProgress = 9;
    private Bitmap thumbBitmap;

    private float lineHeight = 0f;
    private final Bitmap thumbImage = BitmapFactory.decodeResource(
            getResources(), R.drawable.seekbar_thumb);
    private final Bitmap bgImage = BitmapFactory.decodeResource(
            getResources(), R.drawable.text_bg);
    private final float thumbWidth = thumbImage.getWidth();
    private final float thumbHalfWidth = 0.5f * thumbWidth;
    private final float thumbHalfHeight = 0.5f * thumbImage.getHeight();

    // 最大值和最小值
    private int maxValue = 11;
    private int minValue = 2;
    private float thumbPosition; // 拇指位置

    private int mDistanceLeft;

    public CustomSeekBarView(Context context) {
        super(context);
        init();
    }

    public CustomSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#8d8d8d"));

        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#32c45d"));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40f);

        mValuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mValuePaint.setColor(Color.WHITE);
        mValuePaint.setTextSize(30f);

        mLeftPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mLeftPaint.setColor(Color.parseColor("#383838"));
        mLeftPaint.setTextSize(42f);

        thumbBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);

        lineHeight = SizeUtils.dp2px( 4);

        mDistanceLeft = SizeUtils.dp2px( 70);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawText("儿童", 0, SizeUtils.dp2px( 45), mLeftPaint);

        // 绘制最大值和最小值
        canvas.drawText(String.valueOf(minValue), mDistanceLeft, SizeUtils.dp2px( 20), textPaint);
        canvas.drawText(String.valueOf(maxValue), getWidth() - textPaint.measureText(String.valueOf(maxValue)) - bgImage.getWidth() / 2, SizeUtils.dp2px( 20), textPaint);

        // 绘制背景色的进度条
        canvas.drawRect(mDistanceLeft , SizeUtils.dp2px( 37), getWidth() - bgImage.getWidth()/2, SizeUtils.dp2px( 37) + lineHeight, backgroundPaint);

        // 计算拇指位置和绘制滑块
        thumbPosition = progress / maxProgress * (getWidth() - bgImage.getWidth() / 2 - mDistanceLeft) + mDistanceLeft - thumbBitmap.getWidth()/2;
        //上面显示的位置
        float thumbPosition1 = thumbPosition - (bgImage.getWidth() - thumbBitmap.getWidth()) / 2;

        // 绘制进度条
        canvas.drawRect(mDistanceLeft , SizeUtils.dp2px( 37), thumbPosition + thumbBitmap.getWidth() / 2, SizeUtils.dp2px( 37) + lineHeight, progressPaint);

        //绘制滑动块
        canvas.drawBitmap(thumbImage, thumbPosition,
                SizeUtils.dp2px(37) + lineHeight - thumbHalfHeight, backgroundPaint);


        // 绘制当前值
        String progressValue = Math.round(progress) + minValue + "周岁";
        float textWidth = mValuePaint.measureText(progressValue);

        canvas.drawBitmap(bgImage, thumbPosition1, SizeUtils.dp2px( 5), mValuePaint);
        canvas.drawText(progressValue, thumbPosition1 + bgImage.getWidth() / 2 - textWidth / 2, SizeUtils.dp2px( 16), mValuePaint);
 
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() > mDistanceLeft) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    progress = (event.getX() - mDistanceLeft) * maxProgress / (getWidth() - mDistanceLeft - bgImage.getWidth() / 2);
                    if (progress < 0) {
                        progress = 0;
                    } else if (progress > maxProgress) {
                        progress = maxProgress;
                    }
                    invalidate();
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        int width = 200;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = thumbImage.getHeight();
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width, SizeUtils.dp2px( 63));
    }
}



