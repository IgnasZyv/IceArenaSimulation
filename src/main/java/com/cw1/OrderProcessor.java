package com.cw1;

import java.util.concurrent.Semaphore;

public class OrderProcessor implements Runnable {
    private final Order order;
    private final IceArena iceArena;
    private final Semaphore semaphore;

    public OrderProcessor(Order order, IceArena iceArena, Semaphore semaphore) {
        this.order = order;
        this.iceArena = iceArena;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            // Process the order
            while (!iceArena.completeOrder(order)){
                System.out.println("Thread: Waiting for items to be returned");
                iceArena.wait();
            }

            // Release the semaphore
            semaphore.release();

            synchronized (order.getVisitor()) {
                order.getVisitor().notify();
            }
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
