package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class RuneScribingBuilder extends MABaseBuilder {
    private long hMutex;
    private long vMutex;
    private ResourceLocation outputItem;

    @Info("Set the horizontal mutex value for the Rune Scribing recipe")
    public RuneScribingBuilder hMutex(long mutex) {
        this.hMutex = mutex;
        return this;
    }

    @Info("Set the vertical mutex value for the Rune Scribing recipe")
    public RuneScribingBuilder vMutex(long mutex) {
        this.vMutex = mutex;
        return this;
    }

    @Info("Set the output item for the Rune Scribing recipe")
    public RuneScribingBuilder output(ResourceLocation output) {
        this.outputItem = MATags.lookupItem(output).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the output item for the Rune Scribing recipe using an Item")
    public RuneScribingBuilder output(Item output) {
        this.outputItem = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Rune Scribing recipe using a string")
    public RuneScribingBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:runescribing");
        json.addProperty("mutex_h", hMutex);
        json.addProperty("mutex_v", vMutex);

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        } else {
            json.addProperty("output", outputItem.toString());
        }
        return json;
    }
}