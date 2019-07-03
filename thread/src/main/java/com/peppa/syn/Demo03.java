package com.peppa.syn;

public class Demo03 {

    public static void main(String[] args) {
        Demo03 demo03 = new Demo03();
        demo03.test();
    }

    public synchronized void test() {
        System.out.println("test");
    }
}
