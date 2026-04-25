package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.schema.CrushingSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public final class CrushingRecipeJS extends MnaBaseRecipeJS<CrushingRecipeJS> {
    public CrushingRecipeJS input(MnaItemId value) {
        return setKey("input", value);
    }

    @HideFromJS
    public CrushingRecipeJS input(String value) {
        return input(MnaItemId.parse(value));
    }

    @HideFromJS
    public CrushingRecipeJS input(Item value) {
        return input(MnaItemId.parse(value));
    }

    @HideFromJS
    public CrushingRecipeJS input(ItemStack value) {
        return input(MnaItemId.parse(value));
    }

    public CrushingRecipeJS output(MnaItemId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public CrushingRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public CrushingRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public CrushingRecipeJS output(ItemStack value) {
        return output(MnaItemId.parse(value));
    }

    public CrushingRecipeJS outputQuantity(int value) {
        return setKey("output_quantity", value);
    }

    public CrushingRecipeJS quantity(int value) {
        return outputQuantity(value);
    }

    @Override
    protected void validateRecipe() {
        MnaItemId input = getValue(CrushingSchema.INPUT);
        MnaItemId output = getValue(CrushingSchema.OUTPUT);
        require(input != null, "Crushing recipe input must be set");
        require(output != null, "Crushing recipe output must be set");
        Integer quantity = getValue(CrushingSchema.QUANTITY);
        require(quantity == null || quantity > 0, "Crushing recipe output_quantity must be greater than 0");
    }
}
