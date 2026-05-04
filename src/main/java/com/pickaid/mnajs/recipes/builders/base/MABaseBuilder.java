package com.pickaid.mnajs.recipes.builders.base;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.resources.ResourceLocation;

public abstract class MABaseBuilder {

    /**
     * Get the recipe type string (e.g., "mna:component")
     */
    protected String getRecipeType() {
        return "";
    };

    public int tier = 1;
    public ResourceLocation faction = new ResourceLocation("mna", "none");

    @Info("set the tier requirement")
    public MABaseBuilder tier(int value) {
        tier = value;
        return this;
    }

    @Info(value = "Set the faction requirement for this recipe.", params = {
            @Param(name = "value", value = "Faction id such as mna:council or yourmod:custom_faction.")
    })
    public MABaseBuilder faction(MnaFactionId value) {
        this.faction = value.location();
        return this;
    }

    public JsonObject build() {
        var json = new JsonObject();
        json.addProperty("tier", this.tier);
        json.addProperty("requiredFaction", this.faction.toString());
        return json;
    }
}
