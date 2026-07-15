package com.sudoku;

/**
 * Validates Sudoku board state and user placements against standard 9x9 rules.
 */
public final class SudokuValidator {

    public static final int BOARD_SIZE = 9;
    public static final int BOX_SIZE = 3;
    public static final int EMPTY_CELL = 0;

    private SudokuValidator() {
    }

    /**
     * Returns true when placing {@code value} at {@code row}/{@code column} does not
     * violate Sudoku rules. Empty cells (0) are ignored when scanning for conflicts.
     */
    public static boolean isValidPlacement(int[][] board, int row, int column, int value) {
        validateBoardShape(board);
        validateCellCoordinates(row, column);

        if (value < 1 || value > BOARD_SIZE) {
            return false;
        }

        return !hasConflictInRow(board, row, column, value)
                && !hasConflictInColumn(board, row, column, value)
                && !hasConflictInBox(board, row, column, value);
    }

    /**
     * Returns true when every filled cell on the board follows Sudoku rules.
     */
    public static boolean isBoardValid(int[][] board) {
        validateBoardShape(board);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                int value = board[row][column];
                if (value == EMPTY_CELL) {
                    continue;
                }

                if (value < 1 || value > BOARD_SIZE) {
                    return false;
                }

                if (hasConflictInRow(board, row, column, value)
                        || hasConflictInColumn(board, row, column, value)
                        || hasConflictInBox(board, row, column, value)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean hasConflictInRow(int[][] board, int row, int column, int value) {
        for (int scanColumn = 0; scanColumn < BOARD_SIZE; scanColumn++) {
            if (scanColumn == column) {
                continue;
            }

            if (board[row][scanColumn] == value) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasConflictInColumn(int[][] board, int row, int column, int value) {
        for (int scanRow = 0; scanRow < BOARD_SIZE; scanRow++) {
            if (scanRow == row) {
                continue;
            }

            if (board[scanRow][column] == value) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasConflictInBox(int[][] board, int row, int column, int value) {
        int boxStartRow = (row / BOX_SIZE) * BOX_SIZE;
        int boxStartColumn = (column / BOX_SIZE) * BOX_SIZE;

        for (int scanRow = boxStartRow; scanRow < boxStartRow + BOX_SIZE; scanRow++) {
            for (int scanColumn = boxStartColumn; scanColumn < boxStartColumn + BOX_SIZE; scanColumn++) {
                if (scanRow == row && scanColumn == column) {
                    continue;
                }

                if (board[scanRow][scanColumn] == value) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void validateBoardShape(int[][] board) {
        if (board == null || board.length != BOARD_SIZE) {
            throw new IllegalArgumentException("Board must be a 9x9 array.");
        }

        for (int[] row : board) {
            if (row == null || row.length != BOARD_SIZE) {
                throw new IllegalArgumentException("Board must be a 9x9 array.");
            }
        }
    }

    private static void validateCellCoordinates(int row, int column) {
        if (row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE) {
            throw new IllegalArgumentException("Cell coordinates must be within the 9x9 grid.");
        }
    }
}
