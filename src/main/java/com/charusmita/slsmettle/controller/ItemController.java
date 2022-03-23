package com.charusmita.slsmettle.controller;

import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.request.ItemRequest;
import com.charusmita.slsmettle.response.ItemGetByIdsResponse;
import com.charusmita.slsmettle.response.ItemResponse;
import com.charusmita.slsmettle.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return this.itemService.getAllItems();
    }

    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable("id")UUID id) {
        return this.itemService.getItem(id);
    }

    @GetMapping("/items/byIds")
    public ItemGetByIdsResponse getByIds(@RequestParam("ids") List<UUID> ids) {
        return new ItemGetByIdsResponse(this.itemService.getItemsByIds(ids));
    }

    @DeleteMapping
    public void deleteItem(UUID id) {
        this.itemService.deleteItem(id);
    }

    @PostMapping("/item")
    public ItemResponse createItem(@RequestBody @Valid ItemRequest itemRequest) {
        return new ItemResponse.ItemResponseBuilder(this.itemService.addItem(
                new Item.ItemBuilder(UUID.randomUUID()).name(itemRequest.getName())
                        .description(itemRequest.getDescription())
                        .cost(itemRequest.getCost())
                        .createdAt(itemRequest.getCreatedAt())
                        .updatedAt(itemRequest.getUpdatedAt())
                        .deletedAt(itemRequest.getDeletedAt())
                        .build())
        ).build();
    }
}
