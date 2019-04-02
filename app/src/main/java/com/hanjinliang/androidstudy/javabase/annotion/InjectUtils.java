package com.hanjinliang.androidstudy.javabase.annotion;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by monkey on 2019-04-02 15:35.
 * Describe:自定义注解获取
 */

public class InjectUtils {
    
    public static void inject(final Activity activity){
        //遍历属性
        Class<Activity> activityClass= (Class<Activity>) activity.getClass();
        Field[] declaredFields = activityClass.getDeclaredFields();
        for(Field field:declaredFields){
            if(field.isAnnotationPresent(BindView.class)){
                int viewId = field.getAnnotation(BindView.class).value();
                View view = activity.findViewById(viewId);
                try {
                    field.setAccessible(true);
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        //遍历方法 将设置注解的方法绑定到相应的view的点击事件中
        Method[] methods = activityClass.getDeclaredMethods();
        for(final Method method:methods){
            if(method.isAnnotationPresent(BindClick.class)){
                int[] viewIds = method.getAnnotation(BindClick.class).value();
                for(int viewId:viewIds) {
                    View view = activity.findViewById(viewId);
                    if (view != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    //调用该方法
                                    method.setAccessible(true);
                                    method.invoke(activity, v);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
