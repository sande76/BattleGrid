package com.sande76.battlegrid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotTypesTest {

    @Test
    void boardUsesConcreteRobotTypes() {
        GameBoard board = new GameBoard();

        assertInstanceOf(AssaultRobot.class, board.getPlayerRobot());
        assertInstanceOf(TankRobot.class, board.getEnemyRobot());
    }

    @Test
    void robotTypesHaveDifferentCharacteristics() {
        Position start = new Position(0, 0);
        Robot assault = new AssaultRobot("Assault", start);
        Robot tank = new TankRobot("Tank", start);
        Robot scout = new ScoutRobot("Scout", start);

        assertEquals("Assault", assault.getType());
        assertEquals("Tank", tank.getType());
        assertEquals("Scout", scout.getType());
        assertTrue(tank.getMaxHealth() > assault.getMaxHealth());
        assertTrue(scout.getAttackDamage() > assault.getAttackDamage());
    }

    @Test
    void healingDoesNotExceedEachRobotsMaximumHealth() {
        Robot tank = new TankRobot("Tank", new Position(0, 0));

        tank.takeDamage(50);
        tank.heal(100);

        assertEquals(TankRobot.TANK_HEALTH, tank.getHealth());
    }
}
