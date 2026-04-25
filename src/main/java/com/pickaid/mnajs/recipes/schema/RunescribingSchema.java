package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.recipe.RuneScribingRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface RunescribingSchema extends TierBaseSchema {
    RecipeKey<MnaItemId> OUTPUT = MnaRecipeComponents.ITEM_ID.key("output");
    RecipeKey<Long> HMUTEX = NumberComponent.LONG.key("mutex_h").preferred("hMutex").optional((Long) null);
    RecipeKey<Long> VMUTEX = NumberComponent.LONG.key("mutex_v").preferred("vMutex").optional((Long) null);

    RecipeSchema SCHEMA = new RecipeSchema(RuneScribingRecipeJS.class, RuneScribingRecipeJS::new, OUTPUT, HMUTEX, VMUTEX, TIER, FACTION)
            .constructor()
            .constructor(OUTPUT, HMUTEX, VMUTEX);
}
