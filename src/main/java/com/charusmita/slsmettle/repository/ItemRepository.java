package com.charusmita.slsmettle.repository;

import com.charusmita.slsmettle.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID>, JpaSpecificationExecutor<Item> {


}
