package com.cw1;

import java.util.concurrent.LinkedBlockingQueue;

public class IceArenaThread extends Thread {

    private final IceArena iceArena;
    private final LinkedBlockingQueue<Order> orderQueue;
    private final LinkedBlockingQueue<Visitor> visitorQueue;

    public IceArenaThread(IceArena iceArena, LinkedBlockingQueue<Order> orderQueue, LinkedBlockingQueue<Visitor> visitorQueue) {
        this.iceArena = iceArena;
        this.orderQueue = orderQueue;
        this.visitorQueue = visitorQueue;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
//            System.out.println("Thread: Checking the queue" + visitorQueue);
            synchronized (iceArena) {
                try {
                    Visitor visitor = visitorQueue.peek();
                    if (visitor != null) {
                        Order order = visitor.getOrder();
                        if (order != null && iceArena.canFulfillOrder(order)) {
                            System.out.println("Thread: Processing the order: " + order);
                            while (!iceArena.completeOrder(order)) {
                                System.out.println("Thread: Waiting for items to be returned");
                                iceArena.wait();
                            }
                            visitorQueue.take();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
