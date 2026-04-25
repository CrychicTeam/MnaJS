package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pickaid.mnajs.kubejs.id.MnaTypedId;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

import java.util.function.Function;

public final class MnaTypedIdRecipeComponent<T extends MnaTypedId> implements RecipeComponent<T> {
    private final Class<T> type;
    private final Function<Object, T> parser;

    public MnaTypedIdRecipeComponent(Class<T> type, Function<Object, T> parser) {
        this.type = type;
        this.parser = parser;
    }

    @Override
    public Class<?> componentClass() {
        return type;
    }

    @Override
    public JsonElement write(RecipeJS recipe, T value) {
        return new JsonPrimitive(value.id());
    }

    @Override
    public T read(RecipeJS recipe, Object from) {
        return from == null ? null : parser.apply(from);
    }
}
