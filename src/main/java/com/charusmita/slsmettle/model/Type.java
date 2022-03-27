package com.charusmita.slsmettle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Type {
    HOCKEY_PADS("hockey_pads"),
    HOCKEY_SKATES("hockey_skates"),
    HOCKEY_STICKS("hockey_sticks");

    private final String code;

    Type(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Type getTypeFromCode(String code) {
        return Arrays.stream(Type.values())
                .filter(op -> op.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return code;
    }
}
