package com.pickaid.mnajs.recipes.schema;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.ConsoleJS;

import java.util.List;

public interface ManaweavingPatternSchema extends TierBaseSchema {
    RecipeComponent<byte[][]> BYTE_ARRAY_ARRAY_COMPONENT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return byte[][].class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, byte[][] value) {
            JsonArray patternArray = new JsonArray();

            if (value.length != 11) {
                ConsoleJS.SERVER.error("Manaweave Pattern Array Bounds must be 11x11");
                return patternArray;
            }

            for (int i = 0; i < value.length; i++) {
                if (value[i].length != 11) {
                    ConsoleJS.SERVER.error("Manaweave Pattern Array Bounds must be 11x11");
                    return patternArray;
                }

                JsonArray rowArray = new JsonArray();
                for (int j = 0; j < value[i].length; j++) {
                    rowArray.add(value[i][j]);
                }
                patternArray.add(rowArray);
            }

            return patternArray;
        }

        @Override
        public byte[][] read(RecipeJS recipe, Object from) {
            if (from instanceof JsonArray patternArray) {
                byte[][] pattern = new byte[11][11];

                for (int i = 0; i < Math.min(patternArray.size(), 11); i++) {
                    JsonElement rowElement = patternArray.get(i);

                    if (rowElement instanceof JsonArray rowArray) {
                        for (int j = 0; j < Math.min(rowArray.size(), 11); j++) {
                            JsonElement cell = rowArray.get(j);
                            if (cell.isJsonPrimitive()) {
                                pattern[i][j] = cell.getAsJsonPrimitive().getAsByte();
                            }
                        }
                    }
                }

                return pattern;
            } else if (from instanceof List<?> patternList) {
                byte[][] pattern = new byte[11][11];

                for (int i = 0; i < Math.min(patternList.size(), 11); i++) {
                    Object row = patternList.get(i);

                    if (row instanceof List<?> rowList) {
                        for (int j = 0; j < Math.min(rowList.size(), 11); j++) {
                            Object cell = rowList.get(j);

                            if (cell instanceof Number) {
                                pattern[i][j] = ((Number) cell).byteValue();
                            } else if (cell instanceof String) {
                                try {
                                    pattern[i][j] = Byte.parseByte((String) cell);
                                } catch (NumberFormatException e) {
                                    pattern[i][j] = 0;
                                }
                            }
                        }
                    } else if (row instanceof byte[]) {
                        byte[] rowArray = (byte[]) row;
                        System.arraycopy(rowArray, 0, pattern[i], 0, Math.min(rowArray.length, 11));
                    }
                }
                return pattern;
            }

            return new byte[11][11];
        }
    };

    RecipeKey<byte[][]> PATTERN = BYTE_ARRAY_ARRAY_COMPONENT.key("pattern");
    RecipeSchema SCHEMA = new RecipeSchema(PATTERN, TIER, FACTION);
}