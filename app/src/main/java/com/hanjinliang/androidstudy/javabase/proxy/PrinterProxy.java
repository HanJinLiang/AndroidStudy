package com.hanjinliang.androidstudy.javabase.proxy;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/20 11:08
 */
public class PrinterProxy implements IPrinter {
    Printer printer;
    public PrinterProxy() {
        printer=new Printer();
    }

    @Override
    public void print() {
        doSomethingBefore();
        printer.print();
        doSomethingAfter();
    }

    @Override
    public void advert() {
        doSomethingBefore();
        printer.advert();
        doSomethingAfter();
    }

    @Override
    public String getContent() {
      return   printer.getContent();
    }

    private void doSomethingBefore(){
        System.out.println("打印前记录日志");
    }
    private void doSomethingAfter(){
        System.out.println("打印完成记录剩余纸张数量");
    }
}
