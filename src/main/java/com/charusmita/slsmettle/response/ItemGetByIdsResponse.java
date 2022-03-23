package com.charusmita.slsmettle.response;

import com.charusmita.slsmettle.model.Item;

import java.util.List;

public class ItemGetByIdsResponse {
    private final List<Item> items;

    public ItemGetByIdsResponse(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
