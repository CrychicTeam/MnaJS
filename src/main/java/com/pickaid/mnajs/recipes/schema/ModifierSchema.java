package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ModifierSchema extends ItemsPatternSchema {
    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INPUTS, PATTERNS, QUANTITY, TIER, FACTION);
}
