package com.pickaid.mnajs.kubejs.pattern;

import dev.latvian.mods.kubejs.typings.Info;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public final class MnaPatternHelper {
    private static final Pattern TOKEN_SPLIT = Pattern.compile("[,\\s]+");
    private static final int MANAWEAVE_SIZE = 11;

    private MnaPatternHelper() {
    }

    @Info("Create a blank odd-sized square ritual grid.")
    public static int[][] ritualGrid(int size) {
        validateOddSquareSize("Ritual pattern", size);
        return filledGrid(size, size, 0);
    }

    @Info("Create a square ritual grid filled with the provided value.")
    public static int[][] ritualFilled(int size, int value) {
        validateOddSquareSize("Ritual pattern", size);
        return filledGrid(size, size, value);
    }

    @Info("Parse a ritual grid from readable rows such as '0 1 X' or '01X'.")
    public static int[][] ritualRows(String... rows) {
        int[][] grid = parseNumericRows(rows);
        validateSquare("Ritual pattern", grid, true);
        return grid;
    }

    @Info("Create a blank odd-sized reagent grid that matches ritual rows.")
    public static String[] reagentGrid(int size) {
        validateOddSquareSize("Reagent pattern", size);
        String[] rows = new String[size];
        Arrays.fill(rows, " ".repeat(size));
        return rows;
    }

    @Info("Validate and keep reagent rows as-is.")
    public static String[] reagentRows(String... rows) {
        Objects.requireNonNull(rows, "rows");
        if (rows.length == 0) {
            throw new IllegalArgumentException("Reagent pattern must define at least one row");
        }

        int width = -1;
        String[] copy = new String[rows.length];
        for (int row = 0; row < rows.length; row++) {
            String value = Objects.requireNonNull(rows[row], "Row " + row + " cannot be null");
            if (width == -1) {
                width = value.length();
            } else if (value.length() != width) {
                throw new IllegalArgumentException("Reagent pattern rows must all have the same width");
            }
            copy[row] = value;
        }

        if (width <= 0) {
            throw new IllegalArgumentException("Reagent pattern width must be greater than 0");
        }

        if (copy.length != width) {
            throw new IllegalArgumentException("Reagent pattern must be square");
        }

        if ((copy.length & 1) == 0) {
            throw new IllegalArgumentException("Reagent pattern size must be odd");
        }

        return copy;
    }

    @Info("Create a blank 11x11 manaweave grid.")
    public static int[][] manaweaveGrid() {
        return filledGrid(MANAWEAVE_SIZE, MANAWEAVE_SIZE, 0);
    }

    @Info("Create an 11x11 manaweave grid filled with the provided value.")
    public static int[][] manaweaveFilled(int value) {
        return filledGrid(MANAWEAVE_SIZE, MANAWEAVE_SIZE, value);
    }

    @Info("Parse an 11x11 manaweave grid from readable rows such as '0 1 X' or '01X'.")
    public static int[][] manaweaveRows(String... rows) {
        int[][] grid = parseNumericRows(rows);
        validateExact("Manaweave pattern", grid, MANAWEAVE_SIZE, MANAWEAVE_SIZE);
        return grid;
    }

    @Info("Create a rectangular int grid filled with the provided value.")
    public static int[][] filledGrid(int rows, int columns, int value) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Grid bounds must be greater than 0");
        }

        int[][] grid = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            Arrays.fill(grid[row], value);
        }
        return grid;
    }

    private static int[][] parseNumericRows(String... rows) {
        Objects.requireNonNull(rows, "rows");
        if (rows.length == 0) {
            throw new IllegalArgumentException("Pattern must define at least one row");
        }

        int[][] grid = new int[rows.length][];
        int width = -1;
        for (int row = 0; row < rows.length; row++) {
            String source = Objects.requireNonNull(rows[row], "Row " + row + " cannot be null");
            int[] parsedRow = parseNumericRow(source);
            if (width == -1) {
                width = parsedRow.length;
            } else if (parsedRow.length != width) {
                throw new IllegalArgumentException("Pattern rows must all have the same width");
            }
            grid[row] = parsedRow;
        }

        return grid;
    }

    private static int[] parseNumericRow(String source) {
        String trimmed = source.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Pattern rows cannot be blank");
        }

        String[] tokens;
        if (containsExplicitSeparators(trimmed)) {
            tokens = TOKEN_SPLIT.split(trimmed);
        } else {
            tokens = new String[trimmed.length()];
            for (int index = 0; index < trimmed.length(); index++) {
                tokens[index] = String.valueOf(trimmed.charAt(index));
            }
        }

        int[] row = new int[tokens.length];
        for (int column = 0; column < tokens.length; column++) {
            row[column] = parseToken(tokens[column]);
        }
        return row;
    }

    private static boolean containsExplicitSeparators(String value) {
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            if (Character.isWhitespace(character) || character == ',') {
                return true;
            }
        }
        return false;
    }

    private static int parseToken(String token) {
        String value = token.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Pattern token cannot be blank");
        }

        return switch (value) {
            case ".", "_", "-" -> 0;
            case "x", "X" -> 1;
            default -> Integer.parseInt(value);
        };
    }

    private static void validateOddSquareSize(String label, int size) {
        if (size < 1) {
            throw new IllegalArgumentException(label + " size must be at least 1");
        }
        if ((size & 1) == 0) {
            throw new IllegalArgumentException(label + " size must be odd");
        }
    }

    private static void validateSquare(String label, int[][] grid, boolean requireOdd) {
        if (grid.length == 0) {
            throw new IllegalArgumentException(label + " must define at least one row");
        }

        int width = grid[0].length;
        if (width == 0) {
            throw new IllegalArgumentException(label + " width must be greater than 0");
        }

        if (grid.length != width) {
            throw new IllegalArgumentException(label + " must be square");
        }

        if (requireOdd && (grid.length & 1) == 0) {
            throw new IllegalArgumentException(label + " size must be odd");
        }
    }

    private static void validateExact(String label, int[][] grid, int rows, int columns) {
        if (grid.length != rows) {
            throw new IllegalArgumentException(label + " must define exactly " + rows + " rows");
        }
        for (int row = 0; row < grid.length; row++) {
            if (grid[row] == null || grid[row].length != columns) {
                throw new IllegalArgumentException(label + " row " + row + " must define exactly " + columns + " columns");
            }
        }
    }
}
