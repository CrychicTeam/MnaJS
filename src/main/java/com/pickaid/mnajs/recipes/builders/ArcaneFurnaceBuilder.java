package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ArcaneFurnaceBuilder extends MABaseBuilder {
    private ResourceLocation inputItem;
    private ResourceLocation outputItem;
    private int burnTime;
    private int outputQuantity = 1;

    @Info("Set the input item for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder input(ResourceLocation input) {
        this.inputItem = MATags.lookupItem(input).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the input item for the Arcane Furnace recipe using an Item")
    public ArcaneFurnaceBuilder input(Item input) {
        this.inputItem = ForgeRegistries.ITEMS.getKey(input);
        return this;
    }

    @Info("Set the input item for the Arcane Furnace recipe using a string")
    public ArcaneFurnaceBuilder input(String input) {
        return input(new ResourceLocation(input));
    }

    @Info("Set the output item for the Arcane Furnace recipe")
    public ArcaneFurnaceBuilder output(ResourceLocation output) {
        this.outputItem = MATags.lookupItem(output).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the output item for the Arcane Furnace recipe using an Item")
    public ArcaneFurnaceBuilder output(Item output) {
        this.outputItem = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Arcane Furnace recipe using a string")
    public ArcaneFurnaceBuilder output(String output) {
        return output(new ResourceLocation(output));
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
        } else {
            json.addProperty("input", inputItem.toString());
        }

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        } else {
            json.addProperty("output", outputItem.toString());
        }

        if (burnTime <= 0) {
            throw new IllegalStateException("Burn time must be greater than 0");
        } else {
            json.addProperty("burnTime", burnTime);
        }

        if (outputQuantity > 0) {
            json.addProperty("outputQuantity", outputQuantity);
        }

        return json;
    }
}