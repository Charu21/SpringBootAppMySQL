package com.charusmita.slsmettle.request;


import com.charusmita.slsmettle.model.ItemType;

import java.util.Date;

public class ItemRequest {

    private String name;
    private String description;
    private ItemType type;
    private double cost;
    private Date createdAt;
    protected Date updatedAt;
    protected Date deletedAt;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public ItemRequest(String name, String description, ItemType type, double cost, Date createdAt, Date updatedAt, Date deletedAt) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

}
