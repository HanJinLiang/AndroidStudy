package com.hanjinliang.androidstudy.hotfix;

import com.hanjinliang.androidstudy.Common.MyApplication;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by monkey on 2020-03-12 12:09.
 * Describe:
 */
public class HotFixUtils {

    public static void hotfix(MyApplication application, File patchFile) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(patchFile==null||!patchFile.exists()){
            return;
        }
        //获取到PathClassLoader
        ClassLoader classLoader = application.getClassLoader();

        //获取到 DexPathList 对象
        Field pathListField = ReflectUtil.findField(classLoader, "pathList");
        Object pathList = pathListField.get(classLoader);

        //获取到 dexElement数组 属性
        Field dexElementsField = ReflectUtil.findField(pathList, "dexElements");
        Object[] oldDexElements = (Object[]) dexElementsField.get(pathList);

        //通过 patchFile 创建 Element[]

        Method makeDexElements = ReflectUtil.findMethod(pathList, "makeDexElements", List.class,File.class,List.class,ClassLoader.class);
        //执行makeDexElements 方法  第一个参数null是因为是static方法
        List<File> files = Arrays.asList(patchFile);
        Object[] patchDexElements= (Object[]) makeDexElements.invoke(null,files,application.getCacheDir(),new ArrayList<IOException>(),classLoader);

        //重新拼接一个 dexElement数组   patch dex放在第一位
        Object[] newDexElements = (Object[]) Array.newInstance(oldDexElements.getClass().getComponentType(), patchDexElements.length + oldDexElements.length);
        System.arraycopy(patchDexElements,0,newDexElements,0,patchDexElements.length);
        System.arraycopy(oldDexElements,0,newDexElements,patchDexElements.length,oldDexElements.length);

        //直接给 pathList 复制为组合之后新的 dexElement[]
        dexElementsField.set(pathList,newDexElements);
    }
}
