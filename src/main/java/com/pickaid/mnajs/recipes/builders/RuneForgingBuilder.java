package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class RuneForgingBuilder extends MABaseBuilder {
    private ResourceLocation patternItem;
    private ResourceLocation outputItem;
    private ResourceLocation materialItem = null;
    private int hits = 10;
    private int outputQuantity = 1;

    @Info("Set the pattern item for the Runeforging recipe")
    public RuneForgingBuilder pattern(ResourceLocation pattern) {
        this.patternItem = MATags.lookupItem(pattern).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the pattern item for the Runeforging recipe using an Item")
    public RuneForgingBuilder pattern(Item pattern) {
        this.patternItem = ForgeRegistries.ITEMS.getKey(pattern);
        return this;
    }

    @Info("Set the pattern item for the Runeforging recipe using a string")
    public RuneForgingBuilder pattern(String pattern) {
        return pattern(new ResourceLocation(pattern));
    }

    @Info("Set the output item for the Runeforging recipe")
    public RuneForgingBuilder output(ResourceLocation output) {
        this.outputItem = MATags.lookupItem(output).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the output item for the Runeforging recipe using an Item")
    public RuneForgingBuilder output(Item output) {
        this.outputItem = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Runeforging recipe using a string")
    public RuneForgingBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the material item for the Runeforging recipe (optional, defaults to superheated vinteum ingot)")
    public RuneForgingBuilder material(ResourceLocation material) {
        this.materialItem = MATags.lookupItem(material).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the material item for the Runeforging recipe using an Item")
    public RuneForgingBuilder material(Item material) {
        this.materialItem = ForgeRegistries.ITEMS.getKey(material);
        return this;
    }

    @Info("Set the material item for the Runeforging recipe using a string")
    public RuneForgingBuilder material(String material) {
        return material(new ResourceLocation(material));
    }

    @Info("Set the number of hits required for the Runeforging recipe")
    public RuneForgingBuilder hits(int hits) {
        this.hits = hits;
        return this;
    }

    @Info("Set the output quantity for the Runeforging recipe")
    public RuneForgingBuilder outputQuantity(int quantity) {
        this.outputQuantity = quantity;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:runeforging");

        if (patternItem == null) {
            throw new IllegalStateException("Pattern item cannot be null");
        } else {
            json.addProperty("pattern", patternItem.toString());
        }

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        } else {
            json.addProperty("output", outputItem.toString());
        }

        if (materialItem != null) {
            json.addProperty("material", materialItem.toString());
        }

        if (hits != 10) {
            json.addProperty("hits", hits);
        }

        if (outputQuantity > 1) {
            json.addProperty("output_quantity", outputQuantity);
        }
        return json;
    }
}