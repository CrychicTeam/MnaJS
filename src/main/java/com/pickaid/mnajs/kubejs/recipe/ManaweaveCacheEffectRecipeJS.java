package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaIds;
import com.pickaid.mnajs.recipes.schema.ManaweaveCacheEffectSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public final class ManaweaveCacheEffectRecipeJS extends MnaBaseRecipeJS<ManaweaveCacheEffectRecipeJS> {
    public ManaweaveCacheEffectRecipeJS effect(MobEffect value) {
        return setKey("effect", value);
    }

    @HideFromJS
    public ManaweaveCacheEffectRecipeJS effect(String value) {
        return effect(MnaIds.parse(value, "effect", "minecraft", null));
    }

    @HideFromJS
    public ManaweaveCacheEffectRecipeJS effect(ResourceLocation value) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(value);
        require(effect != null, "Unknown mob effect id: " + value);
        return effect(effect);
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
        MobEffect effect = getValue(ManaweaveCacheEffectSchema.EFFECT);
        require(effect != null, "Manaweave cache effect recipe effect must be set");

        Integer durationMin = (Integer) currentValue("duration_min");
        Integer durationMax = (Integer) currentValue("duration_max");
        Integer magnitude = (Integer) currentValue("magnitude");

        require(durationMin != null && durationMin > 0, "Manaweave cache effect recipe duration_min must be greater than 0");
        require(durationMax != null && durationMax > 0, "Manaweave cache effect recipe duration_max must be greater than 0");
        require(durationMin == null || durationMax == null || durationMax >= durationMin,
                "Manaweave cache effect recipe duration_max must be greater than or equal to duration_min");
        require(magnitude == null || magnitude > 0, "Manaweave cache effect recipe magnitude must be greater than 0");
    }
}
