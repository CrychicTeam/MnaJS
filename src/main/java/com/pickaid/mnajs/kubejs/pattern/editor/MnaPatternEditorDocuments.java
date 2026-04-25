package com.pickaid.mnajs.kubejs.pattern.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MnaPatternEditorDocuments {
    public static final int MANAWEAVE_SIZE = 11;

    private MnaPatternEditorDocuments() {
    }

    public static MnaPatternEditorDocumentState newManaweaveDocument() {
        MnaPatternEditorDocumentState document = new MnaPatternEditorDocumentState();
        document.kind = MnaPatternDocumentKind.MANAWEAVE;
        replaceGrid(document, filledGrid(MANAWEAVE_SIZE, MANAWEAVE_SIZE, 0));
        return document;
    }

    public static MnaPatternEditorDocumentState newRitualDocument(int size) {
        validateOddSquareSize(size);
        MnaPatternEditorDocumentState document = new MnaPatternEditorDocumentState();
        document.kind = MnaPatternDocumentKind.RITUAL;
        replaceGrid(document, filledGrid(size, size, 0));
        for (int row = 0; row < size; row++) {
            document.reagentRows.add(" ".repeat(size));
        }
        return document;
    }

    public static void replaceGrid(MnaPatternEditorDocumentState document, int[][] grid) {
        Objects.requireNonNull(document, "document");
        Objects.requireNonNull(grid, "grid");

        document.grid.clear();
        for (int row = 0; row < grid.length; row++) {
            int[] sourceRow = Objects.requireNonNull(grid[row], "grid[" + row + "]");
            List<Integer> targetRow = new ArrayList<>(sourceRow.length);
            for (int value : sourceRow) {
                targetRow.add(value);
            }
            document.grid.add(targetRow);
        }
    }

    public static int[][] toGrid(MnaPatternEditorDocumentState document) {
        Objects.requireNonNull(document, "document");
        int[][] grid = new int[document.grid.size()][];
        for (int row = 0; row < document.grid.size(); row++) {
            List<Integer> sourceRow = Objects.requireNonNull(document.grid.get(row), "document.grid[" + row + "]");
            grid[row] = new int[sourceRow.size()];
            for (int column = 0; column < sourceRow.size(); column++) {
                Integer value = Objects.requireNonNull(sourceRow.get(column), "document.grid[" + row + "][" + column + "]");
                grid[row][column] = value;
            }
        }
        return grid;
    }

    private static int[][] filledGrid(int rows, int columns, int value) {
        int[][] grid = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid[row][column] = value;
            }
        }
        return grid;
    }

    private static void validateOddSquareSize(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("Ritual document size must be at least 3");
        }
        if ((size & 1) == 0) {
            throw new IllegalArgumentException("Ritual document size must be odd");
        }
    }
}
