package com.cw1;

public class Item {
    private String name;
    private boolean available;
    private ItemType type;
    private Visitor borrower;

    public Item(ItemType type) {
        this.available = true;
        this.type = type;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setBorrower(Visitor visitor) {
        this.borrower = visitor;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}
