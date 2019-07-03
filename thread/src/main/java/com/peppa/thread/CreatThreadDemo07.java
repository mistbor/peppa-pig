package com.peppa.thread;

import java.util.Arrays;
import java.util.List;

public class CreatThreadDemo07 {
    /**
     * 输出:
     * <p>
     * 104
     * 30
     * 10
     * 20
     * 40
     * <p>
     * 利用Java8新特性stream实现并发
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(10, 20, 30, 40);
        int result = values.parallelStream().mapToInt(p -> p + 1).sum();
        System.out.println(result);
        values.parallelStream().forEach(p -> System.out.println(p));

    }
}