package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.kubejs.typings.Info;

public class ManaweavingPatternBuilder extends MABaseBuilder {
    private int[][] pattern;
    private static final int X_BOUND = 11;
    private static final int Y_BOUND = 11;

    public ManaweavingPatternBuilder() {
        this.pattern = new int[X_BOUND][Y_BOUND];
    }

    @Info("Set the entire 11x11 manaweaving pattern at once")
    public ManaweavingPatternBuilder pattern(int[][] pattern) {
        if (pattern.length != X_BOUND) {
            throw new IllegalArgumentException("Pattern must be exactly " + X_BOUND + " rows");
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i].length != Y_BOUND) {
                throw new IllegalArgumentException("Each pattern row must be exactly " + Y_BOUND + " columns");
            }
        }

        this.pattern = pattern;
        return this;
    }

    @HideFromJS
    public ManaweavingPatternBuilder pattern(byte[][] pattern) {
        int[][] converted = new int[pattern.length][];
        for (int row = 0; row < pattern.length; row++) {
            converted[row] = new int[pattern[row].length];
            for (int column = 0; column < pattern[row].length; column++) {
                converted[row][column] = pattern[row][column];
            }
        }
        return pattern(converted);
    }

    @Info("Set a specific point in the manaweaving pattern (values should be 0 or 1)")
    public ManaweavingPatternBuilder setPoint(int x, int y, int value) {
        if (x < 0 || x >= X_BOUND || y < 0 || y >= Y_BOUND) {
            throw new IllegalArgumentException("Pattern coordinates must be within bounds 0-" + (X_BOUND-1) + " for x and 0-" + (Y_BOUND-1) + " for y");
        }

        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Pattern value must be 0 (empty) or 1 (filled)");
        }

        this.pattern[x][y] = value;
        return this;
    }

    @Info("Fill a rectangular area in the pattern with the specified value (0 or 1)")
    public ManaweavingPatternBuilder fillRect(int startX, int startY, int endX, int endY, int value) {
        if (startX < 0 || startX >= X_BOUND || startY < 0 || startY >= Y_BOUND ||
                endX < 0 || endX >= X_BOUND || endY < 0 || endY >= Y_BOUND) {
            throw new IllegalArgumentException("Rectangle coordinates must be within bounds 0-" + (X_BOUND-1) + " for x and 0-" + (Y_BOUND-1) + " for y");
        }

        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Pattern value must be 0 (empty) or 1 (filled)");
        }

        for (int x = Math.min(startX, endX); x <= Math.max(startX, endX); x++) {
            for (int y = Math.min(startY, endY); y <= Math.max(startY, endY); y++) {
                this.pattern[x][y] = value;
            }
        }

        return this;
    }

    @Info("Draw a line in the pattern with the specified value (0 or 1)")
    public ManaweavingPatternBuilder drawLine(int startX, int startY, int endX, int endY, int value) {
        if (startX < 0 || startX >= X_BOUND || startY < 0 || startY >= Y_BOUND ||
                endX < 0 || endX >= X_BOUND || endY < 0 || endY >= Y_BOUND) {
            throw new IllegalArgumentException("Line coordinates must be within bounds 0-" + (X_BOUND-1) + " for x and 0-" + (Y_BOUND-1) + " for y");
        }

        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Pattern value must be 0 (empty) or 1 (filled)");
        }

        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;
        int err = dx - dy;

        while (true) {
            this.pattern[startX][startY] = value;

            if (startX == endX && startY == endY) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                startX += sx;
            }

            if (e2 < dx) {
                err += dx;
                startY += sy;
            }
        }

        return this;
    }

    @Info("Clear the entire pattern (fill with zeros)")
    public ManaweavingPatternBuilder clear() {
        for (int x = 0; x < X_BOUND; x++) {
            for (int y = 0; y < Y_BOUND; y++) {
                this.pattern[x][y] = 0;
            }
        }
        return this;
    }

    @Info("Get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:manaweaving-pattern");

        JsonArray patternArray = new JsonArray();
        for (int i = 0; i < pattern.length; i++) {
            JsonArray rowArray = new JsonArray();
            for (int j = 0; j < pattern[i].length; j++) {
                rowArray.add(pattern[i][j]);
            }
            patternArray.add(rowArray);
        }
        json.add("pattern", patternArray);

        return json;
    }
}
