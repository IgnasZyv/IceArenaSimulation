package com.cw1;

public class Counter implements Runnable {
    private int count;

    public Counter() {
        count = 0;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            count++;
            System.out.println("Thread " + Thread.currentThread().threadId() + " counted to " + count);
        }
    }
}
