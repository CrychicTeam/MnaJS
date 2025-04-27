package com.pickaid.mnajs.recipes.schema;

import com.mna.items.ItemInit;
import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface RuneForgingSchema extends TierBaseSchema, ItemBaseSchema {
    RecipeKey<Item> PATTERN = ItemBaseSchema.ITEM.key("pattern");
    RecipeKey<Item> MATERIAL = ITEM.key("material").optional(ItemInit.VINTEUM_INGOT_SUPERHEATED.get());
    RecipeKey<Integer> HITS = NumberComponent.INT.key("hits").optional(10);

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, PATTERN, MATERIAL, HITS, TIER, FACTION);
}
