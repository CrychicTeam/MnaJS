package com.pickaid.mnajs.recipes.schema;

import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.recipes.component.ItemOrTagComponent;
import com.pickaid.mnajs.recipes.component.mna.PowerProvided;
import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface FumerFliterSchema extends TierBaseSchema {
    RecipeKey<Either<TagKey<Item>, Item>> ITEM_OR_TAG_ID = ItemOrTagComponent.ITEM_OR_TAG_COMPONENT.key("item").defaultOptional().allowEmpty();
    RecipeKey<PowerProvided> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.key("power_provided");
    RecipeSchema SCHEMA = new RecipeSchema(ITEM_OR_TAG_ID, POWER_PROVIDED, TIER, FACTION);
}