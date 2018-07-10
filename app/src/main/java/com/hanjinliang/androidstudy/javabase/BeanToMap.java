package com.hanjinliang.androidstudy.javabase;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HanJinLiang on 2018-07-10.
 *
 */
public class BeanToMap{
    /**
     * bean类转map
     * @param object
     * @return
     */
    public static Map<String,Object> beanToMap(Object object){
        Map<String, Object> map = new HashMap<>();
        //获得类
        Class clazz = object.getClass();
        // 获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] fields = clazz.getDeclaredFields();
        // 获得某个类的所有的公共（public）的字段，包括父类中的字段。
        //Field[] fields = clazz.getFields();
        for (Field field : fields) {
            try {
                Method m = clazz.getMethod("get" + upperCase(field.getName()));
                Object value = m.invoke(object);
                if (value != null) {//null 不放入map
                    map.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * String 首字母大写
     * @param str
     * @return
     */
    private static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}
