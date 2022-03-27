package com.charusmita.slsmettle.response;

import com.charusmita.slsmettle.model.Item;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;


@Value
@AllArgsConstructor
public class ItemGetByIdsResponse {
    List<Item> items;

}
