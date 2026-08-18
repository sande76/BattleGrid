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
    void movePlayerToAdjacentCell(){
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(2,3));

        assertTrue(moved);
        assertEquals(new Position(2,3),board.getPlayerRobot().getPosition());

    }

    @Test
    void rejectdiagonalmovement(){
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(3,3));

        assertFalse(moved);
        assertEquals(new Position(2,2),board.getPlayerRobot().getPosition());

    }

    @Test
    void rejectoutsidemove(){
        GameBoard board = new GameBoard();

        boolean moved = board.movePlayer(new Position(5,3));

        assertFalse(moved);

    }
}
