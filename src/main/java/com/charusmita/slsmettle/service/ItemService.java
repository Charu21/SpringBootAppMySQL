package com.charusmita.slsmettle.service;

import com.charusmita.slsmettle.exception.ItemNotFoundException;
import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.repository.ItemRepository;
import com.charusmita.slsmettle.request.ItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public record ItemService(
        ItemRepository itemRepository
) {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public Item addItem(Item item) {
        try {
            final Item savedItem = this.itemRepository.save(item);
            if (itemExists(savedItem.getId()))
                logger.info("Item added succesfully");
            return savedItem;
        } catch (Exception e) {
            logger.info("Item save exception");
            throw new RuntimeException("Unable to create item, check values!" + e.getMessage());
        }
    }

    public List<Item> getItemsByIds(List<UUID> ids) {
        List<Item> items = ids.stream().map(item -> this.itemRepository.findById(item).orElse(null)).collect(Collectors.toList());
        if (items.isEmpty())
            logger.info("No items found");
        else
            logger.info("Get by ids completed successfully");
        return items;
    }

    public Item getItem(UUID id) {
        final Optional<Item> byId = this.itemRepository.findById(id);
        final Item[] ans = new Item[1];
        byId.ifPresentOrElse((item) -> {
                    ans[0] = item;
                    logger.info("Got item succesfully with id=" + id);
                },
                () -> {
                    logger.info("No such item found!");
                    throw new ItemNotFoundException(id);
                });
        return ans[0];
    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            logger.info("No items found!");
        } else {
            logger.info(items.size() + " Items found!");
        }
        return items;
    }

    public void deleteItem(UUID id) {
        try {
            if (this.itemRepository.findById(id).isPresent())
                this.itemRepository.deleteById(id);
            logger.info("Deleted item success!");
        } catch (Exception e) {
            logger.info("Deleting item exception");
            throw new ItemNotFoundException(id);
        }
    }

    public Item replaceItem(ItemRequest newItem, UUID id) {
        return this.itemRepository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setCost(newItem.getCost());
                    item.setDescription(newItem.getDescription());
                    item.setType(newItem.getType());

                    if (newItem.getCreatedAt() != null)
                        item.setCreatedAt(newItem.getCreatedAt());

                    if (newItem.getUpdatedAt() != null)
                        item.setUpdatedAt(newItem.getUpdatedAt());

                    if (newItem.getDeletedAt() != null)
                        item.setDeletedAt(newItem.getDeletedAt());

                    logger.info("Updating item success!");
                    return this.itemRepository.save(item);
                })
                .orElseGet(() -> {
                            logger.info("Item does not exist, creating new item");
                            Item item = this.itemRepository.save(Item.builder()
                                    .id(id)
                                    .name(newItem.getName())
                                    .cost(newItem.getCost())
                                    .description(newItem.getDescription())
                                    .type(newItem.getType())
                                    .createdAt(newItem.getCreatedAt())
                                    .updatedAt(newItem.getUpdatedAt())
                                    .deletedAt(newItem.getDeletedAt())
                                    .build());
                            logger.info("Creating new item success!");
                            return item;
                        }
                );
    }

    public boolean itemExists(UUID id) {
        return this.itemRepository.existsById(id);
    }
}
