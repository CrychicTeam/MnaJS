package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface CrushingSchema extends ItemBaseSchema, TierBaseSchema {
    RecipeSchema SCHEMA = new RecipeSchema(INPUT, OUTPUT , QUANTITY, TIER, FACTION);
}
