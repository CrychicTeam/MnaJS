package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.recipe.ModifierRecipeJS;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ModifierSchema extends ItemsPatternSchema {
    RecipeKey<MnaModifierId> OUTPUT = MnaRecipeComponents.MODIFIER_ID.key("output");

    RecipeSchema SCHEMA = new RecipeSchema(ModifierRecipeJS.class, ModifierRecipeJS::new, OUTPUT, INPUTS, PATTERNS, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(OUTPUT, INPUTS)
            .constructor(OUTPUT, INPUTS, PATTERNS);
}
