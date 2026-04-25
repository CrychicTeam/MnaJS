package com.pickaid.mnajs.recipes.schema;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.recipe.RitualRecipeJS;
import com.pickaid.mnajs.recipes.component.ItemOrTagComponent;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TinyMap;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * Ritual Recipe Schema for Mana and Artifice
 *
 * @author M1hono
 */
public interface RitualRecipeSchema extends TierBaseSchema {
    RecipeComponent<TinyMap<Character, RecipeComponentBuilderMap>> RITUAL_PATTERN_KEY = new RecipeComponentBuilder(6)
            .add(ItemOrTagComponent.ITEM_OR_TAG_COMPONENT.key("item"))
            .add(BooleanComponent.BOOLEAN.key("optional").optional(false))
            .add(BooleanComponent.BOOLEAN.key("manual_return").optional(false))
            .add(BooleanComponent.BOOLEAN.key("is_dynamic").optional(false))
            .add(BooleanComponent.BOOLEAN.key("dynamic_source").optional(false))
            .add(BooleanComponent.BOOLEAN.key("consume").optional(true)).asPatternKey();
    RecipeComponent<RecipeComponentBuilderMap> RITUAL_PARAMETERS = new RecipeComponentBuilder(6)
            .add(StringComponent.NON_BLANK.key("innerColor").defaultOptional().allowEmpty())
            .add(StringComponent.NON_BLANK.key("outerColor").defaultOptional().allowEmpty())
            .add(StringComponent.NON_BLANK.key("beamColor").defaultOptional().allowEmpty())
            .add(BooleanComponent.BOOLEAN.key("connectBeam").optional(true))
            .add(BooleanComponent.BOOLEAN.key("displayIndexes").optional(true))
            .add(BooleanComponent.BOOLEAN.key("kittable").optional(true));
    RecipeComponent<int[][]> INT_GRID_COMPONENT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return int.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, int[][] value) {
            JsonArray patternArray = new JsonArray();

            if (value == null) {
                return patternArray;
            }

            for (int[] row : value) {
                JsonArray rowArray = new JsonArray();
                if (row != null) {
                    for (int cell : row) {
                        rowArray.add(cell);
                    }
                }
                patternArray.add(rowArray);
            }

            return patternArray;
        }

        @Override
        public int[][] read(RecipeJS recipe, Object from) {
            return readGrid(from);
        }
    };

    RecipeKey<int[][]> PATTERN = INT_GRID_COMPONENT.key("pattern").optional((int[][]) null);
    RecipeKey<String[]> REAGENTS = StringComponent.ANY.asArray().key("reagents").optional(type -> new String[0]).allowEmpty();
    RecipeKey<TinyMap<Character, RecipeComponentBuilderMap>> KEYS = RITUAL_PATTERN_KEY.key("keys").defaultOptional().allowEmpty();
    RecipeKey<int[][]> DISPLAY_PATTERN = INT_GRID_COMPONENT.key("displayPattern").optional((int[][]) null);
    RecipeKey<MnaManaweavePatternId[]> MANAWEAVE = MnaRecipeComponents.MANAWEAVE_PATTERN_ID.asArray().key("manaweave").optional((MnaManaweavePatternId[]) null);
    RecipeKey<RecipeComponentBuilderMap> PARAMETERS = RITUAL_PARAMETERS.key("parameters").defaultOptional().allowEmpty();
    RecipeKey<MnaItemId> CREATES_ITEM = MnaRecipeComponents.ITEM_ID.key("createsItem").optional((MnaItemId) null);
    RecipeKey<String> COMMAND = StringComponent.ANY.key("command").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            RitualRecipeJS.class,
            RitualRecipeJS::new,
            CREATES_ITEM, PATTERN, REAGENTS, KEYS, DISPLAY_PATTERN, TIER, FACTION, MANAWEAVE,
            PARAMETERS, COMMAND
    ).constructor()
            .constructor((recipe, schemaType, keys, from) -> {
                recipe.setValue(PATTERN, from.getValue(recipe, PATTERN));
                recipe.setValue(REAGENTS, from.getValue(recipe, REAGENTS));
                recipe.set("keys", Map.of());
            }, PATTERN, REAGENTS);

    private static int[][] readGrid(Object from) {
        if (from instanceof int[][] value) {
            return copyGrid(value);
        }
        if (from instanceof Integer[][] value) {
            return fromIntegerGrid(value);
        }
        if (from instanceof JsonArray patternArray) {
            int[][] pattern = new int[patternArray.size()][];
            for (int row = 0; row < patternArray.size(); row++) {
                JsonElement rowElement = patternArray.get(row);
                if (rowElement instanceof JsonArray rowArray) {
                    int[] values = new int[rowArray.size()];
                    for (int column = 0; column < rowArray.size(); column++) {
                        JsonElement cell = rowArray.get(column);
                        if (cell != null && cell.isJsonPrimitive()) {
                            values[column] = cell.getAsInt();
                        }
                    }
                    pattern[row] = values;
                } else {
                    pattern[row] = new int[0];
                }
            }
            return pattern;
        }
        if (from instanceof List<?> patternList) {
            int[][] pattern = new int[patternList.size()][];
            for (int row = 0; row < patternList.size(); row++) {
                pattern[row] = readRow(patternList.get(row));
            }
            return pattern;
        }
        if (from != null && from.getClass().isArray()) {
            int length = Array.getLength(from);
            int[][] pattern = new int[length][];
            for (int row = 0; row < length; row++) {
                pattern[row] = readRow(Array.get(from, row));
            }
            return pattern;
        }
        return new int[0][0];
    }

    private static int[][] copyGrid(int[][] value) {
        int[][] copy = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            copy[row] = value[row] == null ? null : value[row].clone();
        }
        return copy;
    }

    private static int[][] fromIntegerGrid(Integer[][] value) {
        int[][] result = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            Integer[] sourceRow = value[row];
            if (sourceRow == null) {
                result[row] = null;
                continue;
            }

            result[row] = new int[sourceRow.length];
            for (int column = 0; column < sourceRow.length; column++) {
                Integer cell = sourceRow[column];
                result[row][column] = cell == null ? 0 : cell;
            }
        }
        return result;
    }

    private static int[] readRow(Object row) {
        if (row instanceof int[] value) {
            return value.clone();
        }
        if (row instanceof Integer[] value) {
            int[] result = new int[value.length];
            for (int column = 0; column < value.length; column++) {
                Integer cell = value[column];
                result[column] = cell == null ? 0 : cell;
            }
            return result;
        }
        if (row instanceof List<?> list) {
            int[] result = new int[list.size()];
            for (int column = 0; column < list.size(); column++) {
                result[column] = readCell(list.get(column));
            }
            return result;
        }
        if (row != null && row.getClass().isArray()) {
            int length = Array.getLength(row);
            int[] result = new int[length];
            for (int column = 0; column < length; column++) {
                result[column] = readCell(Array.get(row, column));
            }
            return result;
        }
        return new int[0];
    }

    private static int readCell(Object cell) {
        if (cell instanceof Number number) {
            return number.intValue();
        }
        if (cell instanceof String value) {
            return Integer.parseInt(value);
        }
        return 0;
    }
}
