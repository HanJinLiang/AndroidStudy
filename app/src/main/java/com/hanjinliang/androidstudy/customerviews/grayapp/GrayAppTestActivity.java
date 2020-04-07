package com.hanjinliang.androidstudy.customerviews.grayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.hanjinliang.androidstudy.R;

/**
 * 灰色处理 app demo
 * https://mp.weixin.qq.com/s/EioJ8ogsCxQEFm44mKFiOQ
 */
public class GrayAppTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gray_app_test);

        //方案一
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

        //方案二
//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        if("FrameLayout".equals(name)){
//            int count = attrs.getAttributeCount();
//            for (int i = 0; i < count; i++) {
//                String attributeName = attrs.getAttributeName(i);
//                String attributeValue = attrs.getAttributeValue(i);
//                if (attributeName.equals("id")) {
//                    int id = Integer.parseInt(attributeValue.substring(1));
//                    String idVal = getResources().getResourceName(id);
//                    if ("android:id/content".equals(idVal)) {
//                        GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
//                        return grayFrameLayout;
//                    }
//                }
//            }
//        }
//        return super.onCreateView(name, context, attrs);
//    }
}
