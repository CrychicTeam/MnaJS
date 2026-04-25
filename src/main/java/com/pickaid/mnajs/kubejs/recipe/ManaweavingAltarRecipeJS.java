package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.recipes.schema.ManaweavingAltarSchema;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import dev.latvian.mods.rhino.util.HideFromJS;

import java.util.Arrays;
import java.util.Collection;

public final class ManaweavingAltarRecipeJS extends MnaBaseRecipeJS<ManaweavingAltarRecipeJS> {
    public ManaweavingAltarRecipeJS output(MnaItemId value) {
        return setKey("output", value.id());
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    public ManaweavingAltarRecipeJS outputStack(ItemStack value) {
        return setKey("output", value);
    }

    public ManaweavingAltarRecipeJS inputs(MnaItemOrTag... values) {
        return setKey("items", values);
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS inputs(String... values) {
        return inputs(Arrays.stream(values)
                .map(MnaItemOrTag::parse)
                .toArray(MnaItemOrTag[]::new));
    }

    public ManaweavingAltarRecipeJS addInput(MnaItemOrTag value) {
        var inputs = editableList("items");
        inputs.add(value);
        return setKey("items", inputs);
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS addInput(String value) {
        return addInput(MnaItemOrTag.parse(value));
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS inputs(Collection<?> values) {
        return setKey("items", values);
    }

    public ManaweavingAltarRecipeJS patterns(MnaManaweavePatternId... values) {
        return setKey("patterns", values);
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS patterns(String... values) {
        return setKey("patterns", values);
    }

    public ManaweavingAltarRecipeJS addPattern(MnaManaweavePatternId value) {
        var patterns = editableList("patterns");
        patterns.add(value.id());
        return setKey("patterns", patterns);
    }

    @HideFromJS
    public ManaweavingAltarRecipeJS addPattern(String value) {
        return addPattern(MnaManaweavePatternId.parse(value));
    }

    public ManaweavingAltarRecipeJS enchant(String value) {
        return setKey("enchant", value);
    }

    public ManaweavingAltarRecipeJS enchantment(String value) {
        return enchant(value);
    }

    public ManaweavingAltarRecipeJS magnitude(int value) {
        return setKey("magnitude", value);
    }

    public ManaweavingAltarRecipeJS copyNbt(boolean value) {
        return setKey("copy_nbt", value);
    }

    public ManaweavingAltarRecipeJS copyNBT(boolean value) {
        return copyNbt(value);
    }

    public ManaweavingAltarRecipeJS quantity(int value) {
        return setKey("quantity", value);
    }

    public ManaweavingAltarRecipeJS outputQuantity(int value) {
        return quantity(value);
    }

    @Override
    protected void validateRecipe() {
        ItemStack output = getValue(ManaweavingAltarSchema.OUTPUT);
        MnaItemOrTag[] inputs = getValue(ManaweavingAltarSchema.INPUTS);
        require(output != null && !output.isEmpty(), "Manaweaving recipe output must be set");
        require(inputs != null && inputs.length > 0, "Manaweaving recipe must define at least one input item");
        Integer quantity = (Integer) currentValue("quantity");
        Integer magnitude = (Integer) currentValue("magnitude");
        require(quantity == null || quantity > 0, "Manaweaving recipe quantity must be greater than 0");
        require(magnitude == null || magnitude > 0, "Manaweaving recipe magnitude must be greater than 0");
    }
}
