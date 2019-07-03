package com.peppa.syn;

public class Demo {
    public static void main(String[] args) {
        synchronized (Demo.class) {
            System.out.println("Demo main method");
        }
    }
}