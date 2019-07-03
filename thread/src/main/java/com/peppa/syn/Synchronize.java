package com.peppa.syn;

public class Synchronize {
    public static void main(String[] args) {
        synchronized (Synchronize.class) {
            System.out.println("Synchronize main method");
        }
    }
}
