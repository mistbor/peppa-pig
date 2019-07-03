package com.peppa.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreatThreadDemo04 implements Callable {

    /**
     * 结果输出：
     * <p>
     * 可以在此做点别的任务，因为futureTask是提前完成任务
     * 业务逻辑计算中.....
     * 线程中运算得结果为： 1
     *
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CreatThreadDemo04 demo04 = new CreatThreadDemo04();

        // FutureTask最终实现的是runnable接口
        FutureTask<Integer> task = new FutureTask<Integer>(demo04);

        Thread thread = new Thread(task);

        thread.start();

        System.out.println("可以在此做点别的任务，因为futureTask是提前完成任务");

        // 拿出线程执行得返回值
        Integer result = task.get();
        System.out.println("线程中运算得结果为： " + result);
    }

    /**
     * 重写callable接口的call方法
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        int result = 1;
        System.out.println("业务逻辑计算中.....");
        Thread.sleep(3000);
        return result;
    }
}
