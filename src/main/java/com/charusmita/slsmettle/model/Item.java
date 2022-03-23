package com.charusmita.slsmettle.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name= "item")
public class Item {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "name")
    @Size(max = 20, message = "Name can not be more than 20 characters.")
    private String name;

    @Column(name = "description")
    @Size(max = 200, message = "description exceeeded")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "cost")
    @NotEmpty
    private double cost;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "updated_at")
    protected Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    protected Date deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.cost, cost) == 0 &&
                id.equals(item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                type == item.type &&
                createdAt.equals(item.createdAt) &&
                updatedAt.equals(item.updatedAt) &&
                Objects.equals(deletedAt, item.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, cost, createdAt, updatedAt, deletedAt);
    }

    public Item(UUID id, String name, String description, ItemType type, double cost,
                Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Item() {
    }

    public Item(ItemBuilder itemBuilder) {
        this.id = itemBuilder.id;
        this.name = itemBuilder.name;
        this.description = itemBuilder.description;
        this.type = itemBuilder.type;
        this.cost = itemBuilder.cost;
        this.createdAt = itemBuilder.createdAt;
        this.updatedAt = itemBuilder.updatedAt;
        this.deletedAt = itemBuilder.deletedAt;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    public UUID getId() {
        return id;
    }

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

    public static class ItemBuilder
    {
        private final UUID id;
        private String name;
        private String description;
        private ItemType type;
        private double cost;
        private Date createdAt;
        protected Date updatedAt;
        protected Date deletedAt;

        public ItemBuilder(UUID id, String name, String description, ItemType type, double cost, Date createdAt, Date updatedAt, Date deletedAt) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.cost = cost;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.deletedAt = deletedAt;
        }

        public ItemBuilder(UUID id) {
            this.id = id;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder type(ItemType type) {
            this.type = type;
            return this;
        }

        public ItemBuilder cost(double cost) {
            this.cost = cost;
            return this;
        }

        public ItemBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ItemBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ItemBuilder deletedAt(Date deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Item build() {
            Item item =  new Item(this);
            validateItemObject(item);
            return item;
        }
        private void validateItemObject(Item item) {
                assert item.getCost() > 0.0;
        }
    }
}
