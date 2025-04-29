package com.pickaid.mnajs.recipes.schema.base;

import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.ItemsOrTagsComponent;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ItemsPatternSchema extends TierBaseSchema{
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEMSTACK.key("output");
    RecipeKey<Either<TagKey<Item>, Item>[]> INPUTS = ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT.key("items");
    RecipeKey<String[]> PATTERNS = StringComponent.ID.asArray().key("patterns").defaultOptional().allowEmpty();
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("outputQuantity").optional(1).alt("quantity");
}
