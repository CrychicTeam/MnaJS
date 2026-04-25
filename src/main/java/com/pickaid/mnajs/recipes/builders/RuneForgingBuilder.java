package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneForgingBuilder extends MABaseBuilder {
    private MnaItemId patternItem;
    private MnaItemId outputItem;
    private MnaItemId materialItem;
    private int hits = 10;
    private int outputQuantity = 1;

    @Info("Set the pattern item for the Runeforging recipe")
    public RuneForgingBuilder pattern(MnaItemId pattern) {
        this.patternItem = pattern;
        return this;
    }

    @HideFromJS
    public RuneForgingBuilder pattern(ResourceLocation pattern) {
        return pattern(MnaItemId.of(pattern));
    }

    @HideFromJS
    public RuneForgingBuilder pattern(Item pattern) {
        return pattern(MnaItemId.parse(pattern));
    }

    @HideFromJS
    public RuneForgingBuilder pattern(ItemStack pattern) {
        return pattern(MnaItemId.parse(pattern));
    }

    @HideFromJS
    public RuneForgingBuilder pattern(String pattern) {
        return pattern(MnaItemId.parse(pattern));
    }

    @Info("Set the output item for the Runeforging recipe")
    public RuneForgingBuilder output(MnaItemId output) {
        this.outputItem = output;
        return this;
    }

    @HideFromJS
    public RuneForgingBuilder output(ResourceLocation output) {
        return output(MnaItemId.of(output));
    }

    @HideFromJS
    public RuneForgingBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public RuneForgingBuilder output(ItemStack output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public RuneForgingBuilder output(String output) {
        return output(MnaItemId.parse(output));
    }

    @Info("Set the material item for the Runeforging recipe")
    public RuneForgingBuilder material(MnaItemId material) {
        this.materialItem = material;
        return this;
    }

    @HideFromJS
    public RuneForgingBuilder material(ResourceLocation material) {
        return material(MnaItemId.of(material));
    }

    @HideFromJS
    public RuneForgingBuilder material(Item material) {
        return material(MnaItemId.parse(material));
    }

    @HideFromJS
    public RuneForgingBuilder material(ItemStack material) {
        return material(MnaItemId.parse(material));
    }

    @HideFromJS
    public RuneForgingBuilder material(String material) {
        return material(MnaItemId.parse(material));
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
        }
        json.addProperty("pattern", patternItem.id());

        if (outputItem == null) {
            throw new IllegalStateException("Output item cannot be null");
        }
        json.addProperty("output", outputItem.id());

        if (materialItem != null) {
            json.addProperty("material", materialItem.id());
        }

        if (hits <= 0) {
            throw new IllegalStateException("Hits must be greater than 0");
        }
        if (hits != 10) {
            json.addProperty("hits", hits);
        }

        if (outputQuantity <= 0) {
            throw new IllegalStateException("Output quantity must be greater than 0");
        }
        if (outputQuantity > 1) {
            json.addProperty("output_quantity", outputQuantity);
        }

        return json;
    }
}
