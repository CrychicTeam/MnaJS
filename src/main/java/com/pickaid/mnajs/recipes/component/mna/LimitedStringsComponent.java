package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LimitedStringsComponent {
    public static RecipeComponent<String[]> createWithMaxSize(int maxSize) {
        return new RecipeComponent<>() {
            @Override
            public Class<?> componentClass() {
                return String[].class;
            }

            @Override
            public JsonElement write(RecipeJS recipe, String[] values) {
                String[] limitedValues = values.length > maxSize ?
                        Arrays.copyOf(values, maxSize) : values;

                if (limitedValues.length < values.length) {
                    System.out.println("Warning: String array exceeded maximum size of " + maxSize +
                            ". Truncated from " + values.length + " to " + limitedValues.length + " elements.");
                }
                JsonArray json = new JsonArray();
                for (String value : limitedValues) {
                    json.add(new JsonPrimitive(value));
                }
                return json;
            }

            @Override
            public String[] read(RecipeJS recipe, Object from) {
                List<String> result = new ArrayList<>();
                try {
                    if (from instanceof String[] strings) {
                        result.addAll(Arrays.asList(strings));
                    } else if (from instanceof Collection<?> collection) {
                        for (Object obj : collection) {
                            if (obj instanceof String string) {
                                result.add(string);
                            } else if (obj != null) {
                                result.add(obj.toString());
                            }
                        }
                    } else if (from instanceof String string) {
                        result.add(string);
                    } else if (from != null) {
                        result.add(from.toString());
                    }
                    if (result.size() > maxSize) {
                        List<String> limitedResult = result.subList(0, maxSize);

                        System.out.println("Warning: Read " + result.size() +
                                " strings but limited to " + limitedResult.size() +
                                " due to maximum size restriction.");

                        return limitedResult.toArray(new String[0]);
                    }

                    return result.toArray(new String[0]);
                } catch (Exception e) {
                    System.err.println("Error reading string array: " + e.getMessage());
                    e.printStackTrace();
                    return new String[0];
                }
            }
        };
    }

    public static final RecipeComponent<String[]> MAX_6_STRINGS = createWithMaxSize(6);
    public static final RecipeComponent<String[]> MAX_9_STRINGS = createWithMaxSize(9);
}