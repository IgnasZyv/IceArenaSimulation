package com.cw1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Outlet {

    private final IceArena iceArena;
    private final Outlet outlet = this;
    private final Semaphore semaphore = new Semaphore(1);
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final Object lock = new Object();
    private final LinkedList<Visitor> visitorQueue;
//private Queue<Order> visitorQueue = new LinkedList<>();
    private final LinkedBlockingQueue<Order> returnQueue;
    private final LinkedBlockingQueue<Order> waitingQueue;

    public Outlet(IceArena iceArena) {
        this.iceArena = iceArena;
        this.visitorQueue = new LinkedList<>();
        this.returnQueue = new LinkedBlockingQueue<>();
        this.waitingQueue = new LinkedBlockingQueue<>();
        OutletThread outletThread = new OutletThread(this);
        outletThread.start();
    }

    public synchronized void placeOrder(Order order) throws InterruptedException {
        order.getVisitor().setInQueue(true);
        boolean orderPlaced = false;
        if (!waitingQueue.contains(order)) {
            if (canFulfillOrder(order)) {
                System.out.println("Placing order: " + order.toString());
                iceArena.completeOrder(order);
                orderPlaced = true;
            }

            if (!orderPlaced) {
                System.out.println(order.getVisitor() + ": Cannot place order, waiting for items to be returned");
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
        }

    }

    public synchronized void returnItems(Order returnOrder) throws InterruptedException {
        System.out.println("Queueing items for return : " + returnOrder.getItemList() + " by " + returnOrder.getVisitor().getId());
        returnQueue.put(returnOrder);
    }

    public void notifyOutlet() {
        synchronized (outlet) {
            notifyAll();
        }
    }

//
//    public void queueOrder(Order order) throws InterruptedException {
//        synchronized (lock) {
//            while(!visitorQueue.isEmpty()) {
//                try {
//                    System.out.println(visitorQueue + "Waiting for order to be processed");
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            visitorQueue.add(order);
//            System.out.println("IceArena: Order " + order.getOrderNumber());
//            System.out.println("orders queued: " + visitorQueue);
//            lock.notifyAll();
//        }
//    }



//
//    public synchronized String processOrders() throws InterruptedException {
//        while (!visitorQueue.isEmpty() || !returnQueue.isEmpty()) {
//
//            Order order = visitorQueue.take();
//            System.out.println("Processing order: " + order);
//            iceArena.queueOrder(order);
//
//            if (iceArena.processOrders()) {
//                return "processed";
//            }
//
//            List<Item> returnedItems = returnQueue.poll();
//            if (returnedItems != null) {
//                System.out.println("Processing returned items: " + returnedItems);
//                if (iceArena.returnItems(returnedItems)) {
//                    return "returned";
//                }
//                System.out.println("Returned items processed: " + returnedItems);
//            }
////            iceArena.processOrders();
//        }
//        return null;
//    }

    public synchronized Boolean canFulfillOrder(Order order) {
        return iceArena.canFulfillOrder(order);
    }

    public synchronized Visitor getNextInQueue() {
        return visitorQueue.poll();
    }

    public synchronized void queueVisitor(Visitor visitor) {
        visitorQueue.add(visitor);
        QueuePanel.getInstance().updateQueue(visitorQueue);
    }

    public void processNextOrder() {
        try {
            semaphore.acquire();
            Visitor visitor = getNextInQueue();
            if (visitor != null) {
                Order order = visitor.getOrder();
                if (order != null) {
                    OrderProcessor processor = new OrderProcessor(order, iceArena, semaphore);
                    executorService.submit(processor);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }


    public void completeOrder(Order order) {
        try {
            iceArena.completeOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LinkedBlockingQueue<Order> getWaitingQueue() {
        return waitingQueue;
    }

    public IceArena getIceArena() {
        return iceArena;
    }
}
