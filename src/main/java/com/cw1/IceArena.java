package com.cw1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class IceArena {
    private static IceArena instance = null;
    private final Object lock = new Object();
    private Visitor visitor;
    AtomicReference<ArrayList<Item>> skates = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> helmets = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> gloves = new AtomicReference<>(new ArrayList<>());
    AtomicReference<ArrayList<Item>> penguins = new AtomicReference<>(new ArrayList<>());

    private IceArena() {

        List<List<Item>> borrowedItems = new ArrayList<>();
        int maxItems = 5; // set max items

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

    }

    public static synchronized IceArena getInstance() {
        if (instance == null) {
            instance = new IceArena();
        }
        return instance;
    }

    public Boolean returnItems(Order order) throws InterruptedException {
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
            StatisticsPanel.updateItems(getInventory()); // update inventory gui
            System.out.println("IceArena: Added to storage" + itemTypes);
            System.out.println(getInventoryString());
            order.getVisitor().getBorrowedItems().clear(); // clear visitors borrowed items
            order.getVisitor().setInQueue(false); // remove visitor from queue
            order.setStatus(OrderStatus.Completed);

            SkatingArea.getSkaters().remove(order.getVisitor()); // remove visitor from skating area gui
            StatisticsPanel.updateSkatingVisitors(SkatingArea.getSkaters().size()); // update the statistics panel for skating visitor count
            IceArenaPanel.getInstance().updateSkatingVisitors(SkatingArea.getSkaters()); // update skating area gui

            order.getVisitor().addOrder(order); // add order to the list of visitors orders
            order.getVisitor().setOrder(null); // set the current order to null so the visitor can borrow again

            synchronized (this) { // notify all threads waiting on the ice arena object that items have been returned
                notifyAll();
            }

            // notify the outlet that the storage has been updated
            OutletThread.getInstance(App.getOutlet()).notifyStorageUpdated();

        Thread.sleep(new Random().nextInt(1000, 2000));
        return true;
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
        if (canFulfillOrder(order)) { // check if items are available
            List<Item> borrowedItems = borrowItems(order); // borrow items

            Visitor visitor = order.getVisitor();
            visitor.setBorrowedItems(borrowedItems);
            order.setStatus(OrderStatus.Ready);
            order.getVisitor().setInQueue(false);

            System.out.println(getInventoryString());
            System.out.println(order + " fulfilled.");
            StatisticsPanel.updateItems(getInventory()); // update inventory gui
            notifyAll();
            Thread.sleep(new Random().nextInt(1000, 2000));
            return true;
        }
        return false;
    }

    public synchronized List<Item> borrowItems(Order order) throws InterruptedException {
        List<Item> borrowed = new ArrayList<>();
        for (Item item : order.getItemList()) {
            switch (item.getType()) {
                case SKATES -> {
                    if (skates.get().size() > 0) {
                        borrowed.add(skates.get().remove(0));
                    }
                }
                case GLOVES -> {
                    if (gloves.get().size() > 0) {
                        borrowed.add(gloves.get().remove(0));
                    }
                }
                case HELMET -> {
                    if (helmets.get().size() > 0) {
                        borrowed.add(helmets.get().remove(0));
                    }
                }
                case PENGUIN -> {
                    if (penguins.get().size() > 0) {
                        borrowed.add(penguins.get().remove(0));
                    }
                }
            }
        }
        System.out.println("Borrowed " + borrowed.toString() + " for " + order);
        order.getVisitor().setBorrowedItems(borrowed); // set borrowed items for visitor
        order.setStatus(OrderStatus.Ready); // set order status to ready
        StatisticsPanel.updateItems(getInventory()); // update inventory gui
        Thread.sleep(new Random().nextInt(1000, 2000));
        return borrowed;
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
