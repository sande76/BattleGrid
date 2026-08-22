package com.sande76.battlegrid.model;

import java.util.Optional;

/**
 * Holds the board state, robots, movement rules, and basic combat rules.
 */
public final class GameBoard {

    public static final int DEFAULT_SIZE = 5;

    private final int size;
    private final Robot playerRobot;
    private final Robot enemyRobot;
    private boolean playerTurn = true;

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
        this.enemyRobot = new Robot("Enemy Robot", new Position(0, 0));
    }

    public int getSize() {
        return size;
    }

    public Robot getPlayerRobot() {
        return playerRobot;
    }

    public Robot getEnemyRobot() {
        return enemyRobot;
    }

    public boolean isInside(Position position) {
        return position.row() < size && position.column() < size;
    }

    public Optional<Robot> getRobotAt(Position position) {
        if (playerRobot.getPosition().equals(position)) {
            return Optional.of(playerRobot);
        }

        if (!enemyRobot.isDestroyed() && enemyRobot.getPosition().equals(position)) {
            return Optional.of(enemyRobot);
        }

        return Optional.empty();
    }

    public boolean movePlayer(Position target) {
        if (!playerTurn || isGameOver() || !isInside(target)) {
            return false;
        }

        if (enemyRobot.getPosition().equals(target)) {
            return false;
        }

        if (!isAdjacent(playerRobot.getPosition(), target)) {
            return false;
        }

        playerRobot.moveTo(target);
        endPlayerTurn();
        return true;
    }

    public boolean attackEnemy() {
        if (!playerTurn || isGameOver()
                || !isAdjacent(playerRobot.getPosition(), enemyRobot.getPosition())) {
            return false;
        }

        enemyRobot.takeDamage(playerRobot.getAttackDamage());
        endPlayerTurn();
        return true;
    }

    public boolean isGameOver() {
        return enemyRobot.isDestroyed() || playerRobot.isDestroyed();
    }

    public boolean hasPlayerWon() {
        return enemyRobot.isDestroyed() && !playerRobot.isDestroyed();
    }

    public boolean hasPlayerLost() {
        return playerRobot.isDestroyed();
    }

    private boolean isAdjacent(Position first, Position second) {
        int rowDifference = Math.abs(first.row() - second.row());
        int columnDifference = Math.abs(first.column() - second.column());
        return rowDifference + columnDifference == 1;
    }

    private void endPlayerTurn(){
        playerTurn = false;
        playEnemyTurn();
        playerTurn = true;

    }

    private void playEnemyTurn(){
        if (isGameOver()) {
            return;
        }

        if (isAdjacent(enemyRobot.getPosition(),playerRobot.getPosition())) {

            playerRobot.takeDamage(enemyRobot.getAttackDamage());
            return;
        }

        enemyRobot.moveTo(nextStepTowardPlayer());
    }

    private Position nextStepTowardPlayer() {
        Position enemyPosition = enemyRobot.getPosition();
        Position playerPosition = playerRobot.getPosition();

        int row = enemyPosition.row();
        int column = enemyPosition.column();

        if (row != playerPosition.row()) {
            row += Integer.signum(playerPosition.row() - row);

        } else if (column != playerPosition.column()) {
            column += Integer.signum(playerPosition.column() - column
            );
        }

        return new Position(row, column);
    }


}
