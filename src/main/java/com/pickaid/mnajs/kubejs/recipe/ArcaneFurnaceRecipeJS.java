package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.schema.ArcaneFurnaceSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public final class ArcaneFurnaceRecipeJS extends MnaBaseRecipeJS<ArcaneFurnaceRecipeJS> {
    public ArcaneFurnaceRecipeJS input(MnaItemId value) {
        return setKey("input", value);
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS input(String value) {
        return input(MnaItemId.parse(value));
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS input(Item value) {
        return input(MnaItemId.parse(value));
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS input(ItemStack value) {
        return input(MnaItemId.parse(value));
    }

    public ArcaneFurnaceRecipeJS output(MnaItemId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public ArcaneFurnaceRecipeJS output(ItemStack value) {
        return output(MnaItemId.parse(value));
    }

    public ArcaneFurnaceRecipeJS burnTime(int value) {
        return setKey("burnTime", value);
    }

    public ArcaneFurnaceRecipeJS outputQuantity(int value) {
        return setKey("outputQuantity", value);
    }

    public ArcaneFurnaceRecipeJS quantity(int value) {
        return outputQuantity(value);
    }

    @Override
    protected void validateRecipe() {
        MnaItemId input = getValue(ArcaneFurnaceSchema.INPUT);
        MnaItemId output = getValue(ArcaneFurnaceSchema.OUTPUT);
        Integer burnTime = getValue(ArcaneFurnaceSchema.BURN_TIME);
        require(input != null, "Arcane furnace recipe input must be set");
        require(output != null, "Arcane furnace recipe output must be set");
        require(burnTime != null && burnTime > 0, "Arcane furnace recipe burnTime must be greater than 0");
        Integer quantity = getValue(ArcaneFurnaceSchema.QUANTITY);
        require(quantity == null || quantity > 0, "Arcane furnace recipe outputQuantity must be greater than 0");
    }
}
