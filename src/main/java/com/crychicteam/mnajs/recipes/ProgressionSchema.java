package com.crychicteam.mnajs.recipes;

import com.crychicteam.mnajs.recipes.components.ProgressionAdvancementComponent;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;

public interface ProgressionSchema {
    RecipeKey<ResourceLocation> ADVANCEMENT = ProgressionAdvancementComponent.INSTANCE.key("advancement");
    RecipeKey<Integer> TIER = NumberComponent.intRange(1,4).key("tier");
    RecipeKey<String> DESC = StringComponent.ANY.key("desc").optional("");

    class ProgressionRecipeJS extends RecipeJS {}

    RecipeSchema SCHEMA = new RecipeSchema(ProgressionRecipeJS.class, ProgressionRecipeJS::new, ADVANCEMENT, TIER, DESC);
}
