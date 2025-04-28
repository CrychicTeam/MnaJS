package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class RitualRecipeBuilder extends MABaseBuilder {
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
    private ResourceLocation createdItem;
    private String command;

    @HideFromJS
    public static class ReagentInfo {
        private final ResourceLocation item;
        private boolean optional = false;
        private boolean consume = true;
        private boolean is_dynamic = false;
        private boolean dynamic_source = false;
        private boolean manual_return = false;

        public ReagentInfo(ResourceLocation item) {
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

        public ResourceLocation getItem() {
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

    @Info("The method that is mandatory to set the key of the reagents using an ItemStack")
    public RitualRecipeBuilder reagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item.getItem().kjs$getIdLocation()));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents using an Item")
    public RitualRecipeBuilder reagent(char key, Item item) {
        this.reagentMap.put(key, new ReagentInfo(ForgeRegistries.ITEMS.getKey(item)));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents using a ResourceLocation")
    public RitualRecipeBuilder reagent(char key, ResourceLocation item) {
        this.reagentMap.put(key, new ReagentInfo(MATags.lookupItem(item).getItem().kjs$getIdLocation()));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents using a string")
    public RitualRecipeBuilder reagent(char key, String item) {
        return reagent(key, new ResourceLocation(item));
    }

    @Info("The method that is mandatory to set the key of the reagents with options")
    public RitualRecipeBuilder reagent(char key, ItemStack item, boolean optional, boolean consume) {
        this.reagentMap.put(key, new ReagentInfo(item.getItem().kjs$getIdLocation())
                .optional(optional)
                .consume(consume));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with options using an Item")
    public RitualRecipeBuilder reagent(char key, Item item, boolean optional, boolean consume) {
        this.reagentMap.put(key, new ReagentInfo(ForgeRegistries.ITEMS.getKey(item))
                .optional(optional)
                .consume(consume));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with options using a ResourceLocation")
    public RitualRecipeBuilder reagent(char key, ResourceLocation item, boolean optional, boolean consume) {
        this.reagentMap.put(key, new ReagentInfo(MATags.lookupItem(item).getItem().kjs$getIdLocation())
                .optional(optional)
                .consume(consume));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with options using a string")
    public RitualRecipeBuilder reagent(char key, String item, boolean optional, boolean consume) {
        return reagent(key, new ResourceLocation(item), optional, consume);
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic source")
    public RitualRecipeBuilder dynamicSourceReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item.getItem().kjs$getIdLocation()).dynamicSource(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic source using an Item")
    public RitualRecipeBuilder dynamicSourceReagent(char key, Item item) {
        this.reagentMap.put(key, new ReagentInfo(ForgeRegistries.ITEMS.getKey(item)).dynamicSource(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic source using a ResourceLocation")
    public RitualRecipeBuilder dynamicSourceReagent(char key, ResourceLocation item) {
        this.reagentMap.put(key, new ReagentInfo(MATags.lookupItem(item).getItem().kjs$getIdLocation()).dynamicSource(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic source using a string")
    public RitualRecipeBuilder dynamicSourceReagent(char key, String item) {
        return dynamicSourceReagent(key, new ResourceLocation(item));
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic boolean")
    public RitualRecipeBuilder dynamicReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item.getItem().kjs$getIdLocation()).dynamic(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic boolean using an Item")
    public RitualRecipeBuilder dynamicReagent(char key, Item item) {
        this.reagentMap.put(key, new ReagentInfo(ForgeRegistries.ITEMS.getKey(item)).dynamic(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic boolean using a ResourceLocation")
    public RitualRecipeBuilder dynamicReagent(char key, ResourceLocation item) {
        this.reagentMap.put(key, new ReagentInfo(MATags.lookupItem(item).getItem().kjs$getIdLocation()).dynamic(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with dynamic boolean using a string")
    public RitualRecipeBuilder dynamicReagent(char key, String item) {
        return dynamicReagent(key, new ResourceLocation(item));
    }

    @Info("The method that is mandatory to set the key of the reagents with manual return")
    public RitualRecipeBuilder manualReturnReagent(char key, ItemStack item) {
        this.reagentMap.put(key, new ReagentInfo(item.getItem().kjs$getIdLocation()).manualReturn(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with manual return using an Item")
    public RitualRecipeBuilder manualReturnReagent(char key, Item item) {
        this.reagentMap.put(key, new ReagentInfo(ForgeRegistries.ITEMS.getKey(item)).manualReturn(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with manual return using a ResourceLocation")
    public RitualRecipeBuilder manualReturnReagent(char key, ResourceLocation item) {
        this.reagentMap.put(key, new ReagentInfo(MATags.lookupItem(item).getItem().kjs$getIdLocation()).manualReturn(true));
        return this;
    }

    @Info("The method that is mandatory to set the key of the reagents with manual return using a string")
    public RitualRecipeBuilder manualReturnReagent(char key, String item) {
        return manualReturnReagent(key, new ResourceLocation(item));
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
            Collections.addAll(patterns, manaweavePatterns);
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

    @Info("The method that is optional to set the item that will be created by the ritual using an ItemStack")
    public RitualRecipeBuilder createsItem(ItemStack item) {
        this.createdItem = item.getItem().kjs$getIdLocation();
        return this;
    }

    @Info("The method that is optional to set the item that will be created by the ritual using an Item")
    public RitualRecipeBuilder createsItem(Item item) {
        this.createdItem = ForgeRegistries.ITEMS.getKey(item);
        return this;
    }

    @Info("The method that is optional to set the item that will be created by the ritual using a ResourceLocation")
    public RitualRecipeBuilder createsItem(ResourceLocation item) {
        this.createdItem = MATags.lookupItem(item).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("The method that is optional to set the item that will be created by the ritual using a string")
    public RitualRecipeBuilder createsItem(String item) {
        return createsItem(new ResourceLocation(item));
    }

    @Info("The method that is optional to set the command that will be executed when the ritual is triggered")
    public RitualRecipeBuilder command(String command) {
        this.command = command;
        return this;
    }

    @Info("The method that is mandatory to build the ritual json object to use it in custom()")
    public JsonObject build() {
        validateBuildState();
        JsonObject json = super.build();
        json.addProperty("type", "mna:ritual");

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

            reagentObject.addProperty("item", reagent.getItem().toString());

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
        if (hasParams) {
            json.add("parameters", paramsObject);
        }

        if (createdItem != null) {
            json.addProperty("createsItem", createdItem.toString());
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