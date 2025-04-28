package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaweavingAltarBuilder extends ItemAndPatternBuilder {
    private ResourceLocation enchantment = null;
    private int enchantmentMagnitude = 1;
    private boolean copyNBT = false;

    @Info("Add an input item for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Manaweaving recipe using an Item")
    @Override
    public ManaweavingAltarBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @Info("Add an input item for the Manaweaving recipe using a string")
    @Override
    public ManaweavingAltarBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Add a pattern for the Manaweaving recipe using a string")
    @Override
    public ManaweavingAltarBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output item for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder output(ResourceLocation output) {
        super.output(output);
        return this;
    }

    @Info("Set the output item for the Manaweaving recipe using an Item")
    public ManaweavingAltarBuilder output(Item output) {
        this.output = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Manaweaving recipe using a string")
    @Override
    public ManaweavingAltarBuilder output(String output) {
        return output(new ResourceLocation(output));
    }

    @Info("Set the output quantity for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder outputQuantity(int quantity) {
        super.outputQuantity(quantity);
        return this;
    }

    @Info("Set the NBT data for the output item")
    @Override
    public ManaweavingAltarBuilder outputNBT(JsonObject nbt) {
        super.outputNBT(nbt);
        return this;
    }

    @Info("Set the enchantment for the Manaweaving recipe")
    public ManaweavingAltarBuilder enchantment(ResourceLocation enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    @Info("Set the enchantment for the Manaweaving recipe using a string")
    public ManaweavingAltarBuilder enchantment(String enchantment) {
        this.enchantment = new ResourceLocation(enchantment);
        return this;
    }

    @Info("Set the enchantment magnitude for the Manaweaving recipe")
    public ManaweavingAltarBuilder enchantmentMagnitude(int magnitude) {
        this.enchantmentMagnitude = magnitude;
        return this;
    }

    @Info("Set whether to copy NBT data for the Manaweaving recipe")
    public ManaweavingAltarBuilder copyNBT(boolean copyNBT) {
        this.copyNBT = copyNBT;
        return this;
    }

    @Override
    protected int maxItems() {
        return 9;
    }

    @Override
    protected int maxPatterns() {
        return 6;
    }

    @Override
    protected String getRecipeType() {
        return "mna:manaweaving";
    }

    @Info("get the JsonObject for event.custom()")
    @Override
    public JsonObject build() {
        JsonObject json = super.build();
        if (enchantment != null) {
            json.addProperty("enchant", enchantment.toString());
            if (enchantmentMagnitude > 1) {
                json.addProperty("magnitude", enchantmentMagnitude);
            }
        }
        if (copyNBT) {
            json.addProperty("copy_nbt", true);
        }
        if (enchantment != null) {
            json.addProperty("enchant", enchantment.toString());
            if (enchantmentMagnitude > 1) {
                json.addProperty("magnitude", enchantmentMagnitude);
            }
        }
        if (copyNBT) {
            json.addProperty("copy_nbt", true);
        }

        return json;
    }
}