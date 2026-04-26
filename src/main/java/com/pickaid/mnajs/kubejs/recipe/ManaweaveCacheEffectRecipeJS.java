package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.recipes.schema.ManaweaveCacheEffectSchema;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;

public final class ManaweaveCacheEffectRecipeJS extends MnaBaseRecipeJS<ManaweaveCacheEffectRecipeJS> {
    @Info(value = "Set the mob effect applied by this manaweave cache effect recipe.", params = {
            @Param(name = "value", value = "Mob effect id such as minecraft:speed.")
    })
    public ManaweaveCacheEffectRecipeJS effect(MnaMobEffectId value) {
        return setKey("effect", value);
    }

    public ManaweaveCacheEffectRecipeJS durationMin(int value) {
        return setKey("duration_min", value);
    }

    public ManaweaveCacheEffectRecipeJS durationMax(int value) {
        return setKey("duration_max", value);
    }

    public ManaweaveCacheEffectRecipeJS duration(int min, int max) {
        return durationMin(min).durationMax(max);
    }

    public ManaweaveCacheEffectRecipeJS magnitude(int value) {
        return setKey("magnitude", value);
    }

    @Override
    protected void validateRecipe() {
        MnaMobEffectId effect = getValue(ManaweaveCacheEffectSchema.EFFECT);
        require(effect != null, "Manaweave cache effect recipe effect must be set");

        Integer durationMin = (Integer) currentValue("duration_min");
        Integer durationMax = (Integer) currentValue("duration_max");
        Integer magnitude = (Integer) currentValue("magnitude");

        require(durationMin != null && durationMin > 0, "Manaweave cache effect recipe duration_min must be greater than 0");
        require(durationMax != null && durationMax > 0, "Manaweave cache effect recipe duration_max must be greater than 0");
        require(durationMin == null || durationMax == null || durationMax >= durationMin,
                "Manaweave cache effect recipe duration_max must be greater than or equal to duration_min");
        require(magnitude == null || magnitude >= 0, "Manaweave cache effect recipe magnitude must be greater than or equal to 0");
    }
}
