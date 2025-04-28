package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModifierBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Modifier recipe")
    @Override
    public ModifierBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Modifier recipe using an Item")
    @Override
    public ModifierBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Modifier recipe using a string")
    @Override
    public ModifierBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Modifier recipe")
    @Override
    public ModifierBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Add a pattern for the Modifier recipe using a string")
    @Override
    public ModifierBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output modifier for the recipe")
    @Override
    public ModifierBuilder output(ResourceLocation output) {
        // For modifier recipes, we handle the special namespace behavior
        if (output.getNamespace().equals("mna") && !output.getPath().startsWith("modifiers/")) {
            this.output = new ResourceLocation(output.getNamespace(), "modifiers/" + output.getPath());
        } else {
            this.output = output;
        }
        return this;
    }

    @Info("Set the output modifier for the recipe using a string")
    @Override
    public ModifierBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the output quantity for the Modifier recipe")
    @Override
    public ModifierBuilder outputQuantity(int quantity) {
        super.outputQuantity(quantity);
        return this;
    }

    @Info("Set the NBT data for the output modifier")
    @Override
    public ModifierBuilder outputNBT(JsonObject nbt) {
        super.outputNBT(nbt);
        return this;
    }

    @Override
    protected int maxItems() {
        return 9;
    }

    @Override
    protected int maxPatterns() {
        return 9;
    }

    @Override
    protected String getRecipeType() {
        return "mna:modifier";
    }
}