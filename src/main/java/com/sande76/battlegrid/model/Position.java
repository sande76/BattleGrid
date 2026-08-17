package com.sande76.battlegrid.model;

/**
 * Identifies one cell on the BattleGrid board.
 */
public record Position(int row, int column) {

    public Position {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Row and column cannot be negative");
        }
    }
}
