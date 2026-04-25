package com.pickaid.mnajs.recipes.schema.base;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import net.minecraft.world.item.ItemStack;

public interface ItemsPatternSchema extends TierBaseSchema{
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEMSTACK.key("output");
    RecipeKey<MnaItemOrTag[]> INPUTS = MnaRecipeComponents.ITEM_OR_TAG.asArray().key("items");
    RecipeKey<MnaManaweavePatternId[]> PATTERNS = MnaRecipeComponents.MANAWEAVE_PATTERN_ID.asArray().key("patterns").defaultOptional().allowEmpty();
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("quantity").preferred("outputQuantity").optional(1).alt("outputQuantity");
}
