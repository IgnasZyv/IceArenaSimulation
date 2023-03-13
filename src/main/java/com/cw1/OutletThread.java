package com.cw1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OutletThread extends Thread {
    private static OutletThread instance;
    private final Outlet outlet;
    private final IceArena iceArena = IceArena.getInstance();
    private LinkedBlockingQueue<Order> waitingOrders;
    public static final Lock lock = new ReentrantLock();
    private static final Condition storageUpdated = lock.newCondition();

    private OutletThread(Outlet outlet) {
        this.outlet = outlet;
    }

    public static OutletThread getInstance(Outlet outlet) {
        if (instance == null) {
            instance = new OutletThread(outlet);
        }
        return instance;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            waitingOrders = outlet.getWaitingQueue(); // get the waiting queue from the outlet
            Order order = orderThatCanBeFulfilled(); // get the first order that can be fulfilled from the queue

            if (order != null) {
                System.out.println("Processing the waiting order: " + order.getOrderNumber());
                synchronized (outlet) { // synchronize on the outlet
                    try {
                        // if the order cannot be fulfilled, wait for the outlet to be notified that the storage has been updated
                        if (!iceArena.completeOrder(order)) {
                            System.out.println("Can't fulfill order " + order.getOrderNumber() + ". Waiting for ice arena to complete an order");
                            outlet.wait();
                        }
                        // if the order can be fulfilled, remove the order from the waiting queue and notify the outlet
                        if (order.getStatus() == OrderStatus.Ready) {
                            if (outlet.getWaitingQueue().remove(order)) { // remove the order from the waiting queue
                                System.out.println("Order " + order.getOrderNumber() + " is ready");
                                outlet.notifyOutlet(); // notify the outlet that the order is ready
                                QueuePanel.getInstance().redraw(); // redraw the ice arena queue panel
                            }
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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

    public void notifyStorageUpdated() {
        if (orderThatCanBeFulfilled() != null) {
            synchronized (outlet) {
                System.out.println("Notifying outlet that storage has been updated");
                outlet.notifyAll();
            }

        }
    }


}

