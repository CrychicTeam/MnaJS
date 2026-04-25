package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.recipe.TransmutationRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface TransmutationSchema extends TierBaseSchema {
    RecipeKey<MnaBlockId> TARGET_BLOCK = MnaRecipeComponents.BLOCK_ID.key("targetBlock");
    RecipeKey<MnaBlockId> REPLACE_BLOCK = MnaRecipeComponents.BLOCK_ID.key("replaceBlock").defaultOptional().allowEmpty();
    RecipeKey<MnaLootTableId> LOOTTABLE = MnaRecipeComponents.LOOT_TABLE_ID.key("lootTable").defaultOptional().allowEmpty();
    RecipeKey<MnaItemId> REPRESENTATION_ITEM = MnaRecipeComponents.ITEM_ID.key("representationItem").defaultOptional().allowEmpty();

    RecipeSchema SCHEMA = new RecipeSchema(TransmutationRecipeJS.class, TransmutationRecipeJS::new, TARGET_BLOCK, REPLACE_BLOCK, LOOTTABLE, REPRESENTATION_ITEM, TIER, FACTION)
            .constructor()
            .constructor(TARGET_BLOCK, REPLACE_BLOCK)
            .constructor(TARGET_BLOCK, LOOTTABLE, REPRESENTATION_ITEM);
}
