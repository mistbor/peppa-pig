package com.peppa.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CachePool {
    public void test() {
        Executors.newCachedThreadPool();
    }
}
