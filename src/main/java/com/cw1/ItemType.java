package com.cw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ItemType {
    SKATE,
    HELMET,
    GLOVES,
    PENGUIN;

    public static List<ItemType> getItems() {
        List<ItemType> items = new ArrayList<>();
        items.addAll(Arrays.asList(ItemType.values()));
        return items;
    }
}
