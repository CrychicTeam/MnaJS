package com.pickaid.mnajs.recipes.schema;

import com.mna.items.ItemInit;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.recipe.RuneForgingRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface RuneForgingSchema extends TierBaseSchema {
    RecipeKey<MnaItemId> PATTERN = MnaRecipeComponents.ITEM_ID.key("pattern");
    RecipeKey<MnaItemId> OUTPUT = MnaRecipeComponents.ITEM_ID.key("output");
    RecipeKey<MnaItemId> MATERIAL = MnaRecipeComponents.ITEM_ID.key("material").optional(MnaItemId.parse(ItemInit.VINTEUM_INGOT_SUPERHEATED.get()));
    RecipeKey<Integer> HITS = NumberComponent.INT.key("hits").optional(10);
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("output_quantity").preferred("outputQuantity").optional(1).alt("outputQuantity", "quantity");

    RecipeSchema SCHEMA = new RecipeSchema(RuneForgingRecipeJS.class, RuneForgingRecipeJS::new, PATTERN, OUTPUT, MATERIAL, HITS, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(PATTERN, OUTPUT);
}
