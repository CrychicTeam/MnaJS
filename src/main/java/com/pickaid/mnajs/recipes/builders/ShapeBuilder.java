package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ShapeBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Shape recipe")
    @Override
    public ShapeBuilder addItem(MnaItemOrTag item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Shape recipe")
    @Override
    public ShapeBuilder addPattern(MnaManaweavePatternId pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @Info("Set the output shape for the recipe")
    public ShapeBuilder output(MnaShapeId output) {
        this.output = output.location();
        return this;
    }

    @HideFromJS
    @Override
    public ShapeBuilder output(String output) {
        return output(MnaShapeId.parse(output));
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
