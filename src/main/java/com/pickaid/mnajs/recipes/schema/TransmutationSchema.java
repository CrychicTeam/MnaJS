package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.MnaJSRecipeKeys;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BlockComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface TransmutationSchema extends TierBaseSchema, ItemBaseSchema {
    RecipeKey<Block> TARGET_BLOCK = BlockComponent.BLOCK.key("targetBlock");
    RecipeKey<Block> REPLACE_BLOCK = BlockComponent.BLOCK.key("replaceBlock").defaultOptional().allowEmpty();
    RecipeKey<String> LOOTTABLE = StringComponent.ID.key("lootTable").defaultOptional().allowEmpty();
    RecipeKey<Item> REPRESENTATION_ITEM = INPUT.defaultOptional().allowEmpty();

    RecipeSchema SCHEMA = new RecipeSchema(TARGET_BLOCK, REPLACE_BLOCK, LOOTTABLE, REPRESENTATION_ITEM, TIER, FACTION);
}