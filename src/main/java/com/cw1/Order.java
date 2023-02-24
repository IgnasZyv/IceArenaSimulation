package com.cw1;

import java.util.List;

public class Order {
    private static int orderCounter = 0;
    private final int orderNumber;
    private final List<Item> itemList;
    private final Visitor visitor;

    public Order(List<Item> itemList, Visitor visitor) {
        this.itemList = itemList;
        this.visitor = visitor;
        orderNumber = ++orderCounter;
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
}
