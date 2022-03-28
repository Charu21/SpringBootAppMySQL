package com.charusmita.slsmettle.controller;

import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.request.ItemRequest;
import com.charusmita.slsmettle.response.ItemGetByIdsResponse;
import com.charusmita.slsmettle.response.ItemResponse;
import com.charusmita.slsmettle.service.ItemService;
import com.sipios.springsearch.anotation.SearchSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    String exchangeName;

    @Value("${rabbitmq.routingkey}")
    String routingKey;

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
        Item itemUpdated = this.itemService.replaceItem(itemRequest, id);
        amqpTemplate.convertAndSend(exchangeName, routingKey, "Item updated = " + itemUpdated.toString());
        return ItemResponse.builder().item(itemUpdated).build();
    }

    @GetMapping(value = "/items/filter")
    public ResponseEntity<List<Item>> searchForItems(@SearchSpec Specification<Item> specifications) {
        return new ResponseEntity<>(this.itemService.searchByItem(specifications), HttpStatus.OK);
    }
}
