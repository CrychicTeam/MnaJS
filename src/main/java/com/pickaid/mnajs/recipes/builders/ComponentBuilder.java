package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ComponentBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Component recipe")
    @Override
    public ComponentBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Component recipe using an Item")
    @Override
    public ComponentBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Component recipe using a string")
    @Override
    public ComponentBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Component recipe")
    @Override
    public ComponentBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Add a pattern for the Component recipe using a string")
    @Override
    public ComponentBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output component for the recipe")
    @Override
    public ComponentBuilder output(ResourceLocation output) {
        if (output.getNamespace().equals("mna") && !output.getPath().startsWith("components/")) {
            this.output = new ResourceLocation(output.getNamespace(), "components/" + output.getPath());
        } else {
            this.output = output;
        }
        return this;
    }

    @Info("Set the output component for the recipe using a string")
    @Override
    public ComponentBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the output quantity for the Component recipe")
    @Override
    public ComponentBuilder outputQuantity(int quantity) {
        super.outputQuantity(quantity);
        return this;
    }

    @Info("Set the NBT data for the output component")
    @Override
    public ComponentBuilder outputNBT(JsonObject nbt) {
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
        return "mna:component";
    }
}