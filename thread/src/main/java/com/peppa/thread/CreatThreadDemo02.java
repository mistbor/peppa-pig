package com.peppa.thread;

/**
 * 实现runnable接口，作为线程任务存在
 * runnable只是来修饰线程所执行的任务，它不是一个线程对象，想要启动runnable对象，必须将它放到一个线程对象里
 */
public class CreatThreadDemo02 implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("thread is running.....");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new CreatThreadDemo02());
        thread.start();
    }
}
