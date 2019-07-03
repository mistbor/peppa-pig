package com.peppa.thread;

/**
 * 匿名内部类创建线程对象
 */
public class CreatThreadDemo03 extends Thread {

    /**
     * 执行结果：
     * <p>
     * 创建无参线程对象 thread is running.....
     * 创建带线程任务得线程对象 thread is running.....
     * 创建带线程任务并且重写run方法得线程对象2 override run thread is running......
     * <p>
     **/
    public static void main(String[] args) {
        // 创建无参线程对象
        new Thread() {
            @Override
            public void run() {
                System.out.println("创建无参线程对象 thread is running.....");
            }
        }.start();

        // 创建带线程任务得线程对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("创建带线程任务得线程对象 thread is running.....");
            }
        }).start();

        // 创建带线程任务并且重写run方法得线程对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("创建带线程任务并且重写run方法得线程对象1 runnable run thread is running......");
            }
        }) {
            @Override
            public void run() {
                System.out.println("创建带线程任务并且重写run方法得线程对象2 override run thread is running......");
            }
        }.start();
    }
}