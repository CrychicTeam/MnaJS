package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.schema.base.QuantityBaseSchema;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface ArcaneFurnaceSchema extends QuantityBaseSchema, TierBaseSchema {
    RecipeKey<Item> INPUT = ItemComponent.ITEM.key("input");
    RecipeKey<Item> OUTPUT = ItemComponent.ITEM.key("output");
    RecipeKey<Integer> BURN_TIME = NumberComponent.INT.key("burnTime");
    RecipeSchema SCHEMA = new RecipeSchema(INPUT, OUTPUT, BURN_TIME, QUANTITY, TIER);
}