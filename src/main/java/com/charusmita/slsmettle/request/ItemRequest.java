package com.charusmita.slsmettle.request;


import com.charusmita.slsmettle.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class ItemRequest implements Serializable {

    String name;
    String description;
    Type type;
    double cost;
    Date createdAt;
    protected Date updatedAt;
    protected Date deletedAt;
}
