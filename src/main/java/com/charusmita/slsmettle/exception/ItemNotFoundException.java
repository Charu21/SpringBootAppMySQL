package com.charusmita.slsmettle.exception;

import java.util.UUID;

public class ItemNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    private final UUID itemId;

    public ItemNotFoundException(UUID itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getMessage() {
        return String.format("Couldn't find item with id %s", this.itemId);
    }
}
