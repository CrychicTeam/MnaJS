package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;

public class ProgressionRecipeBuilder extends MABaseBuilder {
    private ResourceLocation advancement;
    private String description = "";

    @Info("set the advancement needed of the progression")
    public ProgressionRecipeBuilder advancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return this;
    }

    @Info("Set the description of the progression (actually translation key)")
    public ProgressionRecipeBuilder desc(String desc) {
        this.description = desc;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:progression-condition");
        if (advancement == null) {
            throw new IllegalStateException("Advancement cannot be null");
        } else {
            json.addProperty("advancement", advancement.toString());
        }
        json.addProperty("desc", description);

        return json;
    }
}
