package com.cw1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OutletThread extends Thread {
    private final Outlet outlet;
    private final IceArena iceArena = IceArena.getInstance();
    private LinkedBlockingQueue<Order> waitingOrders;
    private static final Lock lock = new ReentrantLock();
    private static final Condition storageUpdated = lock.newCondition();

    public OutletThread(Outlet outlet) {
        this.outlet = outlet;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            waitingOrders = outlet.getWaitingQueue();
            Order order = orderThatCanBeFulfilled();
            if (order != null) {
                System.out.println("Processing the waiting order: " + order.getOrderNumber());
                try {
                    synchronized (lock) {
                        // while the order cannot be fulfilled, wait for the ice arena to complete an order
                        while (order.getStatus() != OrderStatus.Ready && !iceArena.completeOrder(order)) {
                            lock.wait();
                        }
                        if (order.getStatus() == OrderStatus.Ready) {
                            if (outlet.getWaitingQueue().remove(order)) { // remove the order from the waiting queue
                                System.out.println("Order " + order.getOrderNumber() + " is ready");
                                synchronized (outlet) {
                                    outlet.notifyAll(); // notify the outlet that the order is ready
                                }
                            }
                        }

                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // if there are no orders that can be fulfilled, wait for the ice arena to complete an order
                try {
                    synchronized (iceArena) {
                        System.out.println("No orders can be fulfilled. Waiting for ice arena to complete an order");
                        iceArena.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private Order orderThatCanBeFulfilled() {
        for (Order order : waitingOrders) {
            if (iceArena.canFulfillOrder(order)) {
                return order;
            }
        }
        return null;
    }
}

