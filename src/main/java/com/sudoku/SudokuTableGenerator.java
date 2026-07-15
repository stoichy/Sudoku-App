package com.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates complete Sudoku tables and playable puzzle boards.
 *
 * <p>Implementation approach:
 * <ol>
 *   <li>Seed the three diagonal 3×3 boxes with shuffled digits 1–9. These boxes
 *       do not share rows or columns, so any permutation inside each box is valid.</li>
 *   <li>Fill remaining empty cells with recursive backtracking. Candidate digits
 *       are tried in shuffled order and {@link SudokuValidator} prunes invalid branches.</li>
 *   <li>Build a puzzle by copying a solved board and clearing cells. A cell stays
 *       cleared only when the partially filled board remains rule-compliant.</li>
 * </ol>
 */
public final class SudokuTableGenerator {

    private final Random random;

    public SudokuTableGenerator() {
        this(new Random());
    }

    public SudokuTableGenerator(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random source must not be null.");
        }
        this.random = random;
    }

    /**
     * Returns a fully solved 9×9 Sudoku board with no empty cells.
     */
    public int[][] generateSolvedBoard() {
        int[][] board = createEmptyBoard();
        fillDiagonalBoxes(board);
        solveRemainingCells(board);
        return board;
    }

    /**
     * Returns a puzzle carved from a solved board by clearing up to
     * {@code cellsToRemove} cells while keeping the board valid.
     */
    public int[][] generatePuzzle(int cellsToRemove) {
        if (cellsToRemove < 0 || cellsToRemove > totalCellCount()) {
            throw new IllegalArgumentException("cellsToRemove must be between 0 and 81.");
        }

        int[][] solvedBoard = generateSolvedBoard();
        int[][] puzzleBoard = copyBoard(solvedBoard);
        carvePuzzleCells(puzzleBoard, cellsToRemove);
        return puzzleBoard;
    }

    private void fillDiagonalBoxes(int[][] board) {
        for (int boxIndex = 0; boxIndex < SudokuValidator.BOARD_SIZE; boxIndex += SudokuValidator.BOX_SIZE) {
            fillBox(board, boxIndex, boxIndex);
        }
    }

    private void fillBox(int[][] board, int startRow, int startColumn) {
        List<Integer> digits = shuffledDigits();

        int digitIndex = 0;
        for (int row = startRow; row < startRow + SudokuValidator.BOX_SIZE; row++) {
            for (int column = startColumn; column < startColumn + SudokuValidator.BOX_SIZE; column++) {
                board[row][column] = digits.get(digitIndex);
                digitIndex++;
            }
        }
    }

    private boolean solveRemainingCells(int[][] board) {
        CellPosition nextEmptyCell = findNextEmptyCell(board);
        if (nextEmptyCell == null) {
            return true;
        }

        for (int candidate : shuffledDigits()) {
            if (!SudokuValidator.isValidPlacement(
                    board, nextEmptyCell.row(), nextEmptyCell.column(), candidate)) {
                continue;
            }

            board[nextEmptyCell.row()][nextEmptyCell.column()] = candidate;
            if (solveRemainingCells(board)) {
                return true;
            }

            board[nextEmptyCell.row()][nextEmptyCell.column()] = SudokuValidator.EMPTY_CELL;
        }

        return false;
    }

    private void carvePuzzleCells(int[][] puzzleBoard, int cellsToRemove) {
        List<CellPosition> cellPositions = allCellPositions();
        Collections.shuffle(cellPositions, random);

        int removedCount = 0;
        for (CellPosition cellPosition : cellPositions) {
            if (removedCount >= cellsToRemove) {
                break;
            }

            int previousValue = puzzleBoard[cellPosition.row()][cellPosition.column()];
            puzzleBoard[cellPosition.row()][cellPosition.column()] = SudokuValidator.EMPTY_CELL;

            if (SudokuValidator.isBoardValid(puzzleBoard)) {
                removedCount++;
            } else {
                puzzleBoard[cellPosition.row()][cellPosition.column()] = previousValue;
            }
        }
    }

    private CellPosition findNextEmptyCell(int[][] board) {
        for (int row = 0; row < SudokuValidator.BOARD_SIZE; row++) {
            for (int column = 0; column < SudokuValidator.BOARD_SIZE; column++) {
                if (board[row][column] == SudokuValidator.EMPTY_CELL) {
                    return new CellPosition(row, column);
                }
            }
        }

        return null;
    }

    private List<Integer> shuffledDigits() {
        List<Integer> digits = new ArrayList<>();
        for (int digit = 1; digit <= SudokuValidator.BOARD_SIZE; digit++) {
            digits.add(digit);
        }
        Collections.shuffle(digits, random);
        return digits;
    }

    private List<CellPosition> allCellPositions() {
        List<CellPosition> cellPositions = new ArrayList<>();
        for (int row = 0; row < SudokuValidator.BOARD_SIZE; row++) {
            for (int column = 0; column < SudokuValidator.BOARD_SIZE; column++) {
                cellPositions.add(new CellPosition(row, column));
            }
        }
        return cellPositions;
    }

    private int[][] createEmptyBoard() {
        return new int[SudokuValidator.BOARD_SIZE][SudokuValidator.BOARD_SIZE];
    }

    private int[][] copyBoard(int[][] sourceBoard) {
        int[][] copiedBoard = new int[SudokuValidator.BOARD_SIZE][SudokuValidator.BOARD_SIZE];
        for (int row = 0; row < SudokuValidator.BOARD_SIZE; row++) {
            System.arraycopy(sourceBoard[row], 0, copiedBoard[row], 0, SudokuValidator.BOARD_SIZE);
        }
        return copiedBoard;
    }

    private int totalCellCount() {
        return SudokuValidator.BOARD_SIZE * SudokuValidator.BOARD_SIZE;
    }

    private record CellPosition(int row, int column) {
    }
}
