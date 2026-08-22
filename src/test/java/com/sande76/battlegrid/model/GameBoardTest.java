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
        assertEquals(25, board.getCells().size());
        assertEquals(new Position(2, 2), board.getPlayerRobot().getPosition());
        assertTrue(board.getRobotAt(new Position(2, 2)).isPresent());
        assertEquals(GameState.PLAYER_TURN, board.getGameState());
    }

    @Test
    void obstacleMakesCellBlockedAndPreventsMovement() {
        GameBoard board = new GameBoard();
        Position target = new Position(2, 3);

        assertTrue(board.addObstacle(target, new Obstacle("Wall")));
        assertTrue(board.getCell(target).hasObstacle());
        assertFalse(board.isWalkable(target));
        assertFalse(board.movePlayer(target));
        assertEquals(new Position(2, 2), board.getPlayerRobot().getPosition());
    }

    @Test
    void canRemoveObstacleAndWalkThroughCell() {
        GameBoard board = new GameBoard();
        Position target = new Position(2, 3);
        Obstacle wall = new Obstacle("Wall");

        board.addObstacle(target, wall);

        assertEquals(wall, board.removeObstacle(target).orElseThrow());
        assertTrue(board.isWalkable(target));
        assertTrue(board.movePlayer(target));
    }

    @Test
    void movingOntoPickupCollectsIt() {
        GameBoard board = new GameBoard();
        Position target = new Position(2, 3);
        Pickup healthPack = new Pickup("Health", 25);

        assertTrue(board.addPickup(target, healthPack));
        assertEquals(healthPack, board.getCell(target).getPickup().orElseThrow());

        assertTrue(board.movePlayer(target));

        assertTrue(board.getCell(target).getPickup().isEmpty());
    }

    @Test
    void healthPickupRestoresPlayerHealthWhenCollected() {
        GameBoard board = new GameBoard();
        Position target = new Position(2, 3);

        board.getPlayerRobot().takeDamage(50);
        board.addPickup(target, new Pickup("Health", 25));

        assertEquals(50, board.getPlayerRobot().getHealth());
        assertTrue(board.movePlayer(target));
        assertEquals(75, board.getPlayerRobot().getHealth());
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
        assertEquals(board.getEnemyRobot().getMaxHealth(), board.getEnemyRobot().getHealth());
    }

    @Test
    void adjacentAttackReducesEnemyHealth() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        boolean attacked = board.attackEnemy();

        assertTrue(attacked);
        assertEquals(
                board.getEnemyRobot().getMaxHealth() - board.getPlayerRobot().getAttackDamage(),
                board.getEnemyRobot().getHealth()
        );
    }

    @Test
    void enemyAttacksWhenAdjacentAfterPlayerAction() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        assertEquals(
                board.getPlayerRobot().getMaxHealth() - board.getEnemyRobot().getAttackDamage(),
                board.getPlayerRobot().getHealth()
        );

        board.attackEnemy();

        assertEquals(
                board.getPlayerRobot().getMaxHealth()
                        - (2 * board.getEnemyRobot().getAttackDamage()),
                board.getPlayerRobot().getHealth()
        );
    }

    @Test
    void defeatingEnemyEndsGameWithPlayerVictory() {
        GameBoard board = createBoardWithPlayerNextToEnemy();
        board.getEnemyRobot().takeDamage(
                board.getEnemyRobot().getHealth() - board.getPlayerRobot().getAttackDamage()
        );

        boolean attacked = board.attackEnemy();

        assertTrue(attacked);
        assertEquals(0, board.getEnemyRobot().getHealth());
        assertTrue(board.getEnemyRobot().isDestroyed());
        assertTrue(board.isGameOver());
        assertTrue(board.hasPlayerWon());
        assertFalse(board.hasPlayerLost());
        assertEquals(GameState.PLAYER_WON, board.getGameState());
        assertTrue(board.getRobotAt(new Position(0, 0)).isEmpty());
    }

    @Test
    void enemyCanDefeatPlayer() {
        GameBoard board = createBoardWithPlayerNextToEnemy();

        while (!board.isGameOver()) {
            assertTrue(board.attackEnemy());
        }

        assertEquals(0, board.getPlayerRobot().getHealth());
        assertTrue(board.isGameOver());
        assertTrue(board.hasPlayerLost());
        assertFalse(board.hasPlayerWon());
        assertEquals(GameState.PLAYER_LOST, board.getGameState());
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
