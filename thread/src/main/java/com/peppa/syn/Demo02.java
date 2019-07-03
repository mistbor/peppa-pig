package com.peppa.syn;

public class Demo02 {

    public static void main(String[] args) {
        test();
    }

    public synchronized static void test() {
        System.out.println("test");
    }
}
