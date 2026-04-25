package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.recipe.CrushingRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface CrushingSchema extends TierBaseSchema {
    RecipeKey<MnaItemId> INPUT = MnaRecipeComponents.ITEM_ID.key("input");
    RecipeKey<MnaItemId> OUTPUT = MnaRecipeComponents.ITEM_ID.key("output");
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("output_quantity").preferred("outputQuantity").optional(1).alt("outputQuantity", "quantity");
    RecipeSchema SCHEMA = new RecipeSchema(CrushingRecipeJS.class, CrushingRecipeJS::new, INPUT, OUTPUT, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(INPUT, OUTPUT);
}
