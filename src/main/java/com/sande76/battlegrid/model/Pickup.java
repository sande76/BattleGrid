package com.sande76.battlegrid.model;

/**
 * A collectible board item. Its value is interpreted by the game rules.
 */
public final class Pickup {

    private final String type;
    private final int value;

    public Pickup(String type, int value) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Pickup type cannot be blank");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Pickup value must be positive");
        }

        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
