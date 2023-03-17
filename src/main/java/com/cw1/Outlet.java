package com.cw1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Outlet {
    private final IceRink iceRink;
    private final Outlet outlet = this;

    private final LinkedBlockingQueue<Order> waitingQueue;

    public Outlet(IceRink iceRink) {
        this.iceRink = iceRink;
        LinkedList<Visitor> visitorQueue = new LinkedList<>();
        LinkedBlockingQueue<Order> returnQueue = new LinkedBlockingQueue<>();
        this.waitingQueue = new LinkedBlockingQueue<>();
    }

    public void placeOrder(Order order) throws InterruptedException {
        boolean orderPlaced = false;
        if (!waitingQueue.contains(order)) {
            if (canFulfillOrder(order)) {
                System.out.println("Placing order: " + order.toString());
                iceRink.completeOrder(order);
                orderPlaced = true;
            }

            if (!orderPlaced) {
                // 10% chance for not waiting in queue and just leaving
                if (Math.random() < 0.1) {
                    System.out.println(order.getVisitor() + ": Not waiting in queue, leaving");
                    QueuePanel.getInstance().removeVisitor(order.getVisitor());
                    return;
                }
                System.out.println(order.getVisitor() + ": Cannot place order, waiting for items to be returned");
                order.getVisitor().setInQueue(true); // set the visitor in queue
                waitingQueue.put(order); // add the order to the waiting queue
                HashMap<String, String> map = new HashMap<>();
                // print out all the orders in the queue
                for (Order ord : waitingQueue) {
                    map.put(ord.getVisitor().getId(), "#" + String.valueOf(ord.getOrderNumber()));
                }
                System.out.println("Waiting queue: " + map);

                synchronized (outlet) {
                    // while the order is still in the queue, wait for the outlet to notify
                    while (waitingQueue.contains(order)) {
                        outlet.wait();
                    }
                }

            }
        } else {
            if (order.getItemList() == null) {
                System.out.println("outlet: No items to order");
                order.getVisitor().setOrder(null);
                order.getVisitor().setInQueue(false);
                order.getVisitor().setBorrowedItems(null);
                return;
            }
        }

    }

    public void notifyOutlet() {
        synchronized (this) {
            notifyAll();
        }
    }

    public synchronized Boolean canFulfillOrder(Order order) {
        return iceRink.canFulfillOrder(order);
    }

    public LinkedBlockingQueue<Order> getWaitingQueue() {
        return waitingQueue;
    }

}
