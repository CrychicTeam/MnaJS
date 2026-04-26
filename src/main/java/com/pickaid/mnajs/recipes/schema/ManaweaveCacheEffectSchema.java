package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.recipe.ManaweaveCacheEffectRecipeJS;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ManaweaveCacheEffectSchema extends TierBaseSchema {
    RecipeKey<MnaMobEffectId> EFFECT = MnaRecipeComponents.MOB_EFFECT_ID.key("effect");
    RecipeKey<Integer> DURATION_MIN = NumberComponent.INT.key("duration_min").optional((Integer) null);
    RecipeKey<Integer> DURATION_MAX = NumberComponent.INT.key("duration_max").optional((Integer) null);
    RecipeKey<Integer> MAGNITUDE = NumberComponent.INT.key("magnitude").optional(0);

    RecipeSchema SCHEMA = new RecipeSchema(ManaweaveCacheEffectRecipeJS.class, ManaweaveCacheEffectRecipeJS::new, EFFECT, DURATION_MIN, DURATION_MAX, MAGNITUDE, TIER, FACTION)
            .constructor()
            .constructor(EFFECT, DURATION_MIN, DURATION_MAX);
}
