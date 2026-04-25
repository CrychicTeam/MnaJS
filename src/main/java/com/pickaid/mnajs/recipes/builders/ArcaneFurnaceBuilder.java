package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ArcaneFurnaceBuilder extends MABaseBuilder {
    private MnaItemId inputItem;
    private MnaItemId outputItem;
    private Integer burnTime;
    private int outputQuantity = 1;

    @Info("Set the input item for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder input(MnaItemId input) {
        this.inputItem = input;
        return this;
    }

    @HideFromJS
    public ArcaneFurnaceBuilder input(ResourceLocation input) {
        return input(MnaItemId.of(input));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder input(Item input) {
        return input(MnaItemId.parse(input));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder input(ItemStack input) {
        return input(MnaItemId.parse(input));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder input(String input) {
        return input(MnaItemId.parse(input));
    }

    @Info("Set the output item for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder output(MnaItemId output) {
        this.outputItem = output;
        return this;
    }

    @HideFromJS
    public ArcaneFurnaceBuilder output(ResourceLocation output) {
        return output(MnaItemId.of(output));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder output(ItemStack output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public ArcaneFurnaceBuilder output(String output) {
        return output(MnaItemId.parse(output));
    }

    @Info("Set the burn time for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder burnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    @Info("Set the output quantity for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder outputQuantity(int quantity) {
        this.outputQuantity = quantity;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:arcane-furnace");

        if (inputItem == null) {
            throw new IllegalStateException("Input item cannot be null");
        }
        json.addProperty("input", inputItem.id());

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        }
        json.addProperty("output", outputItem.id());

        if (burnTime == null || burnTime <= 0) {
            throw new IllegalStateException("Burn time must be greater than 0");
        }
        json.addProperty("burnTime", burnTime);

        if (outputQuantity <= 0) {
            throw new IllegalStateException("Output quantity must be greater than 0");
        }
        json.addProperty("outputQuantity", outputQuantity);

        return json;
    }
}
