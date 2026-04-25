package com.pickaid.mnajs.kubejs.recipe;

import dev.latvian.mods.rhino.util.HideFromJS;

public final class ManaweavingPatternRecipeJS extends MnaBaseRecipeJS<ManaweavingPatternRecipeJS> {
    public ManaweavingPatternRecipeJS pattern(int[][] value) {
        return setKey("pattern", copyPattern(value));
    }

    @HideFromJS
    public ManaweavingPatternRecipeJS pattern(byte[][] value) {
        return pattern(toIntPattern(value));
    }

    public ManaweavingPatternRecipeJS cell(int row, int column, int value) {
        require(row >= 0 && row < 11, "Manaweaving pattern row must be between 0 and 10");
        require(column >= 0 && column < 11, "Manaweaving pattern column must be between 0 and 10");

        int[][] pattern = currentPattern();
        pattern[row][column] = value;
        return setKey("pattern", pattern);
    }

    @Override
    protected void validateRecipe() {
        int[][] pattern = currentPattern();
        require(pattern.length == 11, "Manaweaving pattern recipe must define exactly 11 rows");

        for (int row = 0; row < pattern.length; row++) {
            require(pattern[row] != null && pattern[row].length == 11,
                    "Manaweaving pattern recipe row " + row + " must define exactly 11 columns");
        }
    }

    private int[][] currentPattern() {
        Object current = currentValue("pattern");
        if (current instanceof int[][] value) {
            return copyPattern(value);
        }
        if (current instanceof byte[][] value) {
            return toIntPattern(value);
        }
        return new int[11][11];
    }

    private static int[][] toIntPattern(byte[][] value) {
        if (value == null) {
            return new int[0][0];
        }

        int[][] result = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            byte[] sourceRow = value[row];
            if (sourceRow == null) {
                result[row] = null;
                continue;
            }

            result[row] = new int[sourceRow.length];
            for (int column = 0; column < sourceRow.length; column++) {
                result[row][column] = sourceRow[column];
            }
        }
        return result;
    }

    private static int[][] copyPattern(int[][] value) {
        if (value == null) {
            return new int[0][0];
        }

        int[][] copy = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            copy[row] = value[row] == null ? null : value[row].clone();
        }
        return copy;
    }
}
