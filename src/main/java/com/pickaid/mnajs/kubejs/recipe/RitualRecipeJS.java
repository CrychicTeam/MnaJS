package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.pattern.MnaPatternHelper;
import com.pickaid.mnajs.recipes.schema.RitualRecipeSchema;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RitualRecipeJS extends MnaBaseRecipeJS<RitualRecipeJS> {
    @Info(value = "Set the ritual pattern grid.", params = {
            @Param(name = "value", value = "Odd-sized square int grid that defines the ritual layout.")
    })
    public RitualRecipeJS pattern(int[][] value) {
        return setKey("pattern", copyPattern(value));
    }

    @Info(value = "Set the ritual pattern from readable rows.", params = {
            @Param(name = "rows", value = "Rows such as '0 1 0', '010', or 'X..'.")
    })
    public RitualRecipeJS patternRows(String... rows) {
        return pattern(MnaPatternHelper.ritualRows(rows));
    }

    @HideFromJS
    public RitualRecipeJS pattern(Integer[][] value) {
        return pattern(toIntPattern(value));
    }

    @Info(value = "Set the ritual display pattern grid.", params = {
            @Param(name = "value", value = "Odd-sized square int grid used for the display overlay.")
    })
    public RitualRecipeJS displayPattern(int[][] value) {
        return setKey("displayPattern", copyPattern(value));
    }

    @Info(value = "Set the ritual display pattern from readable rows.", params = {
            @Param(name = "rows", value = "Rows such as '0 1 0', '010', or 'X..'.")
    })
    public RitualRecipeJS displayPatternRows(String... rows) {
        return displayPattern(MnaPatternHelper.ritualRows(rows));
    }

    @HideFromJS
    public RitualRecipeJS displayPattern(Integer[][] value) {
        return displayPattern(toIntPattern(value));
    }

    @Info(value = "Set the reagent symbol rows used by this ritual.", params = {
            @Param(name = "value", value = "Odd-sized square rows using single-character reagent keys.")
    })
    public RitualRecipeJS reagentRows(String... value) {
        return setKey("reagents", MnaPatternHelper.reagentRows(value));
    }

    @Info(value = "Alias for reagentRows(...).", params = {
            @Param(name = "value", value = "Odd-sized square rows using single-character reagent keys.")
    })
    public RitualRecipeJS reagents(String... value) {
        return reagentRows(value);
    }

    @HideFromJS
    public RitualRecipeJS reagentPattern(String[] value) {
        return reagentRows(value);
    }

    @HideFromJS
    public RitualRecipeJS keys(LinkedHashMap<String, ?> value) {
        return setKey("keys", value);
    }

    @Info(value = "Add or replace one ritual reagent key definition.", params = {
            @Param(name = "reagent", value = "Configured reagent object created with MnaRitualReagent.of(...).")
    })
    public RitualRecipeJS reagent(MnaRitualReagent reagent) {
        return putReagent(reagent);
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(symbol, item));
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, MnaItemOrTag item, boolean optional, boolean consume) {
        MnaRitualReagent reagent = new MnaRitualReagent(symbol, item);
        if (optional) {
            reagent.optional();
        }
        if (!consume) {
            reagent.keep();
        }
        return reagent(reagent);
    }

    @HideFromJS
    public RitualRecipeJS dynamicReagent(char symbol, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(symbol, item).dynamic());
    }

    @HideFromJS
    public RitualRecipeJS dynamicSourceReagent(char symbol, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(symbol, item).dynamicSource());
    }

    @HideFromJS
    public RitualRecipeJS manualReturnReagent(char symbol, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(symbol, item).manualReturn());
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, String item) {
        return reagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, Item item) {
        return reagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, ItemStack item) {
        return reagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, String item, boolean optional, boolean consume) {
        return reagent(symbol, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, Item item, boolean optional, boolean consume) {
        return reagent(symbol, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeJS reagent(char symbol, ItemStack item, boolean optional, boolean consume) {
        return reagent(symbol, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeJS dynamicReagent(char symbol, String item) {
        return dynamicReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS dynamicReagent(char symbol, Item item) {
        return dynamicReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS dynamicReagent(char symbol, ItemStack item) {
        return dynamicReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS dynamicSourceReagent(char symbol, String item) {
        return dynamicSourceReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS dynamicSourceReagent(char symbol, Item item) {
        return dynamicSourceReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS dynamicSourceReagent(char symbol, ItemStack item) {
        return dynamicSourceReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS manualReturnReagent(char symbol, String item) {
        return manualReturnReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS manualReturnReagent(char symbol, Item item) {
        return manualReturnReagent(symbol, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeJS manualReturnReagent(char symbol, ItemStack item) {
        return manualReturnReagent(symbol, MnaItemOrTag.parse(item));
    }

    @Info(value = "Replace the manaweave pattern list used by this ritual.", params = {
            @Param(name = "values", value = "Zero or more manaweave pattern ids.")
    })
    public RitualRecipeJS manaweavePatterns(MnaManaweavePatternId... values) {
        return setKey("manaweave", values);
    }

    @HideFromJS
    public RitualRecipeJS manaweavePatterns(String... values) {
        return manaweavePatterns(Arrays.stream(values)
                .map(MnaManaweavePatternId::parse)
                .toArray(MnaManaweavePatternId[]::new));
    }

    @Info(value = "Append one manaweave pattern to this ritual.", params = {
            @Param(name = "value", value = "Manaweave pattern recipe id such as built-in mna:manaweave_patterns/slash or custom kubejs:slash.")
    })
    public RitualRecipeJS addManaweavePattern(MnaManaweavePatternId value) {
        var patterns = editableList("manaweave");
        patterns.add(value.id());
        return setKey("manaweave", patterns);
    }

    @HideFromJS
    public RitualRecipeJS addManaweavePattern(String value) {
        return addManaweavePattern(MnaManaweavePatternId.parse(value));
    }

    @Info(value = "Set the ritual's inner beam color.", params = {
            @Param(name = "value", value = "Packed RGB value such as 0xFFD54F.")
    })
    public RitualRecipeJS innerColor(long value) {
        return putParameter("innerColor", toHexColor(value));
    }

    @Info(value = "Set the ritual's outer beam color.", params = {
            @Param(name = "value", value = "Packed RGB value such as 0xFFF176.")
    })
    public RitualRecipeJS outerColor(long value) {
        return putParameter("outerColor", toHexColor(value));
    }

    @Info(value = "Set the ritual's beam color.", params = {
            @Param(name = "value", value = "Packed RGB value such as 0xFBC02D.")
    })
    public RitualRecipeJS beamColor(long value) {
        return putParameter("beamColor", toHexColor(value));
    }

    @Info(value = "Enable or disable beam connections for the ritual preview.", params = {
            @Param(name = "value", value = "True to connect beams between ritual nodes.")
    })
    public RitualRecipeJS connectBeam(boolean value) {
        return putParameter("connectBeam", value);
    }

    @Info(value = "Enable or disable reagent index rendering in the ritual preview.", params = {
            @Param(name = "value", value = "True to show reagent indexes.")
    })
    public RitualRecipeJS displayIndexes(boolean value) {
        return putParameter("displayIndexes", value);
    }

    @Info(value = "Mark whether this ritual can be used in ritual kits.", params = {
            @Param(name = "value", value = "True if the ritual should be kittable.")
    })
    public RitualRecipeJS kittable(boolean value) {
        return putParameter("kittable", value);
    }

    @Info(value = "Set the output item created by this ritual.", params = {
            @Param(name = "value", value = "Item id such as kubejs:ritual_focus.")
    })
    public RitualRecipeJS outputItem(MnaItemId value) {
        return setKey("createsItem", value);
    }

    @Info(value = "Alias for outputItem(...).", params = {
            @Param(name = "value", value = "Item id such as kubejs:ritual_focus.")
    })
    public RitualRecipeJS output(MnaItemId value) {
        return outputItem(value);
    }

    @Info(value = "Alias for outputItem(...).", params = {
            @Param(name = "value", value = "Item id such as kubejs:ritual_focus.")
    })
    public RitualRecipeJS createsItem(MnaItemId value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS outputItem(String value) {
        return outputItem(MnaItemId.parse(value));
    }

    @HideFromJS
    public RitualRecipeJS outputItem(Item value) {
        return outputItem(MnaItemId.parse(value));
    }

    @HideFromJS
    public RitualRecipeJS outputItem(ItemStack value) {
        return outputItem(MnaItemId.parse(value));
    }

    @HideFromJS
    public RitualRecipeJS output(String value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS output(Item value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS output(ItemStack value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS createsItem(String value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS createsItem(Item value) {
        return outputItem(value);
    }

    @HideFromJS
    public RitualRecipeJS createsItem(ItemStack value) {
        return outputItem(value);
    }

    @Info(value = "Run a command when the ritual completes.", params = {
            @Param(name = "value", value = "A literal command string without the leading slash.")
    })
    public RitualRecipeJS command(String value) {
        return setKey("command", value);
    }

    @Override
    protected void validateRecipe() {
        int[][] pattern = requirePattern("pattern", "Ritual recipe pattern");
        String[] reagents = requireReagentRows();
        MnaItemId createdItem = getValue(RitualRecipeSchema.CREATES_ITEM);
        LinkedHashMap<String, Object> keys = editableObject("keys");

        if (newRecipe) {
            require(createdItem != null, "Ritual recipe outputItem must be set");
        }
        require(!keys.isEmpty(), "Ritual recipe must define at least one reagent key");

        int size = pattern.length;
        for (int row = 0; row < reagents.length; row++) {
            String reagentRow = reagents[row];
            require(reagentRow.length() == size,
                    "Ritual recipe reagent row " + row + " must be exactly " + size + " characters");
        }
        require(reagents.length == size,
                "Ritual recipe reagents must define exactly " + size + " rows to match pattern");

        int[][] displayPattern = readIntGrid(currentValue("displayPattern"));
        if (displayPattern.length > 0) {
            try {
                validateSquareOddGrid(displayPattern, "Ritual recipe displayPattern");
            } catch (IllegalArgumentException exception) {
                throw new RecipeExceptionJS(exception.getMessage());
            }
            require(displayPattern.length == size,
                    "Ritual recipe displayPattern must have the same size as pattern");
        }

        int dynamicSourceCount = 0;
        for (Map.Entry<String, Object> entry : keys.entrySet()) {
            String symbol = entry.getKey();
            require(symbol != null && symbol.length() == 1,
                    "Ritual recipe reagent key definitions must use single-character symbols");
            if (isDynamicSource(entry.getValue())) {
                dynamicSourceCount++;
            }
        }
        require(dynamicSourceCount <= 1, "Ritual recipe can define at most one dynamic source reagent");

        for (String row : reagents) {
            for (int index = 0; index < row.length(); index++) {
                char symbol = row.charAt(index);
                if (!Character.isWhitespace(symbol)) {
                    require(keys.containsKey(String.valueOf(symbol)),
                            "Ritual recipe is missing key definition for '" + symbol + "'");
                }
            }
        }
    }

    private RitualRecipeJS putReagent(MnaRitualReagent reagent) {
        char symbol = reagent.symbolChar();
        require(!Character.isWhitespace(symbol), "Ritual reagent symbol can't be whitespace");

        LinkedHashMap<String, Object> keys = editableObject("keys");
        LinkedHashMap<String, Object> value = new LinkedHashMap<>();
        value.put("item", reagent.item().scriptValue());
        if (reagent.isOptional()) {
            value.put("optional", true);
        }
        if (!reagent.consumes()) {
            value.put("consume", false);
        }
        if (reagent.isManualReturn()) {
            value.put("manual_return", true);
        }
        if (reagent.isDynamic()) {
            value.put("is_dynamic", true);
        }
        if (reagent.isDynamicSource()) {
            value.put("dynamic_source", true);
        }
        keys.put(String.valueOf(symbol), value);
        return setKey("keys", keys);
    }

    private RitualRecipeJS putParameter(String key, Object value) {
        LinkedHashMap<String, Object> parameters = editableObject("parameters");
        parameters.put(key, value);
        return setKey("parameters", parameters);
    }

    private int[][] requirePattern(String key, String label) {
        int[][] grid = readIntGrid(currentValue(key));
        require(grid.length > 0, label + " can't be empty");
        try {
            validateSquareOddGrid(grid, label);
        } catch (IllegalArgumentException exception) {
            throw new RecipeExceptionJS(exception.getMessage());
        }
        return grid;
    }

    private String[] requireReagentRows() {
        Object value = currentValue("reagents");
        String[] rows = readRows(value);
        require(rows.length > 0, "Ritual recipe reagents can't be empty");
        try {
            return MnaPatternHelper.reagentRows(rows);
        } catch (IllegalArgumentException exception) {
            throw new RecipeExceptionJS(exception.getMessage());
        }
    }

    private static boolean isDynamicSource(Object value) {
        if (!(value instanceof Map<?, ?> map)) {
            return false;
        }
        Object rawValue = map.get("dynamic_source");
        return rawValue instanceof Boolean bool && bool;
    }

    private static void validateSquareOddGrid(int[][] grid, String label) {
        requireStatic(grid.length > 0, label + " must define at least one row");

        int width = -1;
        for (int row = 0; row < grid.length; row++) {
            int[] values = grid[row];
            requireStatic(values != null && values.length > 0, label + " row " + row + " can't be empty");
            if (width == -1) {
                width = values.length;
            } else {
                requireStatic(values.length == width, label + " rows must all have the same width");
            }
        }

        requireStatic(grid.length == width, label + " must be square");
        requireStatic((grid.length & 1) == 1, label + " size must be odd");
    }

    private static int[][] readIntGrid(Object value) {
        if (value instanceof int[][] grid) {
            return copyPattern(grid);
        }
        if (value instanceof Integer[][] grid) {
            return toIntPattern(grid);
        }
        if (value instanceof Collection<?> rows) {
            int[][] result = new int[rows.size()][];
            int rowIndex = 0;
            for (Object row : rows) {
                result[rowIndex++] = readIntRow(row);
            }
            return result;
        }
        if (value != null && value.getClass().isArray()) {
            int length = Array.getLength(value);
            int[][] result = new int[length][];
            for (int row = 0; row < length; row++) {
                result[row] = readIntRow(Array.get(value, row));
            }
            return result;
        }
        return new int[0][0];
    }

    private static int[] readIntRow(Object row) {
        if (row instanceof int[] values) {
            return values.clone();
        }
        if (row instanceof Integer[] values) {
            int[] result = new int[values.length];
            for (int column = 0; column < values.length; column++) {
                result[column] = values[column] == null ? 0 : values[column];
            }
            return result;
        }
        if (row instanceof Collection<?> values) {
            int[] result = new int[values.size()];
            int column = 0;
            for (Object value : values) {
                result[column++] = toIntCell(value);
            }
            return result;
        }
        if (row != null && row.getClass().isArray()) {
            int length = Array.getLength(row);
            int[] result = new int[length];
            for (int column = 0; column < length; column++) {
                result[column] = toIntCell(Array.get(row, column));
            }
            return result;
        }
        return new int[0];
    }

    private static int toIntCell(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String text) {
            return Integer.parseInt(text);
        }
        throw new IllegalArgumentException("Unsupported ritual pattern cell value: " + value);
    }

    private static String[] readRows(Object value) {
        if (value instanceof String[] rows) {
            return rows.clone();
        }
        if (value instanceof Collection<?> rows) {
            String[] result = new String[rows.size()];
            int index = 0;
            for (Object row : rows) {
                result[index++] = String.valueOf(row);
            }
            return result;
        }
        if (value != null && value.getClass().isArray()) {
            int length = Array.getLength(value);
            String[] result = new String[length];
            for (int index = 0; index < length; index++) {
                result[index] = String.valueOf(Array.get(value, index));
            }
            return result;
        }
        return new String[0];
    }

    private static int[][] toIntPattern(Integer[][] value) {
        if (value == null) {
            return new int[0][0];
        }

        int[][] result = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            Integer[] sourceRow = value[row];
            if (sourceRow == null) {
                result[row] = null;
                continue;
            }

            result[row] = new int[sourceRow.length];
            for (int column = 0; column < sourceRow.length; column++) {
                Integer sourceValue = sourceRow[column];
                result[row][column] = sourceValue == null ? 0 : sourceValue;
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

    private static void requireStatic(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    private static String toHexColor(long value) {
        return "0x" + Long.toHexString(value);
    }
}
