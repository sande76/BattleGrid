package com.sande76.battlegrid.model;

/**
 * A lightly armoured robot that trades health for attack power.
 */
public final class ScoutRobot extends Robot {

    public static final int SCOUT_HEALTH = 80;
    public static final int SCOUT_ATTACK_DAMAGE = 30;

    public ScoutRobot(String name, Position position) {
        super(name, position, SCOUT_HEALTH, SCOUT_ATTACK_DAMAGE);
    }

    @Override
    public String getType() {
        return "Scout";
    }
}
