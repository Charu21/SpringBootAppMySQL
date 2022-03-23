package com.charusmita.slsmettle.service;

import com.charusmita.slsmettle.model.Item;
import com.charusmita.slsmettle.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item){
        try{
            return this.itemRepository.save(item);
        } catch(Exception e) {
            throw new RuntimeException("Unable to create item, check values!");
        }
    }

    public List<Item> getItemsByIds(List<UUID> ids) {
        return ids.stream().map(item-> this.itemRepository.getById(item)).collect(Collectors.toList());
    }

    public Item getItem(UUID id) {
        return itemRepository.getById(id);
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public void deleteItem(UUID id){
        try{
            if(this.itemRepository.findById(id).isPresent())
                this.itemRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("No such item exists!");
        }
    }
}
