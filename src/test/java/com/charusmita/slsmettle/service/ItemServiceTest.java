package com.charusmita.slsmettle.service;

import com.charusmita.slsmettle.exception.ItemNotFoundException;
import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.model.Type;
import com.charusmita.slsmettle.repository.ItemRepository;
import com.charusmita.slsmettle.request.ItemRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    // CREATE TESTS

    /**
     * Test for creating item successfully.
     */
    @Test
    public void addItem_ProperValues_Success() {
        Item testItem = Item.builder()
                .name("Hockey Stick")
                .description("It is a wooden stick with varying length")
                .cost(100.0)
                .type(Type.HOCKEY_STICKS)
                .build();
        Item actualItem = this.itemService.addItem(testItem);
        assertThat("Item id shouldn't be null", actualItem.getId(), notNullValue());
    }

    /**
     * Test for creating item with missing data throws exception.
     */
    @Test
    public void addItem_InvalidValues_ThrowsException() {
        Item testItem = Item.builder()
                .name("Hockey Stick")
                .description("It is a wooden stick with varying length")
                .build();
        assertThatThrownBy(() -> this.itemService.addItem(testItem))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unable to create item, check values!");
    }

    //READ TESTS

    /**
     * Test for getting item successfully which is present in items db.
     */
    @Test
    public void getItem_AvailableItem_Success() {
        UUID testId = createSingleItemMockData();
        Item actualItem = this.itemService.getItem(testId);
        Assertions.assertEquals(actualItem.getId(), testId);
    }

    /**
     * Test for getting item which is not present in db throws exception.
     */
    @Test
    public void getItem_UnavailableItem_ThrowsException() {
        UUID testId = UUID.randomUUID();
        assertThatThrownBy(() -> this.itemService.getItem(testId))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("Couldn't find item with id " + testId);
    }

    /**
     * Test for getting all items in db successful fetching.
     */
    @Test
    public void getAllItems_AllItems_Success() {
        List<Item> testItemList = createMultiItemMockData();
        Page<Item> items = this.itemService.getAllItems(PageRequest.of(0, 10));
        List<Item> expectedItems = items.stream().toList();
        Assertions.assertEquals(testItemList.size(), expectedItems.size());
    }

    //DELETE TESTS

    /**
     * Test to delete an item from the database successfully.
     */
    @Test
    public void deleteItem_PresentInDbItem_Success() {
        List<Item> testItemList = createMultiItemMockData();
        UUID actualdeleteItemId = testItemList.get(0).getId();
        UUID expectedDeleteItemId = this.itemService.deleteItem(actualdeleteItemId);
        Assertions.assertEquals(actualdeleteItemId, expectedDeleteItemId);
    }

    /**
     * Test to delete an item from the database which doesnt exist throws exception.
     */
    @Test
    public void deleteItem_NotPresentInDb_ThrowsException() {
        UUID testItemToDelete = UUID.randomUUID();
        assertThatThrownBy(() -> this.itemService.deleteItem(testItemToDelete))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("Couldn't find item with id " + testItemToDelete);
    }

    //UPDATE TESTS

    /**
     * Test to update an item exsisting in the database successfully
     */
    @Test
    public void updateItem_PresentInDbItem_Success() {
        List<Item> testItemList = createMultiItemMockData();
        Item testItemToUpdate = testItemList.get(0);
        ItemRequest itemRequest = ItemRequest.builder()
                .name("New Hockey Stick")
                .description("It is a wooden stick with varying length")
                .cost(150.0)
                .type(Type.HOCKEY_STICKS)
                .build();

        Item expectedUpdatedItem = this.itemService.replaceItem(itemRequest, testItemToUpdate.getId());
        Assertions.assertEquals(expectedUpdatedItem.getName(), itemRequest.getName());
        Assertions.assertEquals(expectedUpdatedItem.getDescription(), itemRequest.getDescription());
        Assertions.assertEquals(expectedUpdatedItem.getCost(), itemRequest.getCost());
        Assertions.assertEquals(expectedUpdatedItem.getType(), itemRequest.getType());
    }

    /**
     * Test to update an item which doesnt exist in the database throws exception
     */
    @Test
    public void updateItem_AbsentFromDbItem_ThrowsException() {
        UUID testId = UUID.randomUUID();
        ItemRequest itemRequest = ItemRequest.builder()
                .name("New Hockey Stick")
                .description("It is a wooden stick with varying length")
                .cost(150.0)
                .type(Type.HOCKEY_STICKS)
                .build();

        assertThatThrownBy(() -> this.itemService.replaceItem(itemRequest, testId))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("Couldn't find item with id " + testId);
    }

    //MOCK DATA GENERATOR

    /**
     * Mock data generator for a single item.
     *
     * @return uuid of generated item.
     */
    private UUID createSingleItemMockData() {
        Item testItem = Item.builder()
                .name("Hockey Stick")
                .description("It is a wooden stick with varying length")
                .cost(100.0)
                .type(Type.HOCKEY_STICKS)
                .build();
        Item createdItem = this.itemService.addItem(testItem);
        return createdItem.getId();
    }

    /**
     * Mock data generator for a multiple items.
     *
     * @return List of items generated.
     */
    private List<Item> createMultiItemMockData() {
        Item testItem1 = Item.builder()
                .name("Hockey Stick")
                .description("It is a wooden stick with varying length")
                .cost(100.0)
                .type(Type.HOCKEY_STICKS)
                .build();

        Item testItem2 = Item.builder()
                .name("Hockey Skates")
                .description("Hockey skates")
                .cost(200.0)
                .type(Type.HOCKEY_SKATES)
                .build();

        Item testItem3 = Item.builder()
                .name("Hockey Pads")
                .description("Hockey pads")
                .cost(150.0)
                .type(Type.HOCKEY_PADS)
                .build();

        List<Item> itemList = List.of(testItem1, testItem2, testItem3);
        return this.itemRepository.saveAll(itemList);
    }
}
