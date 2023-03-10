package com.cw1;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class IceArena {
    private static IceArena instance = null;
    private Visitor visitor;
    AtomicReference<ArrayList<Item>> skates = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> helmets = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> gloves = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> penguins = new AtomicReference<>(new ArrayList<>());

    private final LinkedBlockingQueue<Visitor> visitorQueue;
    private final LinkedBlockingQueue<Order> orderQueue;

    private IceArena() {
        this.orderQueue = new LinkedBlockingQueue<>();
        this.visitorQueue = new LinkedBlockingQueue<>();
        List<List<Item>> borrowedItems = new ArrayList<>();
        int maxItems = 1; // set max items

        ArrayList<Item> skateItems = new ArrayList<>();
        ArrayList<Item> helmetItems = new ArrayList<>();
        ArrayList<Item> gloveItems = new ArrayList<>();
        ArrayList<Item> penguinItems = new ArrayList<>();
        for (int i = 0; i < maxItems; i++) {
            skateItems.add(new Item(ItemType.SKATES));
            helmetItems.add(new Item(ItemType.HELMET));
            gloveItems.add(new Item(ItemType.GLOVES));
            penguinItems.add(new Item(ItemType.PENGUIN));
        }
        skates.set(skateItems);
        helmets.set(helmetItems);
        gloves.set(gloveItems);
        penguins.set(penguinItems);

//        IceArenaThread iceArenaThread = new IceArenaThread(this, orderQueue, visitorQueue);
//        iceArenaThread.start();
    }

    public static synchronized IceArena getInstance() {
        if (instance == null) {
            instance = new IceArena();
        }
        return instance;
    }

    public synchronized void returnItems(Order order) throws InterruptedException {
        List<ItemType> itemTypes = new ArrayList<>();
        for (Item item : order.getItemList()) {

            switch (item.getType()) {
                case SKATES -> {
                    skates.get().add(item);
                    itemTypes.add(item.getType());
                }
                case GLOVES -> {
                    gloves.get().add(item);
                    itemTypes.add(item.getType());
                }
                case HELMET -> {
                    helmets.get().add(item);
                    itemTypes.add(item.getType());
                }
                case PENGUIN -> {
                    penguins.get().add(item);
                    itemTypes.add(item.getType());
                }
                default -> System.out.println("IceArena: addItems: " + "failed to add " + item);
            }
        }
        StatisticsPanel.updateItems(getInventory());
        System.out.println("IceArena: Added to storage" + itemTypes);
        System.out.println(getInventoryString());
        order.getVisitor().getBorrowedItems().clear();
        order.setStatus(OrderStatus.Completed);
//        order.getVisitor().addOrder(order);
        order.notifyReady();
        App.getOutlet().notifyOutlet();
        notifyAll();
//        Thread.sleep(5000);
    }

    public synchronized Boolean queueOrder(Order order) {
        try {
            Thread.sleep(new Random().nextInt(3000, 6000));
            orderQueue.put(order);
            System.out.println("IceArena: Order " + order.getOrderNumber() + " queued.");
            System.out.println("orders queued: " + orderQueue.toString());
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized Boolean canFulfillOrder(Order order) {
        List<Item> items = order.getItemList();
        boolean canFulfill = true;
        for (Item item : items) {
            switch (item.getType()) {
                case SKATES -> {
                    if (skates.get().size() == 0) {
                        canFulfill = false;
                    }
                }
                case GLOVES -> {
                    if (gloves.get().size() == 0) {
                        canFulfill = false;
                    }
                }
                case HELMET -> {
                    if (helmets.get().size() == 0) {
                        canFulfill = false;
                    }
                }
                case PENGUIN -> {
                    if (penguins.get().size() == 0) {
                        canFulfill = false;
                    }
                }
            }
        }
        return canFulfill;
    }

    public synchronized Boolean completeOrder(Order order) throws InterruptedException {
        if (canFulfillOrder(order)) {
            List<Item> borrowedItems = borrowItems(order);

            Visitor visitor = order.getVisitor();
            visitor.setBorrowedItems(borrowedItems);
            // Add the order to the visitor's list of orders
            order.setStatus(OrderStatus.Ready);
            order.getVisitor().setInQueue(false);
            visitor.addOrder(order);
            visitor.setOrder(null);
            System.out.println(getInventoryString());
            System.out.println(order + " fulfilled.");
            StatisticsPanel.updateItems(getInventory());
            notifyAll();
            Thread.sleep(2000);
            return true;
        }
        return false;
    }

    public void placeOrder(Order order) throws InterruptedException {
        order.setStatus(OrderStatus.Waiting);
        orderQueue.put(order); // add the order to the blocking queue
        synchronized (this) {
            while (order.getStatus() != OrderStatus.Ready) {
                while (order.getStatus() == OrderStatus.Waiting) {
                    System.out.println("Waiting for order " + order.getOrderNumber() + " to be processed.");
                    processOrders();
                    wait(); // wait until the order is ready
                }
            }
        }

        // TODO: Change the thread to sort the waiting orders and process send one order at a time for processing.

//            notifyAll();
//            notifyVisitors();
        }


    public void processOrders() throws InterruptedException {
        System.out.println("Processing orders...");
            synchronized (this) {
                // Wait until there is at least one order to process
                while (orderQueue.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Process the first order in the queue
                Order order = orderQueue.poll();
                System.out.println("Processing order: " + order);
                order.setStatus(OrderStatus.Processing);
                List<Item> items = order.getItemList();
                // TODO: Check if the items are available
                // TODO: if not, wait until they are will notified by the returnItems method

                while (!canFulfillOrder(order)) {
                    try {
                        System.out.println("Waiting for items to be returned...");
                        order.waitUntilCanFulfill();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                List<Item> borroweditems = borrowItems(order);

                // Wait for the order to be ready
                while (order.getStatus() != OrderStatus.Ready) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Mark the order as ready and notify the visitor who placed the order
                order.getVisitor().setBorrowedItems(borroweditems);
                order.setStatus(OrderStatus.Ready);
                notifyAll();
        }
    }

    public synchronized List<Item> borrowItems(Order order) throws InterruptedException {
        List<Item> borrowed = new ArrayList<>();
        for (Item item : order.getItemList()) {
            Thread.sleep(3000);
            switch (item.getType()) {
                case SKATES -> {
                    if (skates.get().size() > 0) {
                        borrowed.add(skates.get().remove(0));
//                        System.out.println("Borrowed " + item.getType() + " left: " + skates.size() + " " + order);
                    }
                }
                case GLOVES -> {
                    if (gloves.get().size() > 0) {
                        borrowed.add(gloves.get().remove(0));
//                        System.out.println("Borrowed " + item.getType() + " left: " + gloves.size() + " " + order);
                    }
                }
                case HELMET -> {
                    if (helmets.get().size() > 0) {
                        borrowed.add(helmets.get().remove(0));
//                        System.out.println("Borrowed " + item.getType() + " left: " + helmets.size() + " " + order);
                    }
                }
                case PENGUIN -> {
                    if (penguins.get().size() > 0) {
                        borrowed.add(penguins.get().remove(0));
//                        System.out.println("Borrowed " + item.getType() + " left: " + penguins.size() + " " + order);
                    }
                }
            }
        }
        System.out.println("Borrowed " + borrowed.toString() + " for " + order);
        order.getVisitor().setBorrowedItems(borrowed);
        order.setStatus(OrderStatus.Ready);
        StatisticsPanel.updateItems(getInventory());
        return borrowed;
    }

    public synchronized void goToVisitorQueue(Visitor visitor) {
        System.out.println(visitor.getId() + " is queuing up");
        visitorQueue.add(visitor);
        System.out.println(visitorQueue);

    }

    public synchronized void notifyVisitors() {
        this.notifyAll();
    }

    public synchronized List<Item> getSkates() {
        return skates.get();
    }

    public synchronized List<Item> getHelmets() {
        return helmets.get();
    }
    public synchronized List<Item> getGloves() {
        return gloves.get();
    }

    public synchronized List<Item> getPenguins() {
        return penguins.get();
    }

    public LinkedBlockingQueue<Visitor> getVisitorQueue() {
        return visitorQueue;
    }

    public synchronized HashMap<ItemType, Integer> getInventory() {
        HashMap<ItemType, Integer> inventory = new HashMap<>();
        inventory.put(ItemType.SKATES, skates.get().size());
        inventory.put(ItemType.GLOVES, gloves.get().size());
        inventory.put(ItemType.HELMET, helmets.get().size());
        inventory.put(ItemType.PENGUIN, penguins.get().size());

        return inventory;
    }

    public synchronized  String getInventoryString() {
        return "Inventory: " +
                "Skates: " + skates.get().size() +
                ", Helmets: " + helmets.get().size() +
                ", Gloves: " + gloves.get().size() +
                ", Penguins: " + penguins.get().size();
    }

}
