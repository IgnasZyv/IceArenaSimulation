package com.cw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Visitor implements Runnable {
    private static final Object lock = new Object();
    private String id;
    private final IceArena iceArena = IceArena.getInstance();
    private final SkatingArea skatingArea = SkatingArea.getInstance();
    private final DiningHall diningHall = DiningHall.getInstance(App.DINING_HALL_CAPACITY);
    private final Outlet outlet = App.getOutlet();
    private List<Item> borrowedItems;
    private List<Order> orders;
    private Order order;

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
                if (!borrowedItems.isEmpty()) {
                    borrowedItems = new ArrayList<>();
                }

                if (!inQueue) {
                    if (order == null) {
                        order = new Order(getItems(), this);
                    }
                    System.out.println(id + " is going to place order: " + order);
                    synchronized (outlet) {
                        try {
                            QueuePanel.getInstance().addQueue(this);
                            outlet.placeOrder(order);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (!borrowedItems.isEmpty() && !isSkating && !inQueue) {
                    System.out.println(id + " has received order: " + order);
                    System.out.println("borrowed Items: " + borrowedItems);
                    skatingArea.skate(this);
                    System.out.println(id + " has finished skating. Returning Items: " + borrowedItems);
                    try {
                        while (!outlet.returnItems(order)) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (borrowedItems.isEmpty() && !inQueue) {
                    System.out.println(id + " is going to eat");
                    try {
                        diningHall.enter(this);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(id + " has finished eating");
                }
            }
        }

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
