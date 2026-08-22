package com.sande76.battlegrid.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the static contents of one board position.
 */
public final class BoardCell {

    private final Position position;
    private Obstacle obstacle;
    private Pickup pickup;

    public BoardCell(Position position) {
        this.position = Objects.requireNonNull(position, "position cannot be null");
    }

    public Position getPosition() {
        return position;
    }

    public boolean isWalkable() {
        return obstacle == null;
    }

    public boolean hasObstacle() {
        return obstacle != null;
    }

    public Optional<Obstacle> getObstacle() {
        return Optional.ofNullable(obstacle);
    }

    public Optional<Pickup> getPickup() {
        return Optional.ofNullable(pickup);
    }

    public void placeObstacle(Obstacle obstacle) {
        this.obstacle = Objects.requireNonNull(obstacle, "obstacle cannot be null");
    }

    public Optional<Obstacle> removeObstacle() {
        Obstacle removed = obstacle;
        obstacle = null;
        return Optional.ofNullable(removed);
    }

    public void placePickup(Pickup pickup) {
        this.pickup = Objects.requireNonNull(pickup, "pickup cannot be null");
    }

    public Optional<Pickup> collectPickup() {
        Pickup collected = pickup;
        pickup = null;
        return Optional.ofNullable(collected);
    }
}
