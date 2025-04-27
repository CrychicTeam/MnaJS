package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RitualRecipeBuilder {
    private int[][] pattern;
    private String[] reagentPattern;
    private Map<Character, ReagentInfo> reagentMap = new HashMap<>();

    private int[][] displayPattern;
    private String[] manaweavePatterns;
    private Long innerColor;
    private Long outerColor;
    private Long beamColor;
    private Boolean connectBeam;
    private Boolean displayIndexes;
    private Boolean kittable;
    private Integer tier;
    private ItemStack createdItem;
    private String command;

    @HideFromJS
    public static class ReagentInfo {
        private final ItemStack item;
        private boolean optional = false;
        private boolean consume = true;
        private boolean is_dynamic = false;
        private boolean dynamic_source = false;
        private boolean manual_return = false;

        public ReagentInfo(ItemStack item) {
            this.item = item;
        }

        public ReagentInfo optional(boolean value) {
            this.optional = value;
            return this;
        }

        public ReagentInfo consume(boolean value) {
            this.consume = value;
            return this;
        }

        public ReagentInfo dynamic(boolean value) {
            this.is_dynamic = value;
            return this;
        }

        public ReagentInfo dynamicSource(boolean value) {
            this.dynamic_source = value;
            return this;
        }

        public ReagentInfo manualReturn(boolean value) {
            this.manual_return = value;
            return this;
        }

        public ItemStack getItem() {
            return item;
        }

        public boolean isOptional() {
            return optional;
        }

        public boolean shouldConsume() {
            return consume;
        }

        public boolean isDynamic() {
            return is_dynamic;
        }

        public boolean isDynamicSource() {
            return dynamic_source;
        }

        public boolean isManualReturn() {
            return manual_return;
        }
    }

    @Info("The method that is mandatory to set the pattern of the ritual")
    public RitualRecipeBuilder pattern(int[][] pattern) {
        this.pattern = Objects.requireNonNull(pattern, "Pattern cannot be null");
        return this;
    }

    @Info("The method that is optional to set the display pattern of the ritual")
    public RitualRecipeBuilder displayPattern(int[][] displayPattern) {
        this.displayPattern = displayPattern;
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents")
    public RitualRecipeBuilder reagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents")
    public RitualRecipeBuilder reagent(char key, ItemStack item, boolean optional, boolean consume) {
        this.reagentMap.put(key, new ReagentInfo(item)
                .optional(optional)
                .consume(consume));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic source")
    public RitualRecipeBuilder dynamicSourceReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item).dynamicSource(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic boolean")
    public RitualRecipeBuilder dynamicReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item).dynamic(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with manual return")
    public RitualRecipeBuilder manualReturnReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item).manualReturn(true));
        return this;
    }

    @Info("The method that is mandatory to set the pattern of the reagents")
    public RitualRecipeBuilder reagentPattern(String[] reagentPattern) {
        this.reagentPattern = Objects.requireNonNull(reagentPattern, "Reagent pattern cannot be null");
        return this;
    }

    @Info("The method that is optional to set the manaweave patterns of the ritual")
    public RitualRecipeBuilder manaweavePatterns(String[] patterns) {
        this.manaweavePatterns = patterns;
        return this;
    }

    @Info("The method that is optional to add a manaweave pattern to the patterns' list and will finally add to the ritual")
    public RitualRecipeBuilder addManaweavePattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return this;
        }

        List<String> patterns = new ArrayList<>();
        if (manaweavePatterns != null) {
            for (String existingPattern : manaweavePatterns) {
                patterns.add(existingPattern);
            }
        }
        patterns.add(pattern);
        this.manaweavePatterns = patterns.toArray(new String[0]);
        return this;
    }

    @Info("The method that is optional to set the inner color of the ritual")
    public RitualRecipeBuilder innerColor(long color) {
        this.innerColor = color;
        return this;
    }

    @Info("The method that is optional to set the outer color of the ritual")
    public RitualRecipeBuilder outerColor(long color) {
        this.outerColor = color;
        return this;
    }

    @Info("The method that is optional to set the beam color of the ritual")
    public RitualRecipeBuilder beamColor(long color) {
        this.beamColor = color;
        return this;
    }

    @Info("The method that is optional to set the beam connection of the ritual")
    public RitualRecipeBuilder connectBeam(boolean connect) {
        this.connectBeam = connect;
        return this;
    }

    @Info("The method that is optional to set the display indexes of the ritual")
    public RitualRecipeBuilder displayIndexes(boolean display) {
        this.displayIndexes = display;
        return this;
    }

    @Info("The method that is optional to set the kittable of the ritual")
    public RitualRecipeBuilder kittable(boolean kittable) {
        this.kittable = kittable;
        return this;
    }

    @Info("The method that is optional to set the tier of the ritual")
    public RitualRecipeBuilder tier(int tier) {
        this.tier = tier;
        return this;
    }

    @Info("The method that is optional to set the item that will be created by the ritual")
    public RitualRecipeBuilder createsItem(ItemStack item) {
        this.createdItem = item;
        return this;
    }

    @Info("The method that is optional to set the command that will be executed when the ritual is triggered")
    public RitualRecipeBuilder command(String command) {
        this.command = command;
        return this;
    }

    @Info("The method that is mandatory to build the ritual json object to use it in custom()")
    public JsonObject build() {
        validateBuildState();
        JsonObject json = new JsonObject();
        json.addProperty("type", "mna:ritual");

        if (tier != null) {
            json.addProperty("tier", tier);
        }
        JsonArray patternArray = new JsonArray();
        for (int[] row : pattern) {
            JsonArray rowArray = new JsonArray();
            for (int value : row) {
                rowArray.add(value);
            }
            patternArray.add(rowArray);
        }
        json.add("pattern", patternArray);
        if (displayPattern != null) {
            JsonArray displayPatternArray = new JsonArray();
            for (int[] row : displayPattern) {
                JsonArray rowArray = new JsonArray();
                for (int value : row) {
                    rowArray.add(value);
                }
                displayPatternArray.add(rowArray);
            }
            json.add("displayPattern", displayPatternArray);
        }
        JsonArray reagentArray = new JsonArray();
        for (String row : reagentPattern) {
            reagentArray.add(row);
        }
        json.add("reagents", reagentArray);
        JsonObject keysObject = new JsonObject();
        for (char key : reagentMap.keySet()) {
            ReagentInfo reagent = reagentMap.get(key);
            JsonObject reagentObject = new JsonObject();

            reagentObject.addProperty("item", reagent.getItem().kjs$getIdLocation().toString());

            if (reagent.isOptional()) {
                reagentObject.addProperty("optional", true);
            }
            if (!reagent.shouldConsume()) {
                reagentObject.addProperty("consume", false);
            }
            if (reagent.isManualReturn()) {
                reagentObject.addProperty("manual_return", true);
            }
            if (reagent.isDynamic()) {
                reagentObject.addProperty("is_dynamic", true);
            }
            if (reagent.isDynamicSource()) {
                reagentObject.addProperty("dynamic_source", true);
            }

            keysObject.add(String.valueOf(key), reagentObject);
        }
        json.add("keys", keysObject);

        // Optional manaweave patterns
        if (manaweavePatterns != null && manaweavePatterns.length > 0) {
            JsonArray manaweaveArray = new JsonArray();
            for (String pattern : manaweavePatterns) {
                manaweaveArray.add(pattern);
            }
            json.add("manaweave", manaweaveArray);
        }
        JsonObject paramsObject = new JsonObject();
        boolean hasParams = false;

        if (innerColor != null) {
            paramsObject.addProperty("innerColor", "0x" + Long.toHexString(innerColor));
            hasParams = true;
        }
        if (outerColor != null) {
            paramsObject.addProperty("outerColor", "0x" + Long.toHexString(outerColor));
            hasParams = true;
        }
        if (beamColor != null) {
            paramsObject.addProperty("beamColor", "0x" + Long.toHexString(beamColor));
            hasParams = true;
        }
        if (connectBeam != null) {
            paramsObject.addProperty("connectBeam", connectBeam);
            hasParams = true;
        }
        if (displayIndexes != null) {
            paramsObject.addProperty("displayIndexes", displayIndexes);
            hasParams = true;
        }
        if (kittable != null) {
            paramsObject.addProperty("kittable", kittable);
            hasParams = true;
        }
        if (tier != null) {
            paramsObject.addProperty("tier", tier);
            hasParams = true;
        }
        if (hasParams) {
            json.add("parameters", paramsObject);
        }
        if (createdItem != null) {
            ResourceLocation itemId = createdItem.kjs$getIdLocation();
            if (itemId != null) {
                json.addProperty("createsItem", itemId.toString());
            }
        }
        if (command != null && !command.isEmpty()) {
            json.addProperty("command", command);
        }

        return json;
    }

    private void validateBuildState() {
        if (pattern == null) {
            throw new IllegalStateException("Pattern must be set (REQUIRED)");
        }

        if (reagentPattern == null) {
            throw new IllegalStateException("Reagent pattern must be set (REQUIRED)");
        }

        if (reagentMap.isEmpty()) {
            throw new IllegalStateException("At least one reagent must be defined (REQUIRED)");
        }

        int size = pattern.length;
        for (int[] row : pattern) {
            if (row.length != size) {
                throw new IllegalStateException("Pattern must be square");
            }
        }

        if (reagentPattern.length != size) {
            throw new IllegalStateException("Reagent pattern must have same number of rows as pattern");
        }

        boolean dynamicSourceFound = false;
        for (char key : reagentMap.keySet()) {
            ReagentInfo reagent = reagentMap.get(key);
            if (reagent.isDynamicSource()) {
                if (dynamicSourceFound) {
                    throw new IllegalStateException("Only one dynamic source allowed");
                }
                dynamicSourceFound = true;
            }
        }

        for (String row : reagentPattern) {
            for (char c : row.toCharArray()) {
                if (c != ' ' && !reagentMap.containsKey(c)) {
                    throw new IllegalStateException("Reagent pattern uses undefined key: " + c);
                }
            }
        }
    }
}