package com.cw1;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Outlet {

    private final IceArena iceArena;
    private final Visitor visitor;
    private final Outlet outlet = this;

    private final LinkedBlockingQueue<Order> visitorQueue;

    public Outlet(IceArena iceArena, Visitor visitor) {
        this.iceArena = iceArena;
        this.visitor = visitor;
        visitorQueue = new LinkedBlockingQueue<>();
    }

    public Boolean placeOrder(Order order) {
        System.out.println("Placing order: " + order.toString() + " by " + visitor.getId());
        return iceArena.queueOrder(order);
    }

    public Boolean returnItems(List<Item> items) {
        return iceArena.returnItems(items);
    }

    public synchronized void processOrders() {
        iceArena.processOrders();
    }

}
