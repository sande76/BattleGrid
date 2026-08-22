package com.sande76.battlegrid.model;

/**
 * A durable robot with extra health and a heavier frame.
 */
public final class TankRobot extends Robot {

    public static final int TANK_HEALTH = 150;
    public static final int TANK_ATTACK_DAMAGE = 20;

    public TankRobot(String name, Position position) {
        super(name, position, TANK_HEALTH, TANK_ATTACK_DAMAGE);
    }

    @Override
    public String getType() {
        return "Tank";
    }
}
