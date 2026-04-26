package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.pattern.MnaPatternHelper;
import com.pickaid.mnajs.kubejs.recipe.MnaRitualReagent;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RitualRecipeBuilder extends MABaseBuilder {
    private int[][] pattern;
    private String[] reagentPattern;
    private final Map<Character, ReagentInfo> reagentMap = new LinkedHashMap<>();

    private int[][] displayPattern;
    private String[] manaweavePatterns;
    private Long innerColor;
    private Long outerColor;
    private Long beamColor;
    private Boolean connectBeam;
    private Boolean displayIndexes;
    private Boolean kittable;
    private MnaItemId createdItem;
    private String command;

    @HideFromJS
    public static class ReagentInfo {
        private final MnaItemOrTag item;
        private boolean optional = false;
        private boolean consume = true;
        private boolean dynamic = false;
        private boolean dynamicSource = false;
        private boolean manualReturn = false;

        public ReagentInfo(MnaItemOrTag item) {
            this.item = Objects.requireNonNull(item, "item");
        }

        public ReagentInfo(MnaRitualReagent reagent) {
            this(Objects.requireNonNull(reagent, "reagent").item());
            this.optional = reagent.isOptional();
            this.consume = reagent.consumes();
            this.dynamic = reagent.isDynamic();
            this.dynamicSource = reagent.isDynamicSource();
            this.manualReturn = reagent.isManualReturn();
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
            this.dynamic = value;
            return this;
        }

        public ReagentInfo dynamicSource(boolean value) {
            this.dynamicSource = value;
            return this;
        }

        public ReagentInfo manualReturn(boolean value) {
            this.manualReturn = value;
            return this;
        }
    }

    @Info("The method that is mandatory to set the pattern of the ritual")
    public RitualRecipeBuilder pattern(int[][] pattern) {
        Objects.requireNonNull(pattern, "Pattern cannot be null");
        validateSquareOddGrid(pattern, "Pattern");
        this.pattern = copyPattern(pattern);
        return this;
    }

    @Info("The method that is mandatory to set the pattern of the ritual from readable rows")
    public RitualRecipeBuilder patternRows(String... rows) {
        return pattern(MnaPatternHelper.ritualRows(rows));
    }

    @HideFromJS
    public RitualRecipeBuilder pattern(Integer[][] pattern) {
        return pattern(toIntPattern(pattern));
    }

    @Info("The method that is optional to set the display pattern of the ritual")
    public RitualRecipeBuilder displayPattern(int[][] displayPattern) {
        Objects.requireNonNull(displayPattern, "Display pattern cannot be null");
        validateSquareOddGrid(displayPattern, "Display pattern");
        this.displayPattern = copyPattern(displayPattern);
        return this;
    }

    @Info("The method that is optional to set the display pattern of the ritual from readable rows")
    public RitualRecipeBuilder displayPatternRows(String... rows) {
        return displayPattern(MnaPatternHelper.ritualRows(rows));
    }

    @HideFromJS
    public RitualRecipeBuilder displayPattern(Integer[][] displayPattern) {
        return displayPattern(toIntPattern(displayPattern));
    }

    @Info("The method that is mandatory to set the pattern of the reagents")
    public RitualRecipeBuilder reagentRows(String... reagentPattern) {
        this.reagentPattern = MnaPatternHelper.reagentRows(reagentPattern);
        return this;
    }

    @Info("The method that is mandatory to set the pattern of the reagents")
    public RitualRecipeBuilder reagents(String... reagentPattern) {
        return reagentRows(reagentPattern);
    }

    @HideFromJS
    public RitualRecipeBuilder reagentPattern(String[] reagentPattern) {
        return reagentRows(reagentPattern);
    }

    @Info("The method that is mandatory to set the key of the reagents")
    public RitualRecipeBuilder reagent(MnaRitualReagent reagent) {
        return putReagent(reagent);
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(key, item));
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, MnaItemOrTag item, boolean optional, boolean consume) {
        MnaRitualReagent reagent = new MnaRitualReagent(key, item);
        if (optional) {
            reagent.optional();
        }
        if (!consume) {
            reagent.keep();
        }
        return reagent(reagent);
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicSourceReagent(char key, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(key, item).dynamicSource());
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicReagent(char key, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(key, item).dynamic());
    }

    @HideFromJS
    public RitualRecipeBuilder manualReturnReagent(char key, MnaItemOrTag item) {
        return reagent(new MnaRitualReagent(key, item).manualReturn());
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, String item) {
        return reagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, Item item) {
        return reagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, ItemStack item) {
        return reagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, String item, boolean optional, boolean consume) {
        return reagent(key, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, Item item, boolean optional, boolean consume) {
        return reagent(key, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeBuilder reagent(char key, ItemStack item, boolean optional, boolean consume) {
        return reagent(key, MnaItemOrTag.parse(item), optional, consume);
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicSourceReagent(char key, String item) {
        return dynamicSourceReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicSourceReagent(char key, Item item) {
        return dynamicSourceReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicSourceReagent(char key, ItemStack item) {
        return dynamicSourceReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicReagent(char key, String item) {
        return dynamicReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicReagent(char key, Item item) {
        return dynamicReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder dynamicReagent(char key, ItemStack item) {
        return dynamicReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder manualReturnReagent(char key, String item) {
        return manualReturnReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder manualReturnReagent(char key, Item item) {
        return manualReturnReagent(key, MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder manualReturnReagent(char key, ItemStack item) {
        return manualReturnReagent(key, MnaItemOrTag.parse(item));
    }

    @Info("The method that is optional to set the manaweave patterns of the ritual")
    public RitualRecipeBuilder manaweavePatterns(MnaManaweavePatternId[] patterns) {
        if (patterns == null) {
            this.manaweavePatterns = null;
            return this;
        }
        this.manaweavePatterns = new String[patterns.length];
        for (int index = 0; index < patterns.length; index++) {
            this.manaweavePatterns[index] = patterns[index].id();
        }
        return this;
    }

    @HideFromJS
    public RitualRecipeBuilder manaweavePatterns(String[] patterns) {
        if (patterns == null) {
            this.manaweavePatterns = null;
            return this;
        }

        MnaManaweavePatternId[] typedPatterns = new MnaManaweavePatternId[patterns.length];
        for (int index = 0; index < patterns.length; index++) {
            typedPatterns[index] = MnaManaweavePatternId.parse(patterns[index]);
        }
        return manaweavePatterns(typedPatterns);
    }

    @Info("The method that is optional to add a manaweave pattern to the patterns' list and will finally add to the ritual")
    public RitualRecipeBuilder addManaweavePattern(MnaManaweavePatternId pattern) {
        List<String> patterns = new ArrayList<>();
        if (manaweavePatterns != null) {
            for (String value : manaweavePatterns) {
                patterns.add(value);
            }
        }
        patterns.add(pattern.id());
        this.manaweavePatterns = patterns.toArray(new String[0]);
        return this;
    }

    @HideFromJS
    public RitualRecipeBuilder addManaweavePattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return this;
        }
        return addManaweavePattern(MnaManaweavePatternId.parse(pattern));
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

    @Info("The method that is mandatory to set the item that will be created by the ritual")
    public RitualRecipeBuilder outputItem(MnaItemId item) {
        this.createdItem = Objects.requireNonNull(item, "Output item cannot be null");
        return this;
    }

    public RitualRecipeBuilder output(MnaItemId item) {
        return outputItem(item);
    }

    public RitualRecipeBuilder createsItem(MnaItemId item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder outputItem(String item) {
        return outputItem(MnaItemId.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder outputItem(Item item) {
        return outputItem(MnaItemId.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder outputItem(ItemStack item) {
        return outputItem(MnaItemId.parse(item));
    }

    @HideFromJS
    public RitualRecipeBuilder output(String item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder output(Item item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder output(ItemStack item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder createsItem(String item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder createsItem(Item item) {
        return outputItem(item);
    }

    @HideFromJS
    public RitualRecipeBuilder createsItem(ItemStack item) {
        return outputItem(item);
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
        json.add("pattern", toJsonGrid(pattern));

        if (displayPattern != null) {
            json.add("displayPattern", toJsonGrid(displayPattern));
        }

        JsonArray reagentArray = new JsonArray();
        for (String row : reagentPattern) {
            reagentArray.add(row);
        }
        json.add("reagents", reagentArray);

        JsonObject keysObject = new JsonObject();
        for (Map.Entry<Character, ReagentInfo> entry : reagentMap.entrySet()) {
            JsonObject reagentObject = new JsonObject();
            ReagentInfo reagent = entry.getValue();

            reagentObject.addProperty("item", reagent.item.recipeValue());
            if (reagent.optional) {
                reagentObject.addProperty("optional", true);
            }
            if (!reagent.consume) {
                reagentObject.addProperty("consume", false);
            }
            if (reagent.manualReturn) {
                reagentObject.addProperty("manual_return", true);
            }
            if (reagent.dynamic) {
                reagentObject.addProperty("is_dynamic", true);
            }
            if (reagent.dynamicSource) {
                reagentObject.addProperty("dynamic_source", true);
            }

            keysObject.add(String.valueOf(entry.getKey()), reagentObject);
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

        json.addProperty("createsItem", createdItem.id());

        if (command != null && !command.isEmpty()) {
            json.addProperty("command", command);
        }

        return json;
    }

    private RitualRecipeBuilder putReagent(MnaRitualReagent reagent) {
        char key = reagent.symbolChar();
        if (Character.isWhitespace(key)) {
            throw new IllegalArgumentException("Reagent key cannot be whitespace");
        }

        reagentMap.put(key, new ReagentInfo(reagent));
        return this;
    }

    private void validateBuildState() {
        if (pattern == null) {
            throw new IllegalStateException("Pattern must be set (REQUIRED)");
        }

        if (reagentPattern == null) {
            throw new IllegalStateException("Reagent rows must be set (REQUIRED)");
        }

        if (reagentMap.isEmpty()) {
            throw new IllegalStateException("At least one reagent must be defined (REQUIRED)");
        }

        if (createdItem == null) {
            throw new IllegalStateException("outputItem must be set (REQUIRED)");
        }

        int size = pattern.length;

        if (reagentPattern.length != size) {
            throw new IllegalStateException("Reagent rows must have the same number of rows as pattern");
        }

        for (int row = 0; row < reagentPattern.length; row++) {
            if (reagentPattern[row].length() != size) {
                throw new IllegalStateException("Reagent row " + row + " must be exactly " + size + " characters");
            }
        }

        if (displayPattern != null) {
            validateSquareOddGrid(displayPattern, "Display pattern");
            if (displayPattern.length != size) {
                throw new IllegalStateException("Display pattern must have the same size as pattern");
            }
        }

        boolean dynamicSourceFound = false;
        for (Map.Entry<Character, ReagentInfo> entry : reagentMap.entrySet()) {
            ReagentInfo reagent = entry.getValue();
            if (reagent.dynamicSource) {
                if (dynamicSourceFound) {
                    throw new IllegalStateException("Only one dynamic source allowed");
                }
                dynamicSourceFound = true;
            }
        }

        for (String row : reagentPattern) {
            for (char symbol : row.toCharArray()) {
                if (symbol != ' ' && !reagentMap.containsKey(symbol)) {
                    throw new IllegalStateException("Reagent pattern uses undefined key: " + symbol);
                }
            }
        }
    }

    private static JsonArray toJsonGrid(int[][] values) {
        JsonArray patternArray = new JsonArray();
        for (int[] row : values) {
            JsonArray rowArray = new JsonArray();
            for (int value : row) {
                rowArray.add(value);
            }
            patternArray.add(rowArray);
        }
        return patternArray;
    }

    private static void validateSquareOddGrid(int[][] values, String label) {
        if (values.length == 0) {
            throw new IllegalArgumentException(label + " must define at least one row");
        }

        int width = -1;
        for (int row = 0; row < values.length; row++) {
            int[] columns = values[row];
            if (columns == null || columns.length == 0) {
                throw new IllegalArgumentException(label + " row " + row + " cannot be empty");
            }
            if (width == -1) {
                width = columns.length;
            } else if (columns.length != width) {
                throw new IllegalArgumentException(label + " rows must all have the same width");
            }
        }

        if (values.length != width) {
            throw new IllegalArgumentException(label + " must be square");
        }

        if ((values.length & 1) == 0) {
            throw new IllegalArgumentException(label + " size must be odd");
        }
    }

    private static int[][] copyPattern(int[][] value) {
        int[][] copy = new int[value.length][];
        for (int row = 0; row < value.length; row++) {
            copy[row] = value[row] == null ? null : value[row].clone();
        }
        return copy;
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
}
