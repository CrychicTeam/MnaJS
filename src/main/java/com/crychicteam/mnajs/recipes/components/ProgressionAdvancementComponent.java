package com.crychicteam.mnajs.recipes.components;

import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.resources.ResourceLocation;

public class ProgressionAdvancementComponent implements RecipeComponent<ResourceLocation> {
    public static final RecipeComponent<ResourceLocation> INSTANCE = new ProgressionAdvancementComponent();
    @Override
    public Class<?> componentClass() {
        return ResourceLocation.class;
    }

    @Override
    public JsonPrimitive write(RecipeJS recipe, ResourceLocation value) {
        return new JsonPrimitive(value.toString());
    }

    @Override
    public ResourceLocation read(RecipeJS recipe, Object from) {
        if (from instanceof String) {
            return new ResourceLocation((String) from);
        } else if (from instanceof ResourceLocation) {
            return (ResourceLocation) from;
        }
        return new ResourceLocation("mna", "default");
    }
}
