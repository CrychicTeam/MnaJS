package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.PowerProvided;
import com.pickaid.mnajs.recipes.component.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FumerFliterSchema extends TierBaseSchema {
    RecipeKey<InputItem> ITEM_OR_TAG_ID = ItemComponents.INPUT.key("item");
    RecipeKey<PowerProvided> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.key("power_provided");
    RecipeSchema SCHEMA = new RecipeSchema(ITEM_OR_TAG_ID, POWER_PROVIDED, TIER, FACTION);
}