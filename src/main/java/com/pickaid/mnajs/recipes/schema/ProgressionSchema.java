package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.recipe.ProgressionRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ProgressionSchema extends TierBaseSchema {
    RecipeKey<com.pickaid.mnajs.kubejs.id.MnaAdvancementId> ADVANCEMENT = MnaRecipeComponents.ADVANCEMENT_ID.key("advancement");
    RecipeKey<String> DESC = StringComponent.ANY.key("desc").optional("").alt("description");
    RecipeSchema SCHEMA = new RecipeSchema(ProgressionRecipeJS.class, ProgressionRecipeJS::new, ADVANCEMENT, DESC, TIER, FACTION)
            .constructor()
            .constructor(ADVANCEMENT);
}
