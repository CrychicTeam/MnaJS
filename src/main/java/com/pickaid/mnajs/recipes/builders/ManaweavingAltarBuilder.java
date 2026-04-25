package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaIds;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ManaweavingAltarBuilder extends ItemAndPatternBuilder {
    private ResourceLocation enchantment;
    private int enchantmentMagnitude = 1;
    private boolean copyNBT = false;

    @Info("Add an input item for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder addItem(MnaItemOrTag item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder addItem(Item item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder addItem(ResourceLocation item) {
        super.addItem(item);
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder addItem(String item) {
        super.addItem(item);
        return this;
    }

    @Info("Add a pattern for the Manaweaving recipe")
    @Override
    public ManaweavingAltarBuilder addPattern(MnaManaweavePatternId pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output item for the Manaweaving recipe")
    public ManaweavingAltarBuilder output(MnaItemId output) {
        this.output = output.location();
        return this;
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @HideFromJS
    public ManaweavingAltarBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    @Override
    public ManaweavingAltarBuilder output(String output) {
        return output(MnaItemId.parse(output));
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

    @HideFromJS
    public ManaweavingAltarBuilder enchantment(ResourceLocation enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    @Info("Set the enchantment for the Manaweaving recipe")
    public ManaweavingAltarBuilder enchantment(String enchantment) {
        this.enchantment = MnaIds.parse(enchantment, "enchant", "minecraft", null);
        return this;
    }

    public ManaweavingAltarBuilder enchant(String enchantment) {
        return enchantment(enchantment);
    }

    @Info("Set the enchantment magnitude for the Manaweaving recipe")
    public ManaweavingAltarBuilder enchantmentMagnitude(int magnitude) {
        this.enchantmentMagnitude = magnitude;
        return this;
    }

    public ManaweavingAltarBuilder magnitude(int magnitude) {
        return enchantmentMagnitude(magnitude);
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
        return "mna:manaweaving-recipe";
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

        return json;
    }
}
