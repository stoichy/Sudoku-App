package com.sudoku;

import java.util.Random;

/**
 * Sudoku puzzle difficulty based on how many cells are cleared from a solved board.
 * Fewer removed cells means more clues and an easier puzzle.
 */
public enum DifficultyLevel {

    EASY(30, 35),
    MEDIUM(40, 45),
    HARD(50, 55);

    private final int minCellsToRemove;
    private final int maxCellsToRemove;

    DifficultyLevel(int minCellsToRemove, int maxCellsToRemove) {
        this.minCellsToRemove = minCellsToRemove;
        this.maxCellsToRemove = maxCellsToRemove;
    }

    public int getMinCellsToRemove() {
        return minCellsToRemove;
    }

    public int getMaxCellsToRemove() {
        return maxCellsToRemove;
    }

    /**
     * Picks a random number of cells to remove within this difficulty's range.
     */
    public int pickCellsToRemove(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random source must not be null.");
        }

        int range = maxCellsToRemove - minCellsToRemove + 1;
        return minCellsToRemove + random.nextInt(range);
    }
}
