package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ComponentBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Component recipe")
    @Override
    public ComponentBuilder addItem(MnaItemOrTag item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Component recipe")
    @Override
    public ComponentBuilder addPattern(MnaManaweavePatternId pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @Info("Set the output component for the recipe")
    public ComponentBuilder output(MnaSpellEffectId output) {
        this.output = output.location();
        return this;
    }

    @HideFromJS
    @Override
    public ComponentBuilder output(String output) {
        return output(MnaSpellEffectId.parse(output));
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
