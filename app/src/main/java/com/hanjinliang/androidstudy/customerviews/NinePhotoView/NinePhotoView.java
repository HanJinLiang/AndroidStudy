package com.hanjinliang.androidstudy.customerviews.NinePhotoView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import static com.hanjinliang.androidstudy.R.drawable.banner;
import static com.hanjinliang.androidstudy.R.drawable.girl_bg;

/**
 * Created by HanJinLiang on 2017-07-03.
 * 选择图片
 * 参考http://www.jianshu.com/p/138b98095778
 */
public class NinePhotoView extends ViewGroup {
    public static final int MAX_PHOTO_NUMBER = 9;
    public static final int MAX_LINE_NUMBER = 4;

    private int[] constImageIds = { girl_bg, girl_bg,
            girl_bg, girl_bg, girl_bg,
            girl_bg, girl_bg, girl_bg,
            banner };

    // horizontal space among children views
    int hSpace = SizeUtils.dp2px(10);
    // vertical space among children views
    int vSpace =  SizeUtils.dp2px(10);

    // every child view width and height.
    int childWidth = 0;
    int childHeight = 0;

    // store images res id
    ArrayList<String> mImageResArrayList = new ArrayList<String>();
    private AppCompatButton addPhotoView;//添加按钮

    public NinePhotoView(Context context) {
        super(context);
    }

    public NinePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

//        TypedArray t = context.obtainStyledAttributes(attrs,
//                R.styleable.NinePhotoView, 0, 0);
//        hSpace = t.getDimensionPixelSize(
//                R.styleable.NinePhotoView_ninephoto_hspace, hSpace);
//        vSpace = t.getDimensionPixelSize(
//                R.styleable.NinePhotoView_ninephoto_vspace, vSpace);
//        t.recycle();

        addPhotoView = new AppCompatButton(context);
        StateListDrawable stateListDrawable =new StateListDrawable();
        // 设置选中状态
        stateListDrawable.addState(new int[] { android.R.attr.state_pressed}, ContextCompat.getDrawable(context,R.drawable.add_photo_press));
        stateListDrawable.addState(new int[]{},ContextCompat.getDrawable(context,R.drawable.add_photo));
        addPhotoView.setBackgroundDrawable(stateListDrawable);
        addView(addPhotoView);

        addPhotoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                addPhotoBtnClick();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);
        //计算每一个Child的宽度
        childWidth = (rw - (MAX_LINE_NUMBER-1)* hSpace) / MAX_LINE_NUMBER;
        childHeight = childWidth;

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            //这里由于宽高是childWidth算好的  可以不要调用此方法
            //this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            lParams.left = (i % MAX_LINE_NUMBER) * (childWidth + hSpace);
            lParams.top = (i / MAX_LINE_NUMBER) * (childWidth + vSpace);
        }

        int vw = rw;
        int vh = rh;
        //不足一行的时候 宽高都是warp
        if (childCount < MAX_LINE_NUMBER) {
            vw = childCount * (childWidth + hSpace);
        }
        vh = ((childCount + MAX_LINE_NUMBER) / MAX_LINE_NUMBER) * (childWidth + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int index=i;
            final View child = this.getChildAt(i);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
                    lParams.top + childHeight);
        }
    }

    public void addPhoto(int resId) {
            final View newChild = new View(getContext());
            newChild.setClickable(true);
            addView(newChild,0);
            requestLayout();
            invalidate();
            newChild.setBackgroundResource(resId);
            newChild.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addDeleteClick(newChild);
            }
            });
            if(getChildCount()==MAX_PHOTO_NUMBER+1){
                removeView(addPhotoView);
                return;
            }
    }


    public void addPhotoBtnClick() {
        final CharSequence[] items = { "Take Photo", "Photo from gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                addPhoto(constImageIds[3]);
            }

        });
        builder.show();
    }


    public void addDeleteClick(final View view) {
        final CharSequence[] items = { "Delete Photo" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                removeView(view);
                requestLayout();
                invalidate();
                if(getChildCount()<MAX_PHOTO_NUMBER&&addPhotoView.getParent()==null){
                    addView(addPhotoView,getChildCount());//添加addPhotoView
                }
            }

        });
        builder.show();
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new NinePhotoView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof NinePhotoView.LayoutParams;
    }
}
