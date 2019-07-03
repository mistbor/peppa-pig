package com.peppa.thread;

import java.util.Timer;
import java.util.TimerTask;

public class CreatThreadDemo05 {
    public static void main(String[] args) {
        Timer timer = new Timer();

        // 延迟0， 周期1秒
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时器线程执行了。。。。");
            }
        }, 0, 1000);

    }
}
