package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface RunescribingSchema extends TierBaseSchema, ItemBaseSchema {
    RecipeKey<Long> VMUTEX = NumberComponent.LONG.key("mutex_h");
    RecipeKey<Long> VMUTEY = NumberComponent.LONG.key("mutex_v");

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, VMUTEX, VMUTEY, TIER, FACTION);
}
