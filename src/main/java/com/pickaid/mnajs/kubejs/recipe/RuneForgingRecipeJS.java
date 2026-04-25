package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.schema.RuneForgingSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public final class RuneForgingRecipeJS extends MnaBaseRecipeJS<RuneForgingRecipeJS> {
    public RuneForgingRecipeJS pattern(MnaItemId value) {
        return setKey("pattern", value);
    }

    @HideFromJS
    public RuneForgingRecipeJS pattern(String value) {
        return pattern(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS pattern(Item value) {
        return pattern(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS pattern(ItemStack value) {
        return pattern(MnaItemId.parse(value));
    }

    public RuneForgingRecipeJS output(MnaItemId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public RuneForgingRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS output(ItemStack value) {
        return output(MnaItemId.parse(value));
    }

    public RuneForgingRecipeJS material(MnaItemId value) {
        return setKey("material", value);
    }

    @HideFromJS
    public RuneForgingRecipeJS material(String value) {
        return material(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS material(Item value) {
        return material(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneForgingRecipeJS material(ItemStack value) {
        return material(MnaItemId.parse(value));
    }

    public RuneForgingRecipeJS hits(int value) {
        return setKey("hits", value);
    }

    public RuneForgingRecipeJS outputQuantity(int value) {
        return setKey("output_quantity", value);
    }

    public RuneForgingRecipeJS quantity(int value) {
        return outputQuantity(value);
    }

    @Override
    protected void validateRecipe() {
        MnaItemId pattern = getValue(RuneForgingSchema.PATTERN);
        MnaItemId output = getValue(RuneForgingSchema.OUTPUT);
        require(pattern != null, "Runeforging recipe pattern must be set");
        require(output != null, "Runeforging recipe output must be set");
        Integer hits = getValue(RuneForgingSchema.HITS);
        Integer quantity = getValue(RuneForgingSchema.QUANTITY);
        require(hits == null || hits > 0, "Runeforging recipe hits must be greater than 0");
        require(quantity == null || quantity > 0, "Runeforging recipe output_quantity must be greater than 0");
    }
}
