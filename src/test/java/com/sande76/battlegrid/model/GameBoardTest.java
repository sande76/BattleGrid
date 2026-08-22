package com.sande76.battlegrid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBoardTest {

    @Test
    void createsFiveByFiveBoardWithPlayerRobotInCenter() {
        GameBoard board = new GameBoard();

        assertEquals(5, board.getSize());
        assertEquals(new Position(2, 2), board.getPlayerRobot().getPosition());
        assertTrue(board.getRobotAt(new Position(2, 2)).isPresent());
    }

    @Test
    void identifiesPositionsInsideTheBoard() {
        GameBoard board = new GameBoard();

        assertTrue(board.isInside(new Position(0, 0)));
        assertTrue(board.isInside(new Position(4, 4)));
        assertFalse(board.isInside(new Position(5, 0)));
        assertFalse(board.isInside(new Position(0, 5)));
    }

    @Test
    void movesPlayerToAdjacentCell() {
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(2, 3));

        assertTrue(moved);
        assertEquals(new Position(2, 3), board.getPlayerRobot().getPosition());
    }

    @Test
    void successfulPlayerMoveTriggersExactlyOneEnemyMove() {
        GameBoard board = new GameBoard();

        board.movePlayer(new Position(2, 3));

        assertEquals(new Position(1, 0), board.getEnemyRobot().getPosition());
    }

    @Test
    void rejectedPlayerMoveDoesNotTriggerEnemyTurn() {
        GameBoard board = new GameBoard();

        board.movePlayer(new Position(3, 3));

        assertEquals(new Position(0, 0), board.getEnemyRobot().getPosition());
    }

    @Test
    void rejectsDiagonalMovement() {
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(3, 3));

        assertFalse(moved);
        assertEquals(new Position(2, 2), board.getPlayerRobot().getPosition());
    }

    @Test
    void rejectsOutsideMove() {
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(5, 3));

        assertFalse(moved);
    }

    @Test
    void placesEnemyInTopLeftCorner() {
        GameBoard board = new GameBoard();

        assertEquals(new Position(0, 0), board.getEnemyRobot().getPosition());
        assertTrue(board.getRobotAt(new Position(0, 0)).isPresent());
    }

    @Test
    void preventsPlayerEnteringEnemyCell() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        boolean moved = board.movePlayer(new Position(0, 0));

        assertFalse(moved);
        assertEquals(new Position(0, 1), board.getPlayerRobot().getPosition());
    }

    @Test
    void rejectsAttackWhenEnemyIsNotAdjacent() {
        GameBoard board = new GameBoard();

        boolean attacked = board.attackEnemy();

        assertFalse(attacked);
        assertEquals(Robot.DEFAULT_HEALTH, board.getEnemyRobot().getHealth());
    }

    @Test
    void adjacentAttackReducesEnemyHealth() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        boolean attacked = board.attackEnemy();

        assertTrue(attacked);
        assertEquals(75, board.getEnemyRobot().getHealth());
    }

    @Test
    void enemyAttacksWhenAdjacentAfterPlayerAction() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        assertEquals(75, board.getPlayerRobot().getHealth());

        board.attackEnemy();

        assertEquals(50, board.getPlayerRobot().getHealth());
    }

    @Test
    void defeatingEnemyEndsGameWithPlayerVictory() {
        GameBoard board = createBoardWithPlayerNextToEnemy();
        board.getEnemyRobot().takeDamage(75);

        boolean attacked = board.attackEnemy();

        assertTrue(attacked);
        assertEquals(0, board.getEnemyRobot().getHealth());
        assertTrue(board.getEnemyRobot().isDestroyed());
        assertTrue(board.isGameOver());
        assertTrue(board.hasPlayerWon());
        assertFalse(board.hasPlayerLost());
        assertTrue(board.getRobotAt(new Position(0, 0)).isEmpty());
    }

    @Test
    void enemyCanDefeatPlayer() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        assertTrue(board.attackEnemy());
        assertTrue(board.attackEnemy());
        assertTrue(board.attackEnemy());

        assertEquals(0, board.getPlayerRobot().getHealth());
        assertTrue(board.isGameOver());
        assertTrue(board.hasPlayerLost());
        assertFalse(board.hasPlayerWon());
        assertFalse(board.attackEnemy());
    }

    private GameBoard createBoardWithPlayerNextToEnemy() {
        GameBoard board = new GameBoard();
        board.movePlayer(new Position(1, 2));
        board.movePlayer(new Position(0, 2));
        board.movePlayer(new Position(0, 1));
        return board;
    }
}
