package com.sande76.battlegrid.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Owns the board cells, robots, movement rules, and basic combat rules.
 */
public final class GameBoard {

    public static final int DEFAULT_SIZE = 5;

    private final int size;
    private final Map<Position, BoardCell> cells;
    private final Robot playerRobot;
    private final Robot enemyRobot;
    private GameState gameState = GameState.PLAYER_TURN;

    public GameBoard() {
        this(DEFAULT_SIZE);
    }

    public GameBoard(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive");
        }

        this.size = size;
        this.cells = createCells(size);
        int center = size / 2;
        this.playerRobot = new AssaultRobot("Player Robot", new Position(center, center));
        this.enemyRobot = new TankRobot("Enemy Robot", new Position(0, 0));
    }

    private Map<Position, BoardCell> createCells(int boardSize) {
        Map<Position, BoardCell> boardCells = new LinkedHashMap<>();

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Position position = new Position(row, column);
                boardCells.put(position, new BoardCell(position));
            }
        }

        return boardCells;
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

    public GameState getGameState() {
        return gameState;
    }

    public Collection<BoardCell> getCells() {
        return Collections.unmodifiableCollection(cells.values());
    }

    public BoardCell getCell(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("Position is outside the board: " + position);
        }

        return cells.get(position);
    }

    public boolean isInside(Position position) {
        return position != null
                && position.row() >= 0
                && position.column() >= 0
                && position.row() < size
                && position.column() < size;
    }

    public boolean addObstacle(Position position, Obstacle obstacle) {
        if (!isInside(position) || getRobotAt(position).isPresent()) {
            return false;
        }

        BoardCell cell = getCell(position);
        if (cell.hasObstacle()) {
            return false;
        }

        cell.placeObstacle(obstacle);
        return true;
    }

    public Optional<Obstacle> removeObstacle(Position position) {
        if (!isInside(position)) {
            return Optional.empty();
        }

        return getCell(position).removeObstacle();
    }

    public boolean addPickup(Position position, Pickup pickup) {
        if (!isInside(position) || getRobotAt(position).isPresent()) {
            return false;
        }

        getCell(position).placePickup(pickup);
        return true;
    }

    public Optional<Pickup> collectPickup(Position position) {
        if (!isInside(position)) {
            return Optional.empty();
        }

        return getCell(position).collectPickup();
    }

    public boolean isWalkable(Position position) {
        return isInside(position) && getCell(position).isWalkable();
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
        if (gameState != GameState.PLAYER_TURN || !isInside(target)
                || isGameOver() || !isWalkable(target)) {
            return false;
        }

        if (enemyRobot.getPosition().equals(target)) {
            return false;
        }

        if (!isAdjacent(playerRobot.getPosition(), target)) {
            return false;
        }

        playerRobot.moveTo(target);
        collectPickup(target).ifPresent(pickup -> {
            if (pickup.getType().equalsIgnoreCase("Health")) {
                playerRobot.heal(pickup.getValue());
            }
        });
        endPlayerTurn();
        return true;
    }

    public boolean attackEnemy() {
        if (gameState != GameState.PLAYER_TURN || isGameOver()
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

    private void endPlayerTurn() {
        updateGameState();
        if (isGameOver()) {
            return;
        }

        gameState = GameState.ENEMY_TURN;
        playEnemyTurn();
        updateGameState();
    }

    private void playEnemyTurn() {
        if (isGameOver()) {
            return;
        }

        if (isAdjacent(enemyRobot.getPosition(), playerRobot.getPosition())) {
            playerRobot.takeDamage(enemyRobot.getAttackDamage());
            return;
        }

        Position nextPosition = nextStepTowardPlayer();
        if (isWalkable(nextPosition) && !playerRobot.getPosition().equals(nextPosition)) {
            enemyRobot.moveTo(nextPosition);
        }
    }

    private Position nextStepTowardPlayer() {
        Position enemyPosition = enemyRobot.getPosition();
        Position playerPosition = playerRobot.getPosition();
        int row = enemyPosition.row();
        int column = enemyPosition.column();

        if (row != playerPosition.row()) {
            row += Integer.signum(playerPosition.row() - row);
        } else if (column != playerPosition.column()) {
            column += Integer.signum(playerPosition.column() - column);
        }

        return new Position(row, column);
    }

    private void updateGameState() {
        if (hasPlayerWon()) {
            gameState = GameState.PLAYER_WON;
        } else if (hasPlayerLost()) {
            gameState = GameState.PLAYER_LOST;
        } else if (!isGameOver()) {
            gameState = GameState.PLAYER_TURN;
        }
    }
}
