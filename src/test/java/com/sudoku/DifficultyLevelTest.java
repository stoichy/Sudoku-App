package com.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifficultyLevelTest {

    @Test
    void easyRemovesFewerCellsThanHard() {
        assertTrue(DifficultyLevel.EASY.getMaxCellsToRemove() < DifficultyLevel.HARD.getMinCellsToRemove());
        assertTrue(DifficultyLevel.MEDIUM.getMinCellsToRemove() > DifficultyLevel.EASY.getMaxCellsToRemove());
        assertTrue(DifficultyLevel.HARD.getMinCellsToRemove() > DifficultyLevel.MEDIUM.getMaxCellsToRemove());
    }

    @Test
    void pickCellsToRemoveStaysWithinDifficultyRange() {
        Random random = new Random(11);

        for (DifficultyLevel difficulty : DifficultyLevel.values()) {
            int cellsToRemove = difficulty.pickCellsToRemove(random);

            assertTrue(cellsToRemove >= difficulty.getMinCellsToRemove());
            assertTrue(cellsToRemove <= difficulty.getMaxCellsToRemove());
        }
    }

    @Test
    void rejectsNullRandomSource() {
        assertThrows(IllegalArgumentException.class, () -> DifficultyLevel.EASY.pickCellsToRemove(null));
    }
}
