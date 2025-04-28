package com.pickaid.mnajs.recipes.schema.base;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;

public interface QuantityBaseSchema {
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("outputQuantity").optional(1).alt("quantity");
}
