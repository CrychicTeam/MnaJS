package com.crychicteam.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class ProgressionRecipeBuilder {
    private ResourceLocation advancement;
    private int tier = 1;
    private String _desc = "";

    public ProgressionRecipeBuilder advancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return this;
    }

    public ProgressionRecipeBuilder tier(int tier) {
        this.tier = tier;
        return this;
    }

    public ProgressionRecipeBuilder _desc(String desc) {
        this._desc = desc;
        return this;
    }

    public JsonObject build() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "mna:progression-condition");
        json.addProperty("tier", tier);
        if (advancement == null) {
            throw new IllegalStateException("Advancement cannot be null");
        } else {
            json.addProperty("advancement", advancement.toString());
        }
        json.addProperty("desc", _desc);
        return json;
    }
}
