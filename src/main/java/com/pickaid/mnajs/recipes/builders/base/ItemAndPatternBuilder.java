package com.pickaid.mnajs.recipes.builders.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaIds;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemAndPatternBuilder extends MABaseBuilder {
    protected final List<MnaItemOrTag> requiredItems = new ArrayList<>();
    protected final List<MnaManaweavePatternId> requiredPatterns = new ArrayList<>();
    protected ResourceLocation output;
    protected int outputQuantity = 1;
    protected JsonObject outputNBT = null;

    /**
     * Get the maximum number of items allowed in this recipe
     */
    protected abstract int maxItems();

    /**
     * Get the maximum number of patterns allowed in this recipe
     */
    protected abstract int maxPatterns();

    @Info("Add an input item for the recipe")
    public ItemAndPatternBuilder addItem(MnaItemOrTag item) {
        if (requiredItems.size() >= maxItems()) {
            throw new IllegalStateException("Cannot add more than " + maxItems() + " input items");
        }
        this.requiredItems.add(item);
        return this;
    }

    @HideFromJS
    public ItemAndPatternBuilder addItem(Item item) {
        return addItem(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public ItemAndPatternBuilder addItem(ResourceLocation item) {
        return addItem(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public ItemAndPatternBuilder addItem(String item) {
        return addItem(MnaItemOrTag.parse(item));
    }

    @Info("Add a pattern for the recipe")
    public ItemAndPatternBuilder addPattern(MnaManaweavePatternId pattern) {
        if (requiredPatterns.size() >= maxPatterns()) {
            throw new IllegalStateException("Cannot add more than " + maxPatterns() + " patterns");
        }
        this.requiredPatterns.add(pattern);
        return this;
    }

    @HideFromJS
    public ItemAndPatternBuilder addPattern(ResourceLocation pattern) {
        return addPattern(MnaManaweavePatternId.of(pattern));
    }

    @HideFromJS
    public ItemAndPatternBuilder addPattern(String pattern) {
        return addPattern(MnaManaweavePatternId.parse(pattern));
    }

    @HideFromJS
    public ItemAndPatternBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @HideFromJS
    public ItemAndPatternBuilder output(String output) {
        return output(MnaIds.parse(output, "output"));
    }

    @Info("Set the output quantity for the recipe")
    public ItemAndPatternBuilder outputQuantity(int quantity) {
        this.outputQuantity = Math.max(1, quantity);
        return this;
    }

    @Info("Set the NBT data for the output item")
    public ItemAndPatternBuilder outputNBT(JsonObject nbt) {
        this.outputNBT = nbt;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", getRecipeType());
        if (requiredItems.isEmpty()) {
            throw new IllegalStateException("At least one input item must be specified");
        }

        if (output == null) {
            throw new IllegalStateException("Output cannot be null");
        }
        JsonArray itemsArray = new JsonArray();
        for (MnaItemOrTag item : requiredItems) {
            itemsArray.add(item.recipeValue());
        }
        json.add("items", itemsArray);
        if (!requiredPatterns.isEmpty()) {
            JsonArray patternsArray = new JsonArray();
            for (MnaManaweavePatternId pattern : requiredPatterns) {
                patternsArray.add(pattern.id());
            }
            json.add("patterns", patternsArray);
        }
        if (outputNBT != null) {
            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", output.toString());
            outputObject.add("data", outputNBT);
            json.add("output", outputObject);
        } else {
            json.addProperty("output", output.toString());
        }

        if (outputQuantity > 1) {
            json.addProperty("quantity", outputQuantity);
        }

        return json;
    }
}
