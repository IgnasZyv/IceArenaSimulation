package com.cw1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Outlet {
    private final IceArena iceArena;
    private final Object lock = new Object();
    private final Outlet outlet = this;
    private final Semaphore semaphore = new Semaphore(1);
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final LinkedBlockingQueue<Order> waitingQueue;

    public Outlet(IceArena iceArena) {
        this.iceArena = iceArena;
        LinkedList<Visitor> visitorQueue = new LinkedList<>();
        //private Queue<Order> visitorQueue = new LinkedList<>();
        LinkedBlockingQueue<Order> returnQueue = new LinkedBlockingQueue<>();
        this.waitingQueue = new LinkedBlockingQueue<>();
        OutletThread outletThread = OutletThread.getInstance(outlet);
        outletThread.start();
    }

    public synchronized void placeOrder(Order order) throws InterruptedException {
//        order.getVisitor().setInQueue(true);

        boolean orderPlaced = false;
        if (!waitingQueue.contains(order)) {
            if (canFulfillOrder(order)) {
                System.out.println("Placing order: " + order.toString());
                iceArena.completeOrder(order);
                orderPlaced = true;
            }

            if (!orderPlaced) {
                System.out.println(order.getVisitor() + ": Cannot place order, waiting for items to be returned");
                order.getVisitor().setInQueue(true);
                waitingQueue.put(order);
                HashMap<String, String> map = new HashMap<>();
                for (Order ord : waitingQueue) {
                    map.put(ord.getVisitor().getId(), "#" + String.valueOf(ord.getOrderNumber()));
                }
                System.out.println("Waiting queue: " + map);
                while (waitingQueue.contains(order)) {
                    wait();
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

    public synchronized boolean returnItems(Order returnOrder) throws InterruptedException {
        System.out.println("outlet: Items for return: " + returnOrder.getItemList() + " from visitor: " + returnOrder.getVisitor().getId());
        returnOrder.getVisitor().setInQueue(true);
        returnOrder.setStatus(OrderStatus.Returning);
        IceArenaPanel.getInstance().redraw();
        if (iceArena.returnItems(returnOrder)) {
            System.out.println("outlet: Items returned to ice arena");
            return true;
        }
        return false;
    }

    public void notifyOutlet() {
        synchronized (lock) {
            synchronized (this) {
                notifyAll();
            }
        }

    }

    public synchronized Boolean canFulfillOrder(Order order) {
        return iceArena.canFulfillOrder(order);
    }

    public LinkedBlockingQueue<Order> getWaitingQueue() {
        return waitingQueue;
    }

}
