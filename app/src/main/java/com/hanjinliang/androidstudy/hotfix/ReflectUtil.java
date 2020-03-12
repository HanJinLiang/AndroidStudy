package com.hanjinliang.androidstudy.hotfix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by monkey on 2020-03-12 13:50.
 * Describe:反射工具类
 */
public class ReflectUtil {
    /**
     * 反射获取到属性
     * @param obj
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static Field findField(Object obj,String fieldName) throws NoSuchFieldException {
        Class<?> cls = obj.getClass();
        while (cls!=Object.class) {
            try {
                Field field = cls.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    return field;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            cls = cls.getSuperclass();
        }
        throw new NoSuchFieldException(obj.getClass()+"not find "+fieldName);
    }

    /**
     * 反射获取到方法
     * @param obj
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     */
    public static Method findMethod(Object obj,String methodName,Class<?>... args) throws NoSuchMethodException {
        Class<?> cls = obj.getClass();
        while (cls!=Object.class) {
            try {
                Method method = cls.getDeclaredMethod(methodName,args);
                if (method != null) {
                    method.setAccessible(true);
                    return method;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            cls = cls.getSuperclass();
        }
        throw new NoSuchMethodException(obj.getClass()+"not find "+methodName);
    }
}
