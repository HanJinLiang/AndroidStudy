package com.hanjinliang.androidstudy.systemwidget.recyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.hanjinliang.androidstudy.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by HanJinLiang on 2018-08-28.
 */

public class CustomerItemDecoration extends RecyclerView.ItemDecoration {
    private int item_height;
    private Paint paint;
    Bitmap mBitmap;
    public CustomerItemDecoration(Context context) {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        mBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.item_decoration);
        item_height=mBitmap.getHeight()/2;
    }


    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }


    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            if(isRelated(i,parent)) {
                View view = parent.getChildAt(i);
                float top = view.getBottom() - item_height / 4;
                float bottom = view.getBottom() + item_height + item_height / 4;
                // c.drawRect(left, top, right, bottom, paint);
                c.drawBitmap(mBitmap, null, new RectF(left, top, right, bottom), paint);
            }
        }

    }

    /**
     * 判断是否有关联
     * @param curIndex
     * @return
     */
    private boolean isRelated(int curIndex,RecyclerView parent) {
        parent.getAdapter();
        if(curIndex%3==0){
            return true;
        }
        return false;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //padding效果
        outRect.bottom=item_height;
    }

}
