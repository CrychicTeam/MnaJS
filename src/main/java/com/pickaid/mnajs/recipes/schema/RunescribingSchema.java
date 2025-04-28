package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface RunescribingSchema extends TierBaseSchema {
    RecipeKey<Item> OUTPUT = ItemComponent.ITEM.key("output");
    RecipeKey<Long> VMUTEX = NumberComponent.LONG.key("mutex_h");
    RecipeKey<Long> VMUTEY = NumberComponent.LONG.key("mutex_v");

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, VMUTEX, VMUTEY, TIER, FACTION);
}
