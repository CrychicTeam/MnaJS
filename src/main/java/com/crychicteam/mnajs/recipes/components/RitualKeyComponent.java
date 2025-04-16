package com.crychicteam.mnajs.recipes.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.util.MapJS;

import java.util.HashMap;
import java.util.Map;

public class RitualKeyComponent implements RecipeComponent<Map<String, RitualKeyComponent.RitualKey>> {
    public static final RitualKeyComponent INSTANCE = new RitualKeyComponent();

    public static class RitualKey {
        private String item;
        private boolean optional = false;
        private boolean consume = true;
        private boolean manualReturn = false;
        private boolean isDynamic = false;
        private boolean dynamicSource = false;

        public RitualKey(String item) {
            this.item = item;
        }

        public RitualKey(String item, boolean optional, boolean consume,
                         boolean manualReturn, boolean isDynamic, boolean dynamicSource) {
            this.item = item;
            this.optional = optional;
            this.consume = consume;
            this.manualReturn = manualReturn;
            this.isDynamic = isDynamic;
            this.dynamicSource = dynamicSource;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("item", item);

            if (optional) {
                json.addProperty("optional", true);
            }

            if (!consume) {
                json.addProperty("consume", false);
            }

            if (manualReturn) {
                json.addProperty("manual_return", true);
            }

            if (isDynamic) {
                json.addProperty("is_dynamic", true);
            }

            if (dynamicSource) {
                json.addProperty("dynamic_source", true);
            }

            return json;
        }

        public static RitualKey fromJson(JsonObject json) {
            if (!json.has("item")) {
                throw new IllegalArgumentException("Ritual key must have an item!");
            }

            String item = json.get("item").getAsString();
            boolean optional = json.has("optional") && json.get("optional").getAsBoolean();
            boolean consume = !json.has("consume") || json.get("consume").getAsBoolean();
            boolean manualReturn = json.has("manual_return") && json.get("manual_return").getAsBoolean();
            boolean isDynamic = json.has("is_dynamic") && json.get("is_dynamic").getAsBoolean();
            boolean dynamicSource = json.has("dynamic_source") && json.get("dynamic_source").getAsBoolean();

            return new RitualKey(item, optional, consume, manualReturn, isDynamic, dynamicSource);
        }
    }

    @Override
    public Class<?> componentClass() {
        return Map.class;
    }

    @Override
    public String componentType() {
        return "ritualKeys";
    }

    @Override
    public Map<String, RitualKey> read(RecipeJS recipe, Object from) {
        Map<String, RitualKey> result = new HashMap<>();

        if (from instanceof JsonObject jsonObject) {
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (entry.getValue() instanceof JsonObject obj) {
                    try {
                        result.put(entry.getKey(), RitualKey.fromJson(obj));
                    } catch (Exception e) {
                    }
                }
            }
        } else if (from instanceof Map) {
            ((Map<?, ?>) from).forEach((key, value) -> {
                if (key instanceof String) {
                    try {
                        if (value instanceof JsonObject obj) {
                            result.put((String) key, RitualKey.fromJson(obj));
                        } else if (value instanceof Map) {
                            JsonElement element = MapJS.json(value);
                            if (element instanceof JsonObject obj) {
                                result.put((String) key, RitualKey.fromJson(obj));
                            }
                        } else if (value instanceof String) {
                            result.put((String) key, new RitualKey((String) value));
                        } else if (value instanceof ItemStackJS) {
                            result.put((String) key, new RitualKey(((ItemStackJS) value).toString()));
                        }
                    } catch (Exception e) {
                    }
                }
            });
        }

        return result;
    }

    @Override
    public JsonElement write(RecipeJS recipe, Map<String, RitualKey> value) {
        JsonObject result = new JsonObject();

        for (Map.Entry<String, RitualKey> entry : value.entrySet()) {
            result.add(entry.getKey(), entry.getValue().toJson());
        }

        return result;
    }
}