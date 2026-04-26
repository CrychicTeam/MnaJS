package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.resources.ResourceLocation;

public class ProgressionRecipeBuilder extends MABaseBuilder {
    private ResourceLocation advancement;
    private String description = "";

    @Info(value = "Set the advancement required by this progression recipe.", params = {
            @Param(name = "advancement", value = "Advancement id such as mna:tier_1/cast_a_spell.")
    })
    public ProgressionRecipeBuilder advancement(MnaAdvancementId advancement) {
        this.advancement = advancement.location();
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
