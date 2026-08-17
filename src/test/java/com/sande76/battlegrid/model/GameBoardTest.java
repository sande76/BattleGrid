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
}
