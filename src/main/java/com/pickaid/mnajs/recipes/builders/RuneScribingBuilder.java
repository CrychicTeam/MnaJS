package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneScribingBuilder extends MABaseBuilder {
    private Long hMutex;
    private Long vMutex;
    private MnaItemId outputItem;

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
    public RuneScribingBuilder output(MnaItemId output) {
        this.outputItem = output;
        return this;
    }

    @HideFromJS
    public RuneScribingBuilder output(ResourceLocation output) {
        return output(MnaItemId.of(output));
    }

    @HideFromJS
    public RuneScribingBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public RuneScribingBuilder output(ItemStack output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public RuneScribingBuilder output(String output) {
        return output(MnaItemId.parse(output));
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:runescribing");

        if (hMutex == null) {
            throw new IllegalStateException("Horizontal mutex cannot be null");
        }
        json.addProperty("mutex_h", hMutex);

        if (vMutex == null) {
            throw new IllegalStateException("Vertical mutex cannot be null");
        }
        json.addProperty("mutex_v", vMutex);

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        }
        json.addProperty("output", outputItem.id());

        return json;
    }
}
