package com.cw1;

import com.cw1.enums.ItemType;
import com.cw1.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Visitor implements Runnable {
    private final String id;
    private final IceRink iceRink = IceRink.getInstance();
    private final SkatingArea skatingArea = SkatingArea.getInstance();
    private final DiningHall diningHall = DiningHall.getInstance();
    private final Outlet outlet = App.getOutlet();
    private List<Item> borrowedItems;
    private final List<Order> orders;
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
                // for safety in case of multiple orders
                if (!borrowedItems.isEmpty()) {
                    borrowedItems = new ArrayList<>();
                }

                if (!inQueue) { // if not in queue, place order
                    if (order == null) {
                        order = new Order(getItems(), this);
                    }
                    System.out.println(id + " is going to place order: " + order);
                    synchronized (outlet) { // synchronize on the outlet
                        try {
                            QueuePanel.getInstance().addQueue(this); // add the visitor to the queue panel
                            outlet.placeOrder(order);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // if the order is ready, borrow the items
                if (order.getStatus() == OrderStatus.Ready && !isSkating && !inQueue) {
                    System.out.println(id + " has received order: " + order);
                    System.out.println("borrowed Items: " + borrowedItems);
                    skatingArea.skate(this);
                    System.out.println(id + " has finished skating. Returning Items: " + borrowedItems);
                    try {
                        order.getVisitor().setInQueue(true);
                        order.setStatus(OrderStatus.Returning);
                        IceRinkPanel.getInstance().redraw();
                        iceRink.returnItems(order);
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
        List<ItemType> itemTypes = new ArrayList<>(ItemType.getItems()); // get all the item types
        List<Item> itemList = new ArrayList<>();
        List<Item> chosenItems = new ArrayList<>();

        for (ItemType itemType: itemTypes) {
            itemList.add(new Item(itemType));
        }

        Random random = new Random();

        for (int i = 0; i < itemList.size(); i++) {
            // always choose one item
            if (i == 0) {
                int index = random.nextInt(itemList.size());
                Item item = itemList.get(index);
                if (chosenItems.contains(item)) {
                    i--;
                } else {
                    chosenItems.add(item);
                }
            } else {
                // choose another item with 50% chance
                if (random.nextDouble() >  0.5) {
                    int index = random.nextInt(itemList.size());
                    Item item = itemList.get(index);
                    // if the item is already chosen, choose another one
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

    public void setIceArena(IceRink iceRink) {
        iceRink = iceRink;
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

    public synchronized void setOutlet(Outlet outlet) {
        outlet = outlet;
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
