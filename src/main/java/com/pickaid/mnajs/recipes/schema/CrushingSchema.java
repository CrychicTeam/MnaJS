package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.schema.base.QuantityBaseSchema;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface CrushingSchema extends QuantityBaseSchema, TierBaseSchema {
    RecipeKey<Item> INPUT = ItemComponent.ITEM.key("input");
    RecipeKey<Item> OUTPUT = ItemComponent.ITEM.key("output");
    RecipeSchema SCHEMA = new RecipeSchema(INPUT, OUTPUT , QUANTITY, TIER, FACTION);
}
