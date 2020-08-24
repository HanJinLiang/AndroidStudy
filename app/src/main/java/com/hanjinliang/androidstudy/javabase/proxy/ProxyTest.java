package com.hanjinliang.androidstudy.javabase.proxy;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/20 11:10
 */
public class ProxyTest {

    public static void main(String[] arg){
        //静态代理
        PrinterProxy printerProxy=new PrinterProxy();
        printerProxy.print();
        System.out.println("--------------------------------------");
        //动态代理
        IPrinter proxy = (IPrinter) DynamicProxy.getInstance().proxy(new Printer());
        proxy.print();
        proxy.advert();

        //
        System.out.println("-----------------动态代理2---------------------");
        IPrinter proxy1 =  DynamicProxy.getInstance().proxy(IPrinter.class);
        proxy1.print();

        String content = proxy1.getContent();

        System.out.println("content=="+content);
    }
}
