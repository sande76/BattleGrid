package com.sande76.battlegrid.model;

import java.util.Objects;

/**
 * Represents a robot with a position, health, and attack damage.
 */
public final class Robot {

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_ATTACK_DAMAGE = 25;

    private final String name;
    private final int attackDamage;
    private int health;
    private Position position;

    public Robot(String name, Position position) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Robot name cannot be blank");
        }

        this.name = name;
        this.position = Objects.requireNonNull(position, "position cannot be null");
        this.health = DEFAULT_HEALTH;
        this.attackDamage = DEFAULT_ATTACK_DAMAGE;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void moveTo(Position newPosition) {
        this.position = Objects.requireNonNull(newPosition, "newPosition cannot be null");
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            throw new IllegalArgumentException("Damage must be positive");
        }

        health = Math.max(0, health - damage);
    }

    public boolean isDestroyed() {
        return health == 0;
    }
}
