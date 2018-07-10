package com.hanjinliang.androidstudy.javabase.fanxing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.androidstudy.R;
import com.hanjinliang.androidstudy.javabase.BeanToMap;
import com.hanjinliang.androidstudy.javabase.StudentBean;

import java.io.Serializable;
import java.util.Map;

/**
 * 泛型学习
 */
public class FanxingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanxing);
        Button button=new Button(this);
        TextView textView=new TextView(this);
        View view=new View(this);

        Point<? super TextView>  point=new Point<View>(view,view);

        point.setX(button);
        LogUtils.e(point.getX());

        IntegerComparable small= ComparaleUtil.getMinest(new IntegerComparable(1,2),new IntegerComparable(34,2),new IntegerComparable(45,2),new IntegerComparable(16,2),new IntegerComparable(10,2));
        LogUtils.e(small.getX());


        StudentBean studentBean=new StudentBean(20,"刘文豪","男","池河镇");
        studentBean.setName(null);
        Map<String, Object> objectMap = new BeanToMap().beanToMap(studentBean);
        objectMap.toString();
    }

    class IntegerComparable  extends Point<Integer> implements Comparable<IntegerComparable>,Serializable{

        IntegerComparable(Integer x,Integer y){
            super(x,y);
        }
        @Override
        public boolean comparable(IntegerComparable integerComparable) {
            return integerComparable.getX()>getX();
        }
    }


}
