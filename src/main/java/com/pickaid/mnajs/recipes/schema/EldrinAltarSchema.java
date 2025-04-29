package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface EldrinAltarSchema extends ItemsPatternSchema {
    RecipeKey<RecipeComponentBuilderMap[]> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.inputRole().asArray().key("power_requirements");
    RecipeKey<Integer[]> COLOR = NumberComponent.INT.asArray().key("colors").optional(new Integer[0]).allowEmpty();

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INPUTS, POWER_PROVIDED, COLOR, TIER, FACTION);
}