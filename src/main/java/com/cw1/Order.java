package com.cw1;

import com.cw1.enums.OrderStatus;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static AtomicInteger orderCounter = new AtomicInteger(0);
    private final int orderNumber;
    private final List<Item> itemList;
    private final Visitor visitor;
    private OrderStatus status;

    public Order(List<Item> itemList, Visitor visitor) {
        this.itemList = itemList;
        this.visitor = visitor;
        this.orderNumber = orderCounter.getAndIncrement();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "Order #" + orderNumber + ": By " + visitor + " " + itemList.toString();
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}
