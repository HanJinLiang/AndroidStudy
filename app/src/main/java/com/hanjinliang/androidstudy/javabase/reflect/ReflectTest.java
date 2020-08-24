package com.hanjinliang.androidstudy.javabase.reflect;

import android.os.AsyncTask;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: 反射学习
 * @Author: Monkey
 * @CreateDate: 2020/8/18 11:09
 */
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Student student=new Student();
//
//        //获取Class对象的三种方式
//        //通过对象实例获取
//        Class<? extends Student> cls = student.getClass();
//        //通过直接类静态属性获取
//        //Class<? extends Student> cls1 =Student.class;
//        //通过类全路径名称获取
//        //Class cls2 =Class.forName("com.hanjinliang.androidstudy.javabase.reflect.Student");
//
//        //获取class的所有构造方法
//        Constructor<Student>[] constructors = (Constructor<Student>[]) cls.getConstructors();
//        for(Constructor<Student> constructor:constructors){
//            System.out.println(constructor);
//        }
//        //获取cls指定类型的构造函数   int类型必须是int.class Integer.class会报错
//        Constructor<? extends Student> constructor = cls.getConstructor(String.class,int.class);
//        System.out.println(constructor);
//        //通过构造器创建实例
//        Student student1 = constructor.newInstance("唐杰", 19);
//        student1.sayHello();
//
//        //获取class中的方法
//        Method[] methods = cls.getMethods();//获取class所有方法  不能获取私有方法 并且获取父类所有方法
//        for(Method method:methods){
//            System.out.println("method="+method+"()");
//        }
//        System.out.println("获取所有类声明的方法  包括私有方法");
//        Method  [] declaredMethods = cls.getDeclaredMethods();//获取所有类声明的方法  包括私有方法  没有父类
//        for(Method method:declaredMethods){
//            System.out.println("method="+method+"()");
//        }
//        //获取指定方法
//        Method setName = cls.getMethod("setName",String.class);
//        setName.setAccessible(true);//如果是私有方法必须设置
//        //执行方法   实例  参数
//        setName.invoke(student1,"我是反射设置的名字");
//        student1.sayHello();

    }




}
