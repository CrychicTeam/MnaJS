package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.recipe.ComponentRecipeJS;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ComponentSchema extends ItemsPatternSchema {
    RecipeKey<MnaSpellEffectId> OUTPUT = MnaRecipeComponents.SPELL_EFFECT_ID.key("output");

    RecipeSchema SCHEMA = new RecipeSchema(ComponentRecipeJS.class, ComponentRecipeJS::new, OUTPUT, INPUTS, PATTERNS, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(OUTPUT, INPUTS)
            .constructor(OUTPUT, INPUTS, PATTERNS);
}
