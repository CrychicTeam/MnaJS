package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.MnaJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

import java.util.Map;

public class PowerProvidedComponent {
    public static RecipeComponent<PowerProvided> POWER_PROVIDED_COMPONENT = new RecipeComponent<PowerProvided>() {
        @Override
        public Class<?> componentClass() {
            return PowerProvided.class;
        }

        @Override
        public String componentType() {
            return "power_requirements";
        }

        @Override
        public JsonElement write(RecipeJS recipe, PowerProvided value) {
            JsonObject json = new JsonObject();
            json.addProperty("affinity", value.affinity.toString());
            json.addProperty("amount", value.amount);
            return json;
        }

        @Override
        public PowerProvided read(RecipeJS recipe, Object from) {
            if (from instanceof Map map) {
                Object affinityObj = map.get("affinity");
                Object amountObj = map.get("amount");

                String affinityStr = affinityObj != null ? affinityObj.toString() : "";
                float amount = amountObj instanceof Number ? ((Number) amountObj).floatValue() : 0f;

                Affinity affinity;
                try {
                    affinity = Affinity.valueOf(affinityStr);
                } catch (Exception e) {
                    MnaJS.LOGGER.error("Invalid affinity: " + affinityStr);
                    affinity = Affinity.UNKNOWN;
                }

                return new PowerProvided(affinity, amount);
            }

            return new PowerProvided(Affinity.UNKNOWN, 0f);
        }
    };
}
