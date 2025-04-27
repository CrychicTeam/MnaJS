package com.pickaid.mnajs.recipes;

import com.pickaid.mnajs.recipes.builders.ProgressionRecipeBuilder;
import com.pickaid.mnajs.recipes.builders.RitualRecipeBuilder;
import dev.latvian.mods.kubejs.typings.Info;

public class RecipesHelper {
    @Info("The Helper for creating rituals using event.custom().")
    public static RitualRecipeBuilder ritual = new RitualRecipeBuilder();
    @Info("The Helper for creating progression recipes using event.custom().")
    public static ProgressionRecipeBuilder progression = new ProgressionRecipeBuilder();
}
