package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModifierBuilder extends ItemAndPatternBuilder {

    @Info("Add an input item for the Modifier recipe")
    @Override
    public ModifierBuilder addItem(MnaItemOrTag item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Modifier recipe")
    @Override
    public ModifierBuilder addPattern(MnaManaweavePatternId pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @Info("Set the output modifier for the recipe")
    public ModifierBuilder output(MnaModifierId output) {
        this.output = output.location();
        return this;
    }

    @HideFromJS
    @Override
    public ModifierBuilder output(String output) {
        return output(MnaModifierId.parse(output));
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
