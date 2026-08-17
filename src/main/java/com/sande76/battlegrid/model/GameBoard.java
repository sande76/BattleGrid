package com.sande76.battlegrid.model;

import java.util.Optional;

/**
 * Holds the state for the first BattleGrid milestone: a square board and one player robot.
 */
public final class GameBoard {

    public static final int DEFAULT_SIZE = 5;

    private final int size;
    private final Robot playerRobot;

    public GameBoard() {
        this(DEFAULT_SIZE);
    }

    GameBoard(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive");
        }

        this.size = size;
        int center = size / 2;
        this.playerRobot = new Robot("Player Robot", new Position(center, center));
    }

    public int getSize() {
        return size;
    }

    public Robot getPlayerRobot() {
        return playerRobot;
    }

    public boolean isInside(Position position) {
        return position.row() < size && position.column() < size;
    }

    public Optional<Robot> getRobotAt(Position position) {
        if (playerRobot.getPosition().equals(position)) {
            return Optional.of(playerRobot);
        }

        return Optional.empty();
    }
}
