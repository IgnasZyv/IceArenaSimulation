package com.cw1;

import java.util.concurrent.LinkedBlockingQueue;

public class OutletThread extends Thread {
    private final Outlet outlet;
    private final IceArena iceArena = IceArena.getInstance();
    private LinkedBlockingQueue<Order> waitingOrders;
    private final Object lock = new Object();

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
                    synchronized (iceArena) {
                        // while the order cannot be fulfilled, wait for the ice arena to complete an order
                        while (order.getStatus() != OrderStatus.Ready && !iceArena.completeOrder(order)) {
                            iceArena.wait();
                        }
                        if (outlet.getWaitingQueue().remove(order)) { // remove the order from the waiting queue
                            outlet.notifyOutlet();
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

