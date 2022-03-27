package com.charusmita.slsmettle.response;

import com.charusmita.slsmettle.model.Item;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ItemResponse {
    Item item;
}
