package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.PowerProvided;
import com.pickaid.mnajs.recipes.component.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.MnaJSRecipeKeys;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public interface EldrinAltarSchema extends TierBaseSchema {
    RecipeKey<ItemStack> OUTPUT = ItemBaseSchema.OUTPUT;
    RecipeKey<Item[]> INPUTS = MnaJSRecipeKeys.INPUTS;
    RecipeKey<PowerProvided> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.key("power_requirements");
    RecipeKey<Integer[]> COLOR = NumberComponent.INT.asArray().key("colors").optional(new Integer[0]).allowEmpty();
    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INPUTS, POWER_PROVIDED, COLOR);
}
