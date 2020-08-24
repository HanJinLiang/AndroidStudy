package com.hanjinliang.androidstudy.javabase.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/20 11:18
 */
public class DynamicProxy {
    private static DynamicProxy dynamicProxy;
    public static DynamicProxy getInstance(){
        if(dynamicProxy==null){
            synchronized (DynamicProxy.class){
                if(dynamicProxy==null) {
                    dynamicProxy = new DynamicProxy();
                }
            }
        }
        return dynamicProxy;
    }
    public <T> Object proxy(T obj){
        return   Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                doSomethingBefore();
                System.out.println("method=="+method.getName());
                Object result = method.invoke(obj, args);
                doSomethingAfter();
                return result;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T proxy(final Class<T> printer){
        return (T) Proxy.newProxyInstance(printer.getClassLoader(),  new Class<?>[] { printer }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                doSomethingBefore();
                System.out.println("method=="+method.getName());
                //这里的printer必须是 实例对象
//                Object result = method.invoke(printer, args);
                doSomethingAfter();
                // return的就是方法返回值  代理方法返回类型是void的时候  可以直接返回return null
                if(method.getName().equals("getContent")){
                    return "我的动态代理返回的打印机内容";
                }
                return null;
            }
        });
    }

    private void doSomethingBefore(){
        System.out.println("打印前记录日志");
    }
    private void doSomethingAfter(){
        System.out.println("打印完成记录剩余纸张数量");
    }
}
