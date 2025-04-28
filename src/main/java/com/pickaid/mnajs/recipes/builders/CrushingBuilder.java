package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class CrushingBuilder extends MABaseBuilder {
    private ResourceLocation inputItem;
    private ResourceLocation outputItem;
    private int outputQuantity = 1;

    @Info("Set the input item for the Crushing recipe")
    public CrushingBuilder input(ResourceLocation input) {
        this.inputItem = MATags.lookupItem(input).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the input item for the Crushing recipe using an Item")
    public CrushingBuilder input(Item input) {
        this.inputItem = ForgeRegistries.ITEMS.getKey(input);
        return this;
    }

    @Info("Set the input item for the Crushing recipe using a string")
    public CrushingBuilder input(String input) {
        return input(new ResourceLocation(input));
    }

    @Info("Set the output item for the Crushing recipe")
    public CrushingBuilder output(ResourceLocation output) {
        this.outputItem = MATags.lookupItem(output).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the output item for the Crushing recipe using an Item")
    public CrushingBuilder output(Item output) {
        this.outputItem = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Crushing recipe using a string")
    public CrushingBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the output quantity for the Crushing recipe")
    public CrushingBuilder outputQuantity(int quantity) {
        this.outputQuantity = quantity;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:crushing");

        if (inputItem == null) {
            throw new IllegalStateException("Input item cannot be null");
        } else {
            json.addProperty("input", inputItem.toString());
        }

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        } else {
            json.addProperty("output", outputItem.toString());
        }

        if (outputQuantity > 0) {
            json.addProperty("output_quantity", outputQuantity);
        }
        return json;
    }
}