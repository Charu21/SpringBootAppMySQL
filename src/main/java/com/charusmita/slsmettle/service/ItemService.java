package com.charusmita.slsmettle.service;

import com.charusmita.slsmettle.exception.ItemNotFoundException;
import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.repository.ItemRepository;
import com.charusmita.slsmettle.request.ItemRequest;
import com.sipios.springsearch.anotation.SearchSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Item Service to process Create, Read, Update and Delete of Hockey Items.
 *
 * @author charusmita shah
 */
@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Add a new item to the items table.
     *
     * @param item is the new item to be added.
     * @return the item added.
     */
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

    /**
     * Get the item details for ids provided in ids.
     *
     * @param ids the list of ids for which information needs to be fetched.
     * @return the list of items corresponding to the list of ids.
     */
    public List<Item> getItemsByIds(List<UUID> ids) {
        List<Item> items = ids.stream().map(item -> this.itemRepository.findById(item).orElse(null)).collect(Collectors.toList());
        if (items.isEmpty())
            logger.info("No items found");
        else
            logger.info("Get by ids completed successfully");
        return items;
    }

    /**
     * Get a single item by id.
     *
     * @param id the id for the item which needs to be fetched.
     * @return the Item with the specific id.
     */
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

    /**
     * Get all items currently in database. Made for small amount of data only.
     *
     * @return all the items currently in the items table.
     */
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            logger.info("No items found!");
        } else {
            logger.info(items.size() + " Items found!");
        }
        return items;
    }

    /**
     * Delete an item by specific id.
     *
     * @param id deletes the item specified by id.
     */
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

    /**
     * Used to replace an existing item or if it doesnt exist, is created.
     *
     * @param newItem the modified item details.
     * @param id      the id of the item to be modified.
     * @return the modified item.
     */
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

    /**
     * Check if the specified item exists or not in the items table.
     *
     * @param id the id of the item to be checked.
     * @return boolean true if the item exists or false if it does not.
     */
    public boolean itemExists(UUID id) {
        return this.itemRepository.existsById(id);
    }

    /**
     * Search for items by Specification
     *
     * @param specifications
     * @return a list of items matching above specifications
     */
    public List<Item> searchByItem(@SearchSpec Specification<Item> specifications) {
        final List<Item> all = this.itemRepository.findAll(Specification.where(specifications));
        return all;
    }
}
