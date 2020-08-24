package com.hanjinliang.androidstudy.javabase.proxy;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/20 11:07
 */
public class Printer implements IPrinter {
    @Override
    public void print() {
        System.out.println("我打印出来了");
    }

    @Override
    public void advert() {
        System.out.println("我还可以做广告哟");
    }

    @Override
    public String getContent() {
        return "我是打印机的内容";
    }
}
