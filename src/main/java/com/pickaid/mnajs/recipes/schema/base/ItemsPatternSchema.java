package com.pickaid.mnajs.recipes.schema.base;

import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.mna.LimitedItemsOrTagsComponent;
import com.pickaid.mnajs.recipes.component.mna.LimitedStringsComponent;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ItemsPatternSchema extends TierBaseSchema{
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEMSTACK.key("output");
    RecipeKey<List<Either<TagKey<Item>, Item>>> INPUTS = LimitedItemsOrTagsComponent.MAX_9_ITEMS.key("items");
    RecipeKey<String[]> PATTERNS = LimitedStringsComponent.MAX_9_STRINGS.key("patterns").defaultOptional().allowEmpty();
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("outputQuantity").optional(1).alt("quantity");
}
