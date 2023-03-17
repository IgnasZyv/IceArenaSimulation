package com.cw1;

import java.util.ArrayList;
import java.util.List;

public class SkatingArea {
    private static SkatingArea instance = null;
    private static final Object lock = new Object();
    private static final List<Visitor> skaters = new ArrayList<>();

    private SkatingArea() {
    }

    public static synchronized SkatingArea getInstance() {
        if (instance == null) {
            instance = new SkatingArea();
        }
        return instance;
    }

    public void skate(Visitor visitor) {
        synchronized (lock) {
            // while the is skating wait
            while (skaters.contains(visitor)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            skaters.add(visitor); // add visitor to skating list
            IceRinkPanel.getInstance().updateSkatingVisitors(skaters);
            QueuePanel.getInstance().removeVisitor(visitor);
            StatisticsPanel.updateSkatingVisitors(skaters.size());

        }
        try {
            visitor.setSkating(true);
            int sleepTime = App.skatingDiningSleep(); // get random time to skate
            System.out.println(visitor.getId() + " is skating for: " + sleepTime + " ms");
            Thread.sleep(sleepTime);
            visitor.setSkating(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public static List<Visitor> getSkaters() {
        return skaters;
    }

}
