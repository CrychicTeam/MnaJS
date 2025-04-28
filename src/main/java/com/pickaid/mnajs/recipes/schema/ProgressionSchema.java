package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ProgressionSchema extends TierBaseSchema {
    RecipeKey<String> ADVANCEMENT = StringComponent.ANY.key("advancement");
    RecipeKey<String> DESC = StringComponent.ANY.key("description").optional("");
    RecipeSchema SCHEMA = new RecipeSchema(ADVANCEMENT, DESC, TIER, FACTION);
}