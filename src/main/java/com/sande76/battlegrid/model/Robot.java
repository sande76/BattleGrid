package com.sande76.battlegrid.model;

import java.util.Objects;

/**
 * Common state and behavior shared by every robot type.
 */
public abstract class Robot {

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_ATTACK_DAMAGE = 25;

    private final String name;
    private final int maxHealth;
    private final int attackDamage;
    private int health;
    private Position position;

    protected Robot(String name, Position position, int maxHealth, int attackDamage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Robot name cannot be blank");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Maximum health must be positive");
        }
        if (attackDamage <= 0) {
            throw new IllegalArgumentException("Attack damage must be positive");
        }

        this.name = name;
        this.position = Objects.requireNonNull(position, "position cannot be null");
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackDamage = attackDamage;
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public void heal(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Healing amount must be positive");
        }

        health = Math.min(maxHealth, health + amount);
    }

    public boolean isDestroyed() {
        return health == 0;
    }
}
