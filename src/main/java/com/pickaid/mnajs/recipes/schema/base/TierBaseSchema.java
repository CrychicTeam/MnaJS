package com.pickaid.mnajs.recipes.schema.base;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import net.minecraft.resources.ResourceLocation;

public interface TierBaseSchema {
    RecipeKey<Integer> TIER = NumberComponent.intRange(1 , 5).key("tier").optional(1);
    RecipeKey<String> FACTION = StringComponent.ID.key("requiredFaction").optional(new ResourceLocation("mna:none").toString());
}
