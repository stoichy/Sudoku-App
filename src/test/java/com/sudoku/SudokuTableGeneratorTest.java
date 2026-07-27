package com.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuTableGeneratorTest {

    private final SudokuTableGenerator generator = new SudokuTableGenerator(new Random(7));

    @Test
    void generatesFullySolvedValidBoard() {
        int[][] solvedBoard = generator.generateSolvedBoard();

        assertTrue(SudokuValidator.isBoardValid(solvedBoard));
        assertEquals(0, countEmptyCells(solvedBoard));
    }

    @Test
    void generatesPlayablePuzzleFromSolvedBoard() {
        int cellsToRemove = 40;
        int[][] puzzleBoard = generator.generatePuzzle(cellsToRemove);

        assertTrue(SudokuValidator.isBoardValid(puzzleBoard));
        assertTrue(countEmptyCells(puzzleBoard) > 0);
        assertTrue(countEmptyCells(puzzleBoard) <= cellsToRemove);
    }

    @Test
    void rejectsInvalidPuzzleRemovalCount() {
        assertThrows(IllegalArgumentException.class, () -> generator.generatePuzzle(-1));
        assertThrows(IllegalArgumentException.class, () -> generator.generatePuzzle(82));
    }

    @Test
    void generatesPuzzleForEachDifficultyLevel() {
        for (DifficultyLevel difficulty : DifficultyLevel.values()) {
            int[][] puzzleBoard = generator.generatePuzzle(difficulty);

            assertTrue(SudokuValidator.isBoardValid(puzzleBoard));
            assertTrue(countEmptyCells(puzzleBoard) > 0);
            assertTrue(countEmptyCells(puzzleBoard) <= difficulty.getMaxCellsToRemove());
        }
    }

    @Test
    void rejectsNullDifficultyLevel() {
        assertThrows(IllegalArgumentException.class, () -> generator.generatePuzzle((DifficultyLevel) null));
    }

    private int countEmptyCells(int[][] board) {
        int emptyCount = 0;
        for (int row = 0; row < SudokuValidator.BOARD_SIZE; row++) {
            for (int column = 0; column < SudokuValidator.BOARD_SIZE; column++) {
                if (board[row][column] == SudokuValidator.EMPTY_CELL) {
                    emptyCount++;
                }
            }
        }
        return emptyCount;
    }
}
