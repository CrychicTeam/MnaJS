package com.pickaid.mnajs.recipes.schema;

import com.mna.items.ItemInit;
import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface RuneForgingSchema extends TierBaseSchema {
    RecipeKey<Item> OUTPUT = ItemComponent.ITEM.key("output");
    RecipeKey<Item> PATTERN = ItemComponent.ITEM.key("pattern");
    RecipeKey<Item> MATERIAL = ItemComponent.ITEM.key("material").optional(ItemInit.VINTEUM_INGOT_SUPERHEATED.get());
    RecipeKey<Integer> HITS = NumberComponent.INT.key("hits").optional(10);

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, PATTERN, MATERIAL, HITS, TIER, FACTION);
}
