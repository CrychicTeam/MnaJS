package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.recipe.EldrinAltarRecipeJS;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;

public interface EldrinAltarSchema extends TierBaseSchema {
    RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEMSTACK.key("output");
    RecipeKey<MnaItemOrTag[]> INPUTS = MnaRecipeComponents.ITEM_OR_TAG.asArray().key("inputs").optional(type -> new MnaItemOrTag[0]).allowEmpty();
    RecipeKey<RecipeComponentBuilderMap[]> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.inputRole().asArray().key("power_requirements").optional(type -> new RecipeComponentBuilderMap[0]);
    RecipeKey<Integer[]> COLOR = NumberComponent.INT.asArray().key("colors").optional(new Integer[0]).allowEmpty();
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("count").optional(1).alt("quantity");

    RecipeSchema SCHEMA = new RecipeSchema(EldrinAltarRecipeJS.class, EldrinAltarRecipeJS::new, OUTPUT, INPUTS, POWER_PROVIDED, COLOR, QUANTITY, TIER, FACTION)
            .constructor()
            .constructor((recipe, schemaType, keys, from) -> {
                recipe.setValue(OUTPUT, from.getValue(recipe, OUTPUT));
                recipe.setValue(INPUTS, from.getValue(recipe, INPUTS));
            }, OUTPUT, INPUTS);
}
