package com.cw1;

import com.cw1.enums.OrderStatus;

import java.util.concurrent.LinkedBlockingQueue;

public class OutletThread extends Thread {
    private static OutletThread instance;
    private final Outlet outlet;
    private final IceRink iceRink = IceRink.getInstance();
    private LinkedBlockingQueue<Order> waitingOrders;

    private OutletThread() {
        this.outlet = App.getOutlet();
    }

    public static OutletThread getInstance() {
        if (instance == null) {
            instance = new OutletThread();
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
                        if (!iceRink.completeOrder(order)) {
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
                    synchronized (outlet) {
                        System.out.println("No orders can be fulfilled. Waiting for ice arena to complete an order");
                        outlet.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private Order orderThatCanBeFulfilled() {
        if (waitingOrders == null) {
            return null;
        }
        // find an order that can be fulfilled
        for (Order order : waitingOrders) {
            if (iceRink.canFulfillOrder(order)) {
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

