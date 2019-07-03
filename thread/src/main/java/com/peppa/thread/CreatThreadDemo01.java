package com.peppa.thread;

/**
 * 继承Thread类，作为线程对象存在
 */
public class CreatThreadDemo01 extends Thread {

    public CreatThreadDemo01(String name) {
        super(name);
    }


    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println(getName() + "thread is running....");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CreatThreadDemo01 d1 = new CreatThreadDemo01("first");
        CreatThreadDemo01 d2 = new CreatThreadDemo01("second");
        d1.start();
        d2.start();
        d1.interrupt();// 中断第一个线程
    }

}
