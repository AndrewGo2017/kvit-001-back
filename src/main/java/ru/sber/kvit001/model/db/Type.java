package ru.sber.kvit001.model.db;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Type {
    STANDARD("СТАНДАРТНЫЙ"), NOT_STANDARD("НЕСТАНДАРТНЫЙ");

    private String value;

    public String getValue() {
        return value;
    }
}