package com.crychicteam.mnajs.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.crychicteam.mnajs.recipes.components.MAComponents;
import com.crychicteam.mnajs.recipes.components.RitualKeyComponent.RitualKey;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author M1hono
 */
public interface RitualRecipeSchema {
    RecipeKey<Integer[][]> PATTERN = MAComponents.INT_ARRAY_ARRAY.key("pattern");
    RecipeKey<String[]> REAGENTS = MAComponents.STRING_ARRAY.key("reagents");
    RecipeKey<Map<String, RitualKey>> KEYS = MAComponents.RITUAL_KEYS.key("keys");

    RecipeKey<Integer[][]> DISPLAY_PATTERN = MAComponents.INT_ARRAY_ARRAY.key("displayPattern").optional((Integer[][]) null);
    RecipeKey<String[]> MANAWEAVE = MAComponents.STRING_ARRAY.key("manaweave").optional(new String[0]);

    RecipeKey<Long> INNER_COLOR = NumberComponent.LONG.key("innerColor").optional(16777215L);
    RecipeKey<Long> OUTER_COLOR = NumberComponent.LONG.key("outerColor").optional(65280L);
    RecipeKey<Long> BEAM_COLOR = NumberComponent.LONG.key("beamColor").optional(16777215L);
    RecipeKey<Boolean> CONNECT_BEAM = MAComponents.BOOLEAN.key("connectBeam").optional(true);
    RecipeKey<Boolean> DISPLAY_INDEXES = MAComponents.BOOLEAN.key("displayIndexes").optional(true);
    RecipeKey<Boolean> KITTABLE = MAComponents.BOOLEAN.key("kittable").optional(true);
    RecipeKey<Integer> TIER = MAComponents.INT.key("tier").optional(0);

    RecipeKey<OutputItem> CREATES_ITEM = ItemComponents.OUTPUT.key("createsItem").optional(OutputItem.EMPTY);
    RecipeKey<String> COMMAND = MAComponents.STRING.key("command").optional("");

    class RitualRecipeJS extends RecipeJS {
        @Override
        public void serialize() {
            JsonObject json = this.json;

            if (!json.has("pattern") || !json.has("reagents") || !json.has("keys")) {
                if (!json.has("pattern")) {
                    json.add("pattern", new JsonArray());
                }
                if (!json.has("reagents")) {
                    json.add("reagents", new JsonArray());
                }
                if (!json.has("keys")) {
                    json.add("keys", new JsonObject());
                }
            }

            if (!json.has("parameters")) {
                json.add("parameters", new JsonObject());
            }

            JsonObject params = json.getAsJsonObject("parameters");
            moveParameterIfExists(json, params, "innerColor");
            moveParameterIfExists(json, params, "outerColor");
            moveParameterIfExists(json, params, "beamColor");
            moveParameterIfExists(json, params, "connectBeam");
            moveParameterIfExists(json, params, "displayIndexes");
            moveParameterIfExists(json, params, "kittable");
            moveParameterIfExists(json, params, "tier");

            if (!params.has("innerColor")) {
                params.addProperty("innerColor", "0xFFFFFF");
            }
            if (!params.has("outerColor")) {
                params.addProperty("outerColor", "0x00FF00");
            }
            if (!params.has("beamColor")) {
                params.addProperty("beamColor", "0xFFFFFF");
            }
            if (!params.has("connectBeam")) {
                params.addProperty("connectBeam", true);
            }
            if (!params.has("displayIndexes")) {
                params.addProperty("displayIndexes", true);
            }
            if (!params.has("kittable")) {
                params.addProperty("kittable", true);
            }
            if (!params.has("tier")) {
                params.addProperty("tier", 0);
            }

            super.serialize();
        }

        private void moveParameterIfExists(JsonObject source, JsonObject target, String key) {
            if (source.has(key)) {
                target.add(key, source.get(key));
                source.remove(key);
            }
        }

        public RitualRecipeJS innerColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("innerColor", hexColor);
            save();
            return this;
        }

        public RitualRecipeJS outerColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("outerColor", hexColor);
            save();
            return this;
        }

        public RitualRecipeJS beamColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("beamColor", hexColor);
            save();
            return this;
        }

        public RitualRecipeJS connectBeam(boolean connect) {
            JsonObject params = ensureParameters();
            params.addProperty("connectBeam", connect);
            save();
            return this;
        }

        public RitualRecipeJS displayIndexes(boolean display) {
            JsonObject params = ensureParameters();
            params.addProperty("displayIndexes", display);
            save();
            return this;
        }

        public RitualRecipeJS kittable(boolean kittable) {
            JsonObject params = ensureParameters();
            params.addProperty("kittable", kittable);
            save();
            return this;
        }

        public RitualRecipeJS tier(int tier) {
            JsonObject params = ensureParameters();
            params.addProperty("tier", tier);
            save();
            return this;
        }

        public RitualRecipeJS createsItem(OutputItem item) {
            if (item.isEmpty()) {
                json.remove("createsItem");
            } else {
                String itemId = ForgeRegistries.ITEMS.getKey(item.item.getItem()).toString();
                json.addProperty("createsItem", itemId);
            }
            save();
            return this;
        }

        public RitualRecipeJS command(String command) {
            if (command == null || command.isEmpty()) {
                json.remove("command");
            } else {
                json.addProperty("command", command);
            }
            save();
            return this;
        }

        public RitualRecipeJS pattern(int[][] pattern) {
            JsonArray patternArray = new JsonArray();
            for (int[] row : pattern) {
                JsonArray rowArray = new JsonArray();
                for (int cell : row) {
                    rowArray.add(cell);
                }
                patternArray.add(rowArray);
            }
            json.add("pattern", patternArray);
            save();
            return this;
        }

        public RitualRecipeJS displayPattern(int[][] pattern) {
            if (pattern == null) {
                json.remove("displayPattern");
                return this;
            }

            JsonArray patternArray = new JsonArray();
            for (int[] row : pattern) {
                JsonArray rowArray = new JsonArray();
                for (int cell : row) {
                    rowArray.add(cell);
                }
                patternArray.add(rowArray);
            }
            json.add("displayPattern", patternArray);
            save();
            return this;
        }

        public RitualRecipeJS reagents(String[] reagents) {
            JsonArray reagentsArray = new JsonArray();
            for (String row : reagents) {
                reagentsArray.add(row);
            }
            json.add("reagents", reagentsArray);
            save();
            return this;
        }

        public RitualRecipeJS key(String key, InputItem item) {
            return key(key, item, false, true, false, false, false);
        }

        public RitualRecipeJS key(String key, InputItem item, boolean optional, boolean consume,
                                  boolean manualReturn, boolean isDynamic, boolean dynamicSource) {
            Map<String, RitualKey> keys = getValue(KEYS);
            if (keys == null) {
                keys = new HashMap<>();
            }

            String itemId = ForgeRegistries.ITEMS.getKey(Arrays.stream(item.ingredient.getItems()).findFirst().get().getItem()).toString();
            keys.put(key, new RitualKey(itemId, optional, consume, manualReturn, isDynamic, dynamicSource));

            setValue(KEYS, keys);
            save();
            return this;
        }

        public RitualRecipeJS manaweave(String[] patterns) {
            if (patterns == null || patterns.length == 0) {
                json.remove("manaweave");
                return this;
            }

            JsonArray manaweaveArray = new JsonArray();
            for (String pattern : patterns) {
                manaweaveArray.add(pattern);
            }
            json.add("manaweave", manaweaveArray);
            save();
            return this;
        }

        private JsonObject ensureParameters() {
            if (!json.has("parameters")) {
                json.add("parameters", new JsonObject());
            }
            return json.getAsJsonObject("parameters");
        }

        private JsonObject ensureKeys() {
            if (!json.has("keys")) {
                json.add("keys", new JsonObject());
            }
            return json.getAsJsonObject("keys");
        }
    }

    RecipeSchema RITUAL_RECIPE = new RecipeSchema(
            RitualRecipeJS.class,
            RitualRecipeJS::new,
            PATTERN, REAGENTS, KEYS, DISPLAY_PATTERN, MANAWEAVE,
            INNER_COLOR, OUTER_COLOR, BEAM_COLOR, CONNECT_BEAM,
            DISPLAY_INDEXES, KITTABLE, TIER, CREATES_ITEM, COMMAND
    );
}