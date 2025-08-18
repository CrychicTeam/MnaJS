package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonPrimitive;
import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.MnaJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;

import java.util.Locale;

public interface PowerProvidedComponent {
    RecipeComponent<Affinity> AFFINITY_COMPONENT = new RecipeComponent<>() {

        @Override
        public String componentType() {
            return "enum";
        }

        @Override
        public Class<?> componentClass() {
            return Affinity.class;
        }

        @Override
        public JsonPrimitive write(RecipeJS recipe, Affinity value) {
            return new JsonPrimitive(String.valueOf(value).toUpperCase(Locale.ROOT));
        }

        @Override
        public Affinity read(RecipeJS recipe, Object from) {
            if (from instanceof Affinity affinity) {
                return affinity;
            } else {
                Affinity affinity = Affinity.UNKNOWN;
                if (String.valueOf(from).equals("AIR")) affinity = Affinity.WIND;
                try {
                    affinity = Affinity.valueOf(String.valueOf(from).toUpperCase());
                } catch (Exception e) {
                }
                var e = from == null ? null : from instanceof JsonPrimitive j ? j.getAsString() : String.valueOf(from).toUpperCase();
                if (e == null) {
                    return affinity;
                }
                return Affinity.valueOf(e);
            }
        }

        @Override
        public String toString() {
            return componentType();
        }
    };
    RecipeKey<Affinity> AFFINITY = AFFINITY_COMPONENT.key("affinity").noBuilders();
    RecipeComponentBuilder POWER_PROVIDED_COMPONENT =  new RecipeComponentBuilder(2).add(AFFINITY).add(NumberComponent.FLOAT.key("amount"));
}
