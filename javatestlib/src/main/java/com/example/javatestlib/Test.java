package com.example.javatestlib;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2021/4/7 10:32
 */
public class Test {
    public static class MyThread extends Thread {
        @Override
        public void run() {

            try {
                int m = (int) (Math.random() * 10000);
                System.out.println("我在子线程中会随机睡上0-9秒，时间为="+m);
                Thread.sleep(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread =new MyThread();
        myThread.start();
        myThread.join();
        System.out.println("正常情况下肯定是我先执行完，但是加入join后，main主线程会等待子线程执行完毕后才执行");
    }


}
