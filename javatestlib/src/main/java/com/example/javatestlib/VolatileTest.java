package com.example.javatestlib;

import java.util.logging.Handler;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/4/7 17:34
 * https://github.com/Moosphan/Android-Daily-Interview/issues/55
 */
public class VolatileTest {
    static boolean tag = false;
// volatile static boolean tag = false;
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {//线程1
            @Override
            public void run() {
                while (!tag) {
                }
                System.out.println("thread2结束");
            }
        }).start();
        Thread.sleep(2000);
        new Thread(new Runnable() {//线程2
            @Override
            public void run() {
                tag = true;
                System.out.println("tag已经更改完成");
            }
        }).start();
    }
}
