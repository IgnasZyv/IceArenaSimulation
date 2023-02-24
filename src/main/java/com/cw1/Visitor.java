package com.cw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Visitor implements Runnable {
    private String id;
    private IceArena iceArena;
    private Outlet outlet;
    private List<Item> borrowedItems;
    private List<Order> orders;

    public Visitor(String id) {
        this.id = id;
        this.borrowedItems = new ArrayList<>();
        this.orders = new ArrayList<>();
    }


    @Override
    public void run() {
        while (true) {
            if (borrowedItems.size() > 0)
                returnItems(borrowedItems);

            placeOrder(new Order(getItems(), this));
            processOrders();
//            returnItems(borrowedItems);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IceArena getIceArena() {
        return iceArena;
    }

    public void setIceArena(IceArena iceArena) {
        this.iceArena = iceArena;
    }

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public void setBorrowedItems(List<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public void placeOrder(Order order) {
        outlet.placeOrder(order);
    }

    public void returnItems(List<Item> items) {
        outlet.returnItems(items);
    }

    public void processOrders() {
        outlet.processOrders();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
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

    @Override
    public String toString() {
        return id;
    }

}
