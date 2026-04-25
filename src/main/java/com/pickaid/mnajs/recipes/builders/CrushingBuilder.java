package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CrushingBuilder extends MABaseBuilder {
    private MnaItemId inputItem;
    private MnaItemId outputItem;
    private int outputQuantity = 1;

    @Info("Set the input item for the Crushing recipe")
    public CrushingBuilder input(MnaItemId input) {
        this.inputItem = input;
        return this;
    }

    @HideFromJS
    public CrushingBuilder input(ResourceLocation input) {
        return input(MnaItemId.of(input));
    }

    @HideFromJS
    public CrushingBuilder input(Item input) {
        return input(MnaItemId.parse(input));
    }

    @HideFromJS
    public CrushingBuilder input(ItemStack input) {
        return input(MnaItemId.parse(input));
    }

    @HideFromJS
    public CrushingBuilder input(String input) {
        return input(MnaItemId.parse(input));
    }

    @Info("Set the output item for the Crushing recipe")
    public CrushingBuilder output(MnaItemId output) {
        this.outputItem = output;
        return this;
    }

    @HideFromJS
    public CrushingBuilder output(ResourceLocation output) {
        return output(MnaItemId.of(output));
    }

    @HideFromJS
    public CrushingBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public CrushingBuilder output(ItemStack output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public CrushingBuilder output(String output) {
        return output(MnaItemId.parse(output));
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
        }
        json.addProperty("input", inputItem.id());

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        }
        json.addProperty("output", outputItem.id());

        if (outputQuantity <= 0) {
            throw new IllegalStateException("Output quantity must be greater than 0");
        }
        json.addProperty("output_quantity", outputQuantity);

        return json;
    }
}
