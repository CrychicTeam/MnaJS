package com.pickaid.mnajs.recipes.builders.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemAndPatternBuilder extends MABaseBuilder {
    protected final List<ResourceLocation> requiredItems = new ArrayList<>();
    protected final List<ResourceLocation> requiredPatterns = new ArrayList<>();
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
    public ItemAndPatternBuilder addItem(ResourceLocation item) {
        if (requiredItems.size() >= maxItems()) {
            throw new IllegalStateException("Cannot add more than " + maxItems() + " input items");
        }
        this.requiredItems.add(item);
        return this;
    }

    @Info("Add an input item for the recipe using an Item")
    public ItemAndPatternBuilder addItem(Item item) {
        return addItem(ForgeRegistries.ITEMS.getKey(item));
    }

    @Info("Add an input item for the recipe using a string")
    public ItemAndPatternBuilder addItem(String item) {
        return addItem(new ResourceLocation(item));
    }

    @Info("Add a pattern for the recipe")
    public ItemAndPatternBuilder addPattern(ResourceLocation pattern) {
        if (requiredPatterns.size() >= maxPatterns()) {
            throw new IllegalStateException("Cannot add more than " + maxPatterns() + " patterns");
        }
        this.requiredPatterns.add(pattern);
        return this;
    }

    @Info("Add a pattern for the recipe using a string")
    public ItemAndPatternBuilder addPattern(String pattern) {
        return addPattern(new ResourceLocation(pattern));
    }

    @Info("Set the output item or component for the recipe")
    public ItemAndPatternBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @Info("Set the output item or component for the recipe using a string")
    public ItemAndPatternBuilder output(String output) {
        return output(new ResourceLocation(output));
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
        for (ResourceLocation item : requiredItems) {
            itemsArray.add(item.toString());
        }
        json.add("items", itemsArray);
        if (!requiredPatterns.isEmpty()) {
            JsonArray patternsArray = new JsonArray();
            for (ResourceLocation pattern : requiredPatterns) {
                patternsArray.add(pattern.toString());
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