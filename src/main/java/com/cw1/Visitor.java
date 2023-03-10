package com.cw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Visitor implements Runnable {
    private static final Object lock = new Object();
    private String id;
    private final IceArena iceArena = IceArena.getInstance();
    private final SkatingArea skatingArea = SkatingArea.getInstance();
    private final Outlet outlet = App.getOutlet();
    private List<Item> borrowedItems;
    private List<Order> orders;
    private Order order;
    private Semaphore semaphore;

    private Boolean isSkating = false;
    private Boolean inQueue = false;


    public Visitor(String id) {
        this.id = id;
        this.borrowedItems = new ArrayList<>();
        this.orders = new ArrayList<>();
        order = new Order(getItems(), this);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(id + "starting");
            if (borrowedItems.isEmpty() && !skatingArea.isSkating(this)) {
                if (order == null) {
                    order = new Order(getItems(), this);
                }
                System.out.println(id + " is going to place order: " + order);
                synchronized (outlet) {
                    try {
                        outlet.placeOrder(order);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            if (!borrowedItems.isEmpty() && !isSkating) {
                System.out.println(id + " has received order: " + orders.get(orders.size() - 1));
                System.out.println("borrowed Items: " + borrowedItems);
                skatingArea.skate(this);
                System.out.println(id + " has finished skating. Returning Items: " + borrowedItems);
                try {
                    iceArena.returnItems(orders.get(orders.size() - 1));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(1000);
//                if (borrowedItems.isEmpty() && skatingArea.isSkating(this)) {
//                    Order order = new Order(getItems(), this);
//                    System.out.println(id + " is going to the queue. " + order);
//                    currentOrder = order;
//                    iceArena.goToVisitorQueue(this);
//                    synchronized (iceArena) {
//                        while (order.getStatus() != OrderStatus.Ready) {
//                            iceArena.wait();
//                        }
//                    }
//                    if (!borrowedItems.isEmpty() && skatingArea.isSkating(this)) {
//                        System.out.println(id + " has received order: " + order);
//                        skatingArea.skate(this);
//                        System.out.println(id + " has finished skating. Returning Items: " + borrowedItems);
//                        iceArena.returnItems(order);
//                    }
//                    synchronized (iceArena) {
//                        while (order.getStatus() != OrderStatus.Completed) {
//                            iceArena.wait();
//                        }
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }



//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(1000);
//                if (borrowedItems.isEmpty() && !skatingArea.isSkating(this)) {
//                    Order order = new Order(getItems(), this);
//                    System.out.println(id + " is placing order: " + order);
//                    placeOrder(order);
//                    order.setStatus(OrderStatus.Waiting);
//
//                    while (order.getStatus() != OrderStatus.Ready) {
//                        Thread.sleep(1000);
//                    }
//
//                    if (!borrowedItems.isEmpty() && !skatingArea.isSkating(this)) {
//                        System.out.println(id + " has received order: " + order);
//                        skatingArea.skate(this);
//
//                        System.out.println(id + " has finished skating. Returning Items: " + borrowedItems);
//
//                        returnItems(order);
//                    }
//
//                    while (order.getStatus() != OrderStatus.Completed) {
//                        Thread.sleep(10000);
//                    }
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }


    public static List<Item> getItems() {
        List<ItemType> itemTypes = new ArrayList<>(ItemType.getItems());
        List<Item> itemList = new ArrayList<>();
        List<Item> chosenItems = new ArrayList<>();

        for (ItemType itemType: itemTypes) {
            itemList.add(new Item(itemType));
        }

        Random random = new Random();

        for (int i = 0; i < itemList.size(); i++) {
            if (i == 0) {
                int index = random.nextInt(itemList.size());
                Item item = itemList.get(index);
                if (chosenItems.contains(item)) {
                    i--;
                } else {
                    chosenItems.add(item);
                }
            } else {
                if (random.nextDouble() >  0.5) {
                    int index = random.nextInt(itemList.size());
                    Item item = itemList.get(index);
                    if (chosenItems.contains(item)) {
                        i--;
                    } else {
                        chosenItems.add(item);
                    }
                }
            }
        }
        return chosenItems;
    }


    public void waitUntilReady(Order order) throws InterruptedException {
        synchronized (iceArena) {
            while (order.getStatus() != OrderStatus.Ready) {
                wait();
            }
        }
    }

    public synchronized String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IceArena getIceArena() {
        return iceArena;
    }

    public void setIceArena(IceArena iceArena) {
        iceArena = iceArena;
    }

    public SkatingArea getSkatingArea() {
        return skatingArea;
    }

    public void setSkatingArea(SkatingArea skatingArea) {
        skatingArea = skatingArea;
    }

    public synchronized List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public synchronized void setBorrowedItems(List<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public synchronized Outlet getOutlet() {
        return outlet;
    }

    public synchronized void setOutlet(Outlet outlet) {
        outlet = outlet;
    }

    public synchronized List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public synchronized void addOrder(Order order) {
        orders.add(order);
    }

    public synchronized Order getOrder() {
        return order;
    }

    public synchronized void setOrder(Order order) {
        this.order = order;
    }

    public Boolean getSkating() {
        return isSkating;
    }

    public void setSkating(Boolean skating) {
        isSkating = skating;
    }

    public Boolean getInQueue() {
        return inQueue;
    }

    public void setInQueue(Boolean inQueue) {
        this.inQueue = inQueue;
    }

    @Override
    public String toString() {
        return id;
    }


}
