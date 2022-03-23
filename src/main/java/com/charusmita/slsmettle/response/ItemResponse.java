package com.charusmita.slsmettle.response;

import com.charusmita.slsmettle.model.Item;

public class ItemResponse {
    private Item item;

    public ItemResponse(ItemResponseBuilder itemResponseBuilder) {
        this.item = itemResponseBuilder.item;
    }

    public ItemResponse(Item item){
        this.item = item;
    }
//    public static ItemResponse fromItem(Item item){
//        return new Item.ItemBuilder(item.getId())
//                .name(item.getName())
//                .description(item.getDescription())
//                .cost(item.getCost())
//                .createdAt(item.getCreatedAt())
//                .updatedAt(item.getUpdatedAt())
//                .deletedAt(item.getDeletedAt())
//                .build();
//    }

    public static class ItemResponseBuilder
    {
        private Item item;
        public ItemResponseBuilder(Item item){
            this.item = item;
        }

        public ItemResponseBuilder item(Item item){
            this.item = item;
            return this;
        }

        public ItemResponse build() {
            return new ItemResponse(this);
        }
    }
}
