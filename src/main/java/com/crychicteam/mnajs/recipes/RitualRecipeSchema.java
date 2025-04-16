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
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ritual Recipe Schema for Mana and Artifice
 *
 * @author M1hono
 */
public interface RitualRecipeSchema {
    /* Required recipe components */

    /**
     * The rune pattern for the ritual
     * A 2D integer array where 0 means no rune, 1-16 represent rune positions (can be used for numbered sequence)
     */
    RecipeKey<Integer[][]> PATTERN = MAComponents.INT_ARRAY_ARRAY.key("pattern");

    /**
     * String array defining reagent positions
     * Each character corresponds to a position, space means no item, other characters correspond to items defined in keys
     */
    RecipeKey<String[]> REAGENTS = MAComponents.STRING_ARRAY.key("reagents");

    /**
     * Maps characters to specific items
     * Example: {'a': {item: 'minecraft:diamond', consume: false}}
     */
    RecipeKey<Map<String, RitualKey>> KEYS = MAComponents.RITUAL_KEYS.key("keys");

    /* Optional recipe components */

    /**
     * Display pattern for the ritual (optional)
     * If omitted, the regular pattern will be used
     * Only affects visual appearance, not functionality
     */
    RecipeKey<Integer[][]> DISPLAY_PATTERN = MAComponents.INT_ARRAY_ARRAY.key("displayPattern").optional((Integer[][]) null);

    /**
     * Manaweave patterns associated with the ritual (optional)
     * String array, each element is a manaweave pattern ID
     */
    RecipeKey<String[]> MANAWEAVE = MAComponents.STRING_ARRAY.key("manaweave").optional(new String[0]);

    /* Visual parameters */

    /**
     * Inner circle color (hex format)
     * Default: white (0xFFFFFF)
     */
    RecipeKey<Long> INNER_COLOR = NumberComponent.LONG.key("innerColor").optional(16777215L);

    /**
     * Outer circle color (hex format)
     * Default: green (0x00FF00)
     */
    RecipeKey<Long> OUTER_COLOR = NumberComponent.LONG.key("outerColor").optional(65280L);

    /**
     * Beam color (hex format)
     * Default: white (0xFFFFFF)
     */
    RecipeKey<Long> BEAM_COLOR = NumberComponent.LONG.key("beamColor").optional(16777215L);

    /**
     * Whether to connect beams
     * Default: true
     */
    RecipeKey<Boolean> CONNECT_BEAM = MAComponents.BOOLEAN.key("connectBeam").optional(true);

    /**
     * Whether to display indexes
     * Default: true
     */
    RecipeKey<Boolean> DISPLAY_INDEXES = MAComponents.BOOLEAN.key("displayIndexes").optional(true);

    /**
     * Whether the ritual can be created with a ritual kit
     * Default: true
     */
    RecipeKey<Boolean> KITTABLE = MAComponents.BOOLEAN.key("kittable").optional(true);

    /**
     * Ritual tier/difficulty
     * Default: 0
     */
    RecipeKey<Integer> TIER = NumberComponent.INT.key("tier").optional(0);

    /* Result configuration */

    /**
     * Item created by the ritual
     * Optional, default: empty
     */
    RecipeKey<OutputItem> CREATES_ITEM = ItemComponents.OUTPUT.key("createsItem").optional(OutputItem.EMPTY);

    /**
     * Command executed by the ritual
     * Optional, default: empty string
     */
    RecipeKey<String> COMMAND = MAComponents.STRING.key("command").optional("");

    /**
     * JS implementation class for ritual recipes
     */
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

        /**
         * Sets the ritual ID/name
         */
        @Info("Sets the ritual ID/name. Important: MA ritual effects require a specific ID format, usually 'manaandartifice:rituals/name' or 'mna:rituals/name'. Example: ritualName(new ResourceLocation('mna:rituals/earth_mote'))")
        public RitualRecipeJS ritualName(ResourceLocation name) {
            this.id = name;
            save();
            return this;
        }

        /**
         * Sets the inner circle color
         */
        @Info("Sets the inner circle color. Parameter is a hex color string, example: '0x644828'")
        public RitualRecipeJS innerColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("innerColor", hexColor);
            save();
            return this;
        }

        /**
         * Sets the outer circle color
         */
        @Info("Sets the outer circle color. Parameter is a hex color string, example: '0x444444'")
        public RitualRecipeJS outerColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("outerColor", hexColor);
            save();
            return this;
        }

        /**
         * Sets the beam color
         */
        @Info("Sets the beam color. Parameter is a hex color string, example: '0x47260a'")
        public RitualRecipeJS beamColor(String hexColor) {
            JsonObject params = ensureParameters();
            params.addProperty("beamColor", hexColor);
            save();
            return this;
        }

        /**
         * Sets whether to connect beams
         */
        @Info("Sets whether beams connect to the center. true connects beams, false disconnects them")
        public RitualRecipeJS connectBeam(boolean connect) {
            JsonObject params = ensureParameters();
            params.addProperty("connectBeam", connect);
            save();
            return this;
        }

        /**
         * Sets whether to display indexes
         */
        @Info("Sets whether to display indexes. true shows indexes, false hides them")
        public RitualRecipeJS displayIndexes(boolean display) {
            JsonObject params = ensureParameters();
            params.addProperty("displayIndexes", display);
            save();
            return this;
        }

        /**
         * Sets whether the ritual can be created with a ritual kit
         */
        @Info("Sets whether the ritual can be created with a ritual kit. true allows kit creation, false prevents it")
        public RitualRecipeJS kittable(boolean kittable) {
            JsonObject params = ensureParameters();
            params.addProperty("kittable", kittable);
            save();
            return this;
        }

        /**
         * Sets the ritual tier/difficulty
         */
        @Info("Sets the ritual tier/difficulty. Tier value starts from 0")
        public RitualRecipeJS tier(int tier) {
            JsonObject params = ensureParameters();
            params.addProperty("tier", tier);
            save();
            return this;
        }

        /**
         * Sets the item created by the ritual
         */
        @Info("Sets the item created by the ritual. Example: createsItem(Item.of('mna:mote_earth'))")
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

        /**
         * Sets the command executed by the ritual
         */
        @Info("Sets the command executed by the ritual, example: command('/give @p minecraft:diamond')")
        public RitualRecipeJS command(String command) {
            if (command == null || command.isEmpty()) {
                json.remove("command");
            } else {
                json.addProperty("command", command);
            }
            save();
            return this;
        }

        /**
         * Sets the rune pattern for the ritual
         */
        @Info("Sets the rune pattern for the ritual. 2D integer array where 0 means no rune, 1-16 represent rune positions (can be used for numbered sequence). Example: pattern([[0,1,0,0,0,16,0], [0,2,0,0,0,15,0], ...])")
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

        /**
         * Sets the display pattern for the ritual (optional)
         */
        @Info("Sets the display pattern for the ritual (optional). Same format as pattern, but only for display purposes")
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

        /**
         * Sets the reagent positions
         */
        @Info("Sets reagent positions. String array where each character corresponds to a key. Example: reagents([' G   G ', ' D   D ', '  S S  ', '   O   ', '  S S  ', ' D   D ', ' GAOAG '])")
        public RitualRecipeJS reagents(String[] reagents) {
            JsonArray reagentsArray = new JsonArray();
            for (String row : reagents) {
                reagentsArray.add(row);
            }
            json.add("reagents", reagentsArray);
            save();
            return this;
        }

        /**
         * Adds a simple ritual material key
         */
        @Info("Adds a simple ritual material key. Example: key('S', Ingredient.of('minecraft:stone'))")
        public RitualRecipeJS key(String key, InputItem item) {
            return key(key, item, false, true, false, false, false);
        }

        /**
         * Adds a fully configured ritual material key
         */
        @Info("Adds a fully configured ritual material key. Parameters: key-character reference, item-ingredient, optional-whether optional, consume-whether consumed")
        public RitualRecipeJS key(String key, InputItem item, boolean optional, boolean consume,
                                  boolean manualReturn, boolean isDynamic, boolean dynamicSource) {
            JsonObject keysObj = ensureKeys();
            JsonObject keyObj = new JsonObject();

            String itemId;
            try {
                String itemString = item.toString();
                if (itemString.startsWith("#")) {
                    itemId = itemString.substring(1);
                } else {
                    itemId = ForgeRegistries.ITEMS.getKey(item.ingredient.kjs$getFirst().getItem()).toString();
                }
            } catch (Exception e) {
                itemId = "minecraft:air";
            }
            keyObj.addProperty("item", itemId);
            if (optional) {
                keyObj.addProperty("optional", true);
            }
            keyObj.addProperty("consume", consume);
            if (manualReturn) {
                keyObj.addProperty("manual_return", true);
            }
            if (isDynamic) {
                keyObj.addProperty("is_dynamic", true);
            }
            if (dynamicSource) {
                keyObj.addProperty("dynamic_source", true);
            }
            keysObj.add(key, keyObj);
            save();
            return this;
        }

        /**
         * Sets the manaweave patterns associated with the ritual
         */
        @Info("Sets manaweave patterns associated with the ritual. Example: manaweave(['mna:manaweave_patterns/square', 'mna:manaweave_patterns/diamond', 'mna:manaweave_patterns/square'])")
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