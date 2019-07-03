package com.peppa.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreatThreadDemo06 {

    /**
     * 输出结果：
     * <p>
     * 多线程用时0
     * pool-1-thread-1线程执行了......
     * pool-1-thread-2线程执行了......
     * pool-1-thread-3线程执行了......
     * pool-1-thread-4线程执行了......
     * pool-1-thread-5线程执行了......
     * pool-1-thread-7线程执行了......
     * pool-1-thread-6线程执行了......
     * pool-1-thread-8线程执行了......
     * pool-1-thread-10线程执行了......
     * pool-1-thread-9线程执行了......
     *
     * @param args
     */
    public static void main(String[] args) {

        // ThreadPoolExecutor
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        long threadPoolUseTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "线程执行了......");
                }
            });
        }
        long threadPoolUseTime1 = System.currentTimeMillis();
        System.out.println("多线程用时" + (threadPoolUseTime1 - threadPoolUseTime));
        // 销毁线程池
        threadPool.shutdown();
        threadPoolUseTime = System.currentTimeMillis();
    }
}