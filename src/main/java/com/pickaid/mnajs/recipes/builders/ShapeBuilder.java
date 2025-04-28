package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ShapeBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Shape recipe")
    @Override
    public ShapeBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Shape recipe using an Item")
    @Override
    public ShapeBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Shape recipe using a string")
    @Override
    public ShapeBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Shape recipe")
    @Override
    public ShapeBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Add a pattern for the Shape recipe using a string")
    @Override
    public ShapeBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output shape for the recipe")
    @Override
    public ShapeBuilder output(ResourceLocation output) {
        if (output.getNamespace().equals("mna") && !output.getPath().startsWith("shapes/")) {
            this.output = new ResourceLocation(output.getNamespace(), "shapes/" + output.getPath());
        } else {
            this.output = output;
        }
        return this;
    }

    @Info("Set the output shape for the recipe using a string")
    @Override
    public ShapeBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the output quantity for the Shape recipe")
    @Override
    public ShapeBuilder outputQuantity(int quantity) {
        super.outputQuantity(quantity);
        return this;
    }

    @Info("Set the NBT data for the output shape")
    @Override
    public ShapeBuilder outputNBT(JsonObject nbt) {
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
        return "mna:shape";
    }
}