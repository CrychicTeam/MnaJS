package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.recipe.ArcaneFurnaceRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.QuantityBaseSchema;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ArcaneFurnaceSchema extends QuantityBaseSchema, TierBaseSchema {
    RecipeKey<MnaItemId> INPUT = MnaRecipeComponents.ITEM_ID.key("input");
    RecipeKey<MnaItemId> OUTPUT = MnaRecipeComponents.ITEM_ID.key("output").optional((MnaItemId) null);
    RecipeKey<Integer> BURN_TIME = NumberComponent.INT.key("burnTime").optional((Integer) null);
    RecipeSchema SCHEMA = new RecipeSchema(ArcaneFurnaceRecipeJS.class, ArcaneFurnaceRecipeJS::new, INPUT, OUTPUT, BURN_TIME, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor(INPUT, OUTPUT, BURN_TIME);
}
