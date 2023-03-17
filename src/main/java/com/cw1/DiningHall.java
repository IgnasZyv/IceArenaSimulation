package com.cw1;

import java.util.*;
import java.util.concurrent.Semaphore;

public class DiningHall {
    private static DiningHall instance = null;
    private final Queue<Visitor> waitingVisitors;
    private final List<Visitor> visitors;
    private final Semaphore semaphore;

    private DiningHall() {
        this.visitors = new ArrayList<>();
        this.waitingVisitors = new LinkedList<>();
        semaphore = new Semaphore(App.getDiningHallCapacity()); // create a semaphore with the capacity of the dining hall
    }

    public static synchronized DiningHall getInstance() { // singleton pattern
        if (instance == null) {
            instance = new DiningHall();
        }
        return instance;
    }

    public void dine(Visitor visitor) {
        try {
            DiningHallPanel.getInstance().addVisitor(visitor); // add the visitor to the dining hall panel
            System.out.println(visitor.getId() + " is eating");
            visitors.add(visitor); // add the visitor to the list of visitors in the dining hall
            int sleepTime = App.skatingDiningSleep();
            System.out.println(visitor.getId() + " is dining for: " + sleepTime + " ms");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        leave(visitor); // leave the dining hall
    }


    public void enter(Visitor visitor) throws InterruptedException {
        if (waitingVisitors.contains(visitor)) return; // If the visitor is already in the queue, return
        waitingVisitors.offer(visitor); // Add the visitor to the waiting queue
        visitor.setInQueue(true);
        DiningHallQueue.getInstance().addVisitor(visitor); // add the visitor to the queue panel
        System.out.printf("%s is waiting to enter the dining hall\n", visitor);

        semaphore.acquire(); // Acquire a permit from the Semaphore, or wait if no permit is available
        waitingVisitors.remove(visitor); // Remove the visitor from the waiting queue
        DiningHallQueue.getInstance().removeQueue(visitor); // remove the visitor from the queue panel
        visitor.setInQueue(false);
        System.out.printf("%s entered the dining hall. Spaces available: %d\n",
                visitor, semaphore.availablePermits());
        dine(visitor); // Start dining
    }

    public void leave(Visitor visitor) {
        semaphore.release(); // Release the permit back to the Semaphore
        visitors.remove(visitor); // remove the visitor from the list of visitors in the dining hall
        System.out.printf("%s left the dining hall. Spaces available: %d\n",
                visitor, semaphore.availablePermits());
        DiningHallPanel.getInstance().removeQueue(visitor); // remove the visitor from the dining hall panel
    }

    public int getWaitingVisitorsCount() {
        return waitingVisitors.size();
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

}
