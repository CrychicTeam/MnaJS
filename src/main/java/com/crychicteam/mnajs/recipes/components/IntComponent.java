package com.crychicteam.mnajs.recipes.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

/**
 * @author M1hono
 */
public class IntComponent implements RecipeComponent<Integer> {
    public static final IntComponent INT = new IntComponent();

    @Override
    public Integer read(RecipeJS recipe, Object from) {
        if (from instanceof Number) {
            return ((Number) from).intValue();
        } else if (from instanceof String) {
            try {
                return Integer.parseInt((String) from);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public Class<?> componentClass() {
        return Integer.class;
    }

    @Override
    public JsonElement write(RecipeJS recipe, Integer value) {
        return new JsonPrimitive(value);
    }

    @Override
    public String componentType() {
        return "int";
    }
}