package com.pickaid.mnajs.recipes.schema.base;

import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;

public interface TierBaseSchema {
    RecipeKey<Integer> TIER = NumberComponent.intRange(1 , 5).key("tier").optional(1);
    RecipeKey<MnaFactionId> FACTION = MnaRecipeComponents.FACTION_ID.key("requiredFaction").optional(MnaFactionId.parse("mna:none"));
}
