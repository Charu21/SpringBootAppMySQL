package com.charusmita.slsmettle.controller;

import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.request.ItemRequest;
import com.charusmita.slsmettle.response.ItemGetByIdsResponse;
import com.charusmita.slsmettle.response.ItemResponse;
import com.charusmita.slsmettle.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
public record ItemController(
        ItemService itemService
) {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @GetMapping("/items")
    public List<Item> getAllItems() {
        logger.info("Get all items endpoint called");
        return this.itemService.getAllItems();
    }

    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable("id") UUID id) {
        logger.info("Get item called ");
        return this.itemService.getItem(id);
    }

    @GetMapping("/items/byIds")
    public ItemGetByIdsResponse getByIds(@RequestParam("ids") List<UUID> ids) {
        logger.info("Get item with a list of ids called");
        return new ItemGetByIdsResponse(this.itemService.getItemsByIds(ids));
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable UUID id) {
        logger.info("Deleting item with item id=" + id);
        this.itemService.deleteItem(id);
    }

    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@RequestBody @Valid ItemRequest itemRequest) {
        logger.info("Create item called");
        return ItemResponse.builder().item(this.itemService.addItem(
                Item.builder()
                        .name(itemRequest.getName())
                        .description(itemRequest.getDescription())
                        .cost(itemRequest.getCost())
                        .type(itemRequest.getType())
                        .createdAt(itemRequest.getCreatedAt())
                        .updatedAt(itemRequest.getUpdatedAt())
                        .deletedAt(itemRequest.getDeletedAt())
                        .build())
        ).build();
    }

    @PutMapping(value = "/item/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponse updateItem(@RequestBody @Valid ItemRequest itemRequest, @PathVariable UUID id) {
        logger.info("Update item called");
        return ItemResponse.builder().item(this.itemService.replaceItem(itemRequest, id)).build();
    }
}