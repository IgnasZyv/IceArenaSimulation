package com.cw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkatingArea {
    private static SkatingArea instance = null;
    private static final Object lock = new Object();
    private static final List<Visitor> skaters = new ArrayList<>();
    private SimulationFrame simulationFrame;
    private final Random random = new Random();

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
            while (skaters.contains(visitor)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            skaters.add(visitor);
            IceArenaPanel.getInstance().updateSkatingVisitors(skaters);
            QueuePanel.getInstance().removeVisitor(visitor);
            StatisticsPanel.updateSkatingVisitors(skaters.size());

//            List<Visitor> inQueue = App.getVisitors();
//            inQueue.removeAll(skaters);
//            QueuePanel.getInstance().updateQueue(inQueue);
        }
        try {
            System.out.println(visitor.getId() + " is skating");
            visitor.setSkating(true);
            Thread.sleep(new Random().nextInt(8000, 15000));
            visitor.setSkating(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
//            skaters.remove(visitor);
//            IceArenaPanel.getInstance().updateSkatingVisitors(skaters);
            System.out.println(visitor.getId() + " has finished skating");
            lock.notifyAll();
        }
    }

    public boolean isSkating(Visitor visitor) {
        synchronized (lock) {
            return skaters.contains(visitor);
        }
    }

    public static List<Visitor> getSkaters() {
        return skaters;
    }

    public void setSimulationFrame(SimulationFrame simulationFrame) {
        this.simulationFrame = simulationFrame;
    }

    public QueuePanel getQueuePanel() {
        if (simulationFrame == null) {
            return App.getSimulationFrame().getQueuePanel();
        }
        return simulationFrame.getQueuePanel();
    }
}
