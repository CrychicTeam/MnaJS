package com.pickaid.mnajs.recipes.schema;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public interface ManaweaveCacheEffectSchema extends TierBaseSchema {
    RecipeComponent<MobEffect> EFFECT_COMPONENT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return MobEffect.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, MobEffect value) {
            var key = ForgeRegistries.MOB_EFFECTS.getKey(value);
            if (key == null) return new JsonPrimitive("");
            return new JsonPrimitive(key.toString());
        }

        @Override
        public MobEffect read(RecipeJS recipe, Object from) {
            if (from == null) return null;

            ResourceLocation resourceLocation;
            if (from instanceof ResourceLocation rl) {
                resourceLocation = rl;
            } else if (from instanceof String s) {
                resourceLocation = new ResourceLocation(s);
            } else if (from instanceof JsonElement jsonElement && jsonElement.isJsonPrimitive()
                    && jsonElement.getAsJsonPrimitive().isString()) {
                resourceLocation = new ResourceLocation(jsonElement.getAsString());
            } else {
                return null;
            }

            return ForgeRegistries.MOB_EFFECTS.getValue(resourceLocation);
        }
    };

    RecipeKey<MobEffect> EFFECT = EFFECT_COMPONENT.key("effect");
    RecipeKey<Integer> DURATION_MIN = NumberComponent.INT.key("duration_min");
    RecipeKey<Integer> DURATION_MAX = NumberComponent.INT.key("duration_max");
    RecipeKey<Integer> MAGNITUDE = NumberComponent.INT.key("magnitude").optional(1);

    RecipeSchema SCHEMA = new RecipeSchema(EFFECT, DURATION_MIN, DURATION_MAX, MAGNITUDE, TIER, FACTION);
}