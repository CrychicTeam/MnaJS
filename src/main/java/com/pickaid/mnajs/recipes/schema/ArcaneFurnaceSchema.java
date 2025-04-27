package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface ArcaneFurnaceSchema extends ItemBaseSchema, TierBaseSchema {
    RecipeKey<Integer> BURN_TIME = NumberComponent.INT.key("burnTime");
    RecipeSchema SCHEMA = new RecipeSchema(INPUT, OUTPUT, BURN_TIME, QUANTITY, TIER);
}