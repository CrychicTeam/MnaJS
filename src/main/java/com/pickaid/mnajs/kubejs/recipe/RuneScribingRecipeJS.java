package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.schema.RunescribingSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public final class RuneScribingRecipeJS extends MnaBaseRecipeJS<RuneScribingRecipeJS> {
    public RuneScribingRecipeJS output(MnaItemId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public RuneScribingRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneScribingRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public RuneScribingRecipeJS output(ItemStack value) {
        return output(MnaItemId.parse(value));
    }

    public RuneScribingRecipeJS hMutex(long value) {
        return setKey("mutex_h", value);
    }

    public RuneScribingRecipeJS vMutex(long value) {
        return setKey("mutex_v", value);
    }

    @Override
    protected void validateRecipe() {
        MnaItemId output = getValue(RunescribingSchema.OUTPUT);
        require(output != null, "Runescribing recipe output must be set");
        require(currentValue("mutex_h") != null, "Runescribing recipe hMutex must be set");
        require(currentValue("mutex_v") != null, "Runescribing recipe vMutex must be set");
    }
}
