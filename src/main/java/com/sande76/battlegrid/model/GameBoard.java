package com.sande76.battlegrid.model;

import java.util.Optional;

/**
 * Holds the state for the first BattleGrid milestone: a square board and one player robot.
 */
public final class GameBoard {

    public static final int DEFAULT_SIZE = 5;

    private final int size; 
    private final Robot playerRobot;
    private final Robot enemyRobot;

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
        this.enemyRobot = new Robot("Enemy Robot", new Position(0,0));
    }

    public int getSize() {
        return size;
    }

    public Robot getPlayerRobot() {
        return playerRobot;
    }

    public Robot getEnemyRobot(){
        return enemyRobot;
    }

    public boolean isInside(Position position) {
        return position.row() < size && position.column() < size;
    }

    public Optional<Robot> getRobotAt(Position position) {
        if (playerRobot.getPosition().equals(position)) {
            return Optional.of(playerRobot);
        }

        if (enemyRobot.getPosition().equals(position)) {
            return Optional.of(enemyRobot);
        }

        return Optional.empty();
    }

    public boolean movePlayer(Position target){

        if (!isInside(target)){
            return false;
        }

        if (enemyRobot.getPosition().equals(target)) {
            return false;
        }

        // get the current position of the rorbot
        Position current = playerRobot.getPosition();

        int rowdiff = Math.abs(current.row()-target.row());
        int coldiff = Math.abs(current.column()-target.column());
        boolean isAdjacent = rowdiff + coldiff == 1;

        if (!isAdjacent){
            return false;
        }

        playerRobot.moveTo(target);
        return true;

    }
}

// final class — cannot be extended.
// final method — cannot be overridden.
// final variable — cannot be reassigned.
// static — one value is shared by every object of the class.