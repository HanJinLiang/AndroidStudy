package com.example.javatestlib;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/24 11:37
 */
public class ThreadTest {
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) { countA(); }
            }
        }, "线程A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) { countA(); }
            }
        }, "线程B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) { countA(); }
            }
        }, "线程C").start();
    }

    private int countA = 0;
    private int countB = 0;
    //累加方法A加锁，防止其他线程篡改结果
    private synchronized  void countA() {
        try {
            countA++;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "--" + countA);
    }
    //累加方法B加锁，防止其他线程篡改结果
    private synchronized void countB() {
        try {
            countB++;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "--" + countB);
    }

}
