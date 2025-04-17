package com.crychicteam.mnajs.recipes.components;

import dev.latvian.mods.kubejs.recipe.component.ArrayRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;

import java.util.Map;

public class MAComponents {
    public static final IntComponent INT = IntComponent.INT;
    public static final ArrayRecipeComponent<Integer> INT_ARRAY =
            new ArrayRecipeComponent<>(INT, false, Integer[].class, new Integer[0]);
    public static final ArrayRecipeComponent<Integer[]> INT_ARRAY_ARRAY =
            new ArrayRecipeComponent<>(INT_ARRAY, false, Integer[][].class, new Integer[0][]);
    public static final RecipeComponent<Boolean> BOOLEAN = BooleanComponent.BOOLEAN;
    public static final RecipeComponent<String> STRING = StringComponent.ANY;
    public static final ArrayRecipeComponent<String> STRING_ARRAY =
            new ArrayRecipeComponent<>(STRING, false, String[].class, new String[0]);
    public static final RitualKeyComponent RITUAL_KEYS = RitualKeyComponent.INSTANCE;

    public static ArrayRecipeComponent<Integer> intArray() {
        return INT_ARRAY;
    }

    public static ArrayRecipeComponent<Integer[]> intArrayArray() {
        return INT_ARRAY_ARRAY;
    }

    public static ArrayRecipeComponent<String> stringArray() {
        return STRING_ARRAY;
    }

    public static RecipeComponent<Map<String, RitualKeyComponent.RitualKey>> ritualKeys() {
        return RITUAL_KEYS;
    }

    public static RecipeComponent<Boolean> bool() {
        return BOOLEAN;
    }
}