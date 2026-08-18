package com.sande76.battlegrid.model;

import java.util.Objects;

/**
 * The player-controlled robot currently placed on the board.
 */
public final class Robot {

    private final String name;
    private Position position;

    public Robot(String name, Position position) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Robot name cannot be blank");
        }

        this.name = name;
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void moveTo(Position newPosition){
        this.position = Objects.requireNonNull(newPosition, "newPosition cannot be null");
    }

}
