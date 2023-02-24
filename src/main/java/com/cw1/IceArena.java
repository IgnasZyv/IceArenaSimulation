package com.cw1;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class IceArena {
    private static IceArena instance = null;
//    private final Outlet outlet;
    private Visitor visitor;
    private List<Item> skates;
    private List<Item> helmets;
    private List<Item> gloves;
    private List<Item> penguins;
    private List<List<Item>> borrowedItems;
    private int maxItems;

    private LinkedBlockingQueue<Order> orderQueue;

    private IceArena(int maxItems) {
        this.orderQueue = new LinkedBlockingQueue<>();
        this.skates = new ArrayList<>();
        this.helmets = new ArrayList<>();
        this.gloves = new ArrayList<>();
        this.penguins = new ArrayList<>();
        this.borrowedItems = new ArrayList<>();
        this.maxItems = maxItems;

        for (int i = 0; i < maxItems; i++) {
            skates.add(new Item(ItemType.SKATE));
            helmets.add(new Item(ItemType.HELMET));
            gloves.add(new Item(ItemType.GLOVES));
            penguins.add(new Item(ItemType.PENGUIN));
        }
    }

    public static synchronized IceArena getInstance(int maxItems) {
        if (instance == null) {
            instance = new IceArena(maxItems);
        }
        return instance;
    }

    public synchronized boolean returnItems(List<Item> items) {

        List<ItemType> itemTypes = new ArrayList<>();
        for (Item item : items) {
            switch (item.getType()) {
                case SKATE:
                    if (skates.size() <= maxItems) {
                        skates.add(item);
                        itemTypes.add(item.getType());
                    }
                    break;
                case GLOVES:
                    if (gloves.size() <= maxItems) {
                        gloves.add(item);
                        itemTypes.add(item.getType());
                    }
                    break;
                case HELMET:
                    if (helmets.size() <= maxItems) {
                        helmets.add(item);
                        itemTypes.add(item.getType());
                    }
                    break;
                case PENGUIN:
                    if (penguins.size() <= maxItems) {
                        penguins.add(item);
                        itemTypes.add(item.getType());
                    }
                    break;
                default:
                    System.out.println("addItems: " + "failed to add " + item);
                    return false;
            }
        }
        System.out.println("Added " + itemTypes);
        return true;
    }

    public synchronized Boolean queueOrder(Order order) {
        try {
            orderQueue.put(order);
            System.out.println("Order " + order.getOrderNumber() + " queued.");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void processOrders() {
        System.out.println("Processing orders");
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            System.out.println("finalizing order: " + order);
            List<Item> items = order.getItemList();
            List<Item> borrowed = new ArrayList<>();
            for (Item item : items) {
                switch (item.getType()) {
                    case SKATE:
                        if (skates.size() > 0) {
                            borrowed.add(skates.remove(0));
                        }
                        break;
                    case GLOVES:
                        if (gloves.size() > 0) {
                            borrowed.add(gloves.remove(0));
                        }
                        break;
                    case HELMET:
                        if (helmets.size() > 0) {
                            borrowed.add(helmets.remove(0));
                        }
                        break;
                    case PENGUIN:
                        if (penguins.size() > 0) {
                            borrowed.add(penguins.remove(0));
                        }
                        break;
                    default:
                        System.out.println("processOrders: " + "failed to borrow " + item);
                }
            }
            System.out.println("Borrowed " + borrowed);
            if (borrowed.size() == items.size()) {
                borrowedItems.add(borrowed);
                System.out.println("Order " + order.getOrderNumber() + " fulfilled.");
            } else {
                System.out.println("Order " + order.getOrderNumber() + " failed.");
            }
        }
    }
//
//    public Outlet getOutlet() {
//        return outlet;
//    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public LinkedBlockingQueue<Order> getOrderQueue() {
        return orderQueue;
    }
}
