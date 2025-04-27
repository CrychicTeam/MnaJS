package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ProgressionSchema extends TierBaseSchema {
    RecipeKey<String> ADVANCEMENT = StringComponent.ID.key("advancement");
    RecipeKey<String> DESC = StringComponent.ANY.key("description").optional("").exclude();
    RecipeSchema SCHEMA = new RecipeSchema(ADVANCEMENT, TIER);
}