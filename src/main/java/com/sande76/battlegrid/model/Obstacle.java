package com.sande76.battlegrid.model;

/**
 * A board element that prevents robots from entering its cell.
 */
public final class Obstacle {

    private final String name;

    public Obstacle() {
        this("Obstacle");
    }

    public Obstacle(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Obstacle name cannot be blank");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
