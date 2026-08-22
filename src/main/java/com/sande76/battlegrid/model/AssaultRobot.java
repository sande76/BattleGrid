package com.sande76.battlegrid.model;

/**
 * A balanced robot with the default health and attack values.
 */
public final class AssaultRobot extends Robot {

    public AssaultRobot(String name, Position position) {
        super(name, position, DEFAULT_HEALTH, DEFAULT_ATTACK_DAMAGE);
    }

    @Override
    public String getType() {
        return "Assault";
    }
}
