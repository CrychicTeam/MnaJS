package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.recipe.ShapeRecipeJS;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ShapeSchema extends ItemsPatternSchema {
    RecipeKey<MnaShapeId> OUTPUT = MnaRecipeComponents.SHAPE_ID.key("output");

    RecipeSchema SCHEMA = new RecipeSchema(ShapeRecipeJS.class, ShapeRecipeJS::new, OUTPUT, INPUTS, PATTERNS, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(OUTPUT, INPUTS)
            .constructor(OUTPUT, INPUTS, PATTERNS);
}
