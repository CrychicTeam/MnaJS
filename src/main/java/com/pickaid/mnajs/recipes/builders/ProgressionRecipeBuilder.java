package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaIds;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public class ProgressionRecipeBuilder extends MABaseBuilder {
    private ResourceLocation advancement;
    private String description = "";

    @Info("set the advancement needed of the progression")
    @HideFromJS
    public ProgressionRecipeBuilder advancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return this;
    }

    @Info("set the advancement needed of the progression")
    public ProgressionRecipeBuilder advancement(String advancement) {
        this.advancement = MnaIds.parse(advancement, "advancement", "minecraft", null);
        return this;
    }

    @Info("Set the description of the progression (actually translation key)")
    public ProgressionRecipeBuilder desc(String desc) {
        this.description = desc;
        return this;
    }

    public ProgressionRecipeBuilder description(String desc) {
        return desc(desc);
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
