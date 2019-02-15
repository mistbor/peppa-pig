package com.peppa.pig.utils;

import java.util.concurrent.LinkedBlockingDeque;

public class BuyQueue<T> extends LinkedBlockingDeque<T> {
    public BuyQueue(int capacity) {
        super(capacity);
    }
}
