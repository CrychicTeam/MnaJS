package com.pickaid.mnajs.recipes.builders.base;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;

public abstract class MABaseBuilder {

    /**
     * Get the recipe type string (e.g., "mna:component")
     */
    protected String getRecipeType() {
        return "";
    };

    public int tier = 1;
    public ResourceLocation faction = new ResourceLocation("mna:none");

    @Info("set the tier requirement")
    public MABaseBuilder tier(int value) {
        tier = value;
        return this;
    }

    @Info("set the faction requirement")
    public MABaseBuilder faction(ResourceLocation value) {
        this.faction = value;
        return this;
    }

    public JsonObject build() {
        var json = new JsonObject();
        json.addProperty("tier", this.tier);
        json.addProperty("requiredFaction", this.faction.toString());
        return json;
    }
}
