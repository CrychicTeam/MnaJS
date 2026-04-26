package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

public final class MnaItemOrTagRecipeComponent implements RecipeComponent<MnaItemOrTag> {
    @Override
    public Class<?> componentClass() {
        return MnaItemOrTag.class;
    }

    @Override
    public JsonElement write(RecipeJS recipe, MnaItemOrTag value) {
        return new JsonPrimitive(value.recipeValue());
    }

    @Override
    public MnaItemOrTag read(RecipeJS recipe, Object from) {
        return from == null ? null : MnaItemOrTag.parse(from);
    }
}
