package com.cw1.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ItemType {
    SKATES,
    HELMET,
    GLOVES,
    PENGUIN;

    public static List<ItemType> getItems() {
        List<ItemType> items = new ArrayList<>();
        items.addAll(Arrays.asList(ItemType.values()));
        return items;
    }
}
