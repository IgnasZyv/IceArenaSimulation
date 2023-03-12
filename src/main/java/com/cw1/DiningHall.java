package com.cw1;

import java.util.ArrayList;
import java.util.List;

public class DiningHall {
    private static DiningHall instance = null;
    private final Object lock = new Object();
    private final List<Visitor> visitors;

    private DiningHall() {
        this.visitors = new ArrayList<>();
    }

    public static synchronized DiningHall getInstance() {
        if (instance == null) {
            instance = new DiningHall();
        }
        return instance;
    }

    public void dine(Visitor visitor) {
        synchronized (lock) {
            while (visitors.contains(visitor)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            visitors.add(visitor);
            DiningHallPanel.getInstance().addQueue(visitor);
        }
        try {
            System.out.println(visitor.getId() + " is eating");
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            visitors.remove(visitor);
            DiningHallPanel.getInstance().removeQueue(visitor);
            System.out.println(visitor.getId() + " has finished eating");
            lock.notifyAll();
        }
    }


}
