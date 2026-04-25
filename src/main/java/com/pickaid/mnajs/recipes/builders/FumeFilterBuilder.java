package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FumeFilterBuilder extends MABaseBuilder {
    private MnaItemOrTag itemOrTag;
    private String affinity;
    private Float powerAmount;

    @Info("Set the item or tag input for the Fume Filter recipe")
    public FumeFilterBuilder item(MnaItemOrTag itemOrTag) {
        this.itemOrTag = itemOrTag;
        return this;
    }

    public FumeFilterBuilder input(MnaItemOrTag itemOrTag) {
        return item(itemOrTag);
    }

    @HideFromJS
    public FumeFilterBuilder item(ResourceLocation itemOrTag) {
        return item(MnaItemOrTag.parse(itemOrTag));
    }

    @HideFromJS
    public FumeFilterBuilder item(Item item) {
        return item(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public FumeFilterBuilder item(ItemStack item) {
        return item(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public FumeFilterBuilder item(String itemOrTag) {
        return item(MnaItemOrTag.parse(itemOrTag));
    }

    @HideFromJS
    public FumeFilterBuilder input(ResourceLocation itemOrTag) {
        return input(MnaItemOrTag.parse(itemOrTag));
    }

    @HideFromJS
    public FumeFilterBuilder input(Item item) {
        return input(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public FumeFilterBuilder input(ItemStack item) {
        return input(MnaItemOrTag.parse(item));
    }

    @HideFromJS
    public FumeFilterBuilder input(String itemOrTag) {
        return input(MnaItemOrTag.parse(itemOrTag));
    }

    @Info("Set the affinity type for power generation")
    public FumeFilterBuilder affinity(String affinity) {
        this.affinity = affinity;
        return this;
    }

    @HideFromJS
    public FumeFilterBuilder affinity(Affinity affinity) {
        return affinity(String.valueOf(affinity));
    }

    @Info("Set the amount of power generated")
    public FumeFilterBuilder powerAmount(float amount) {
        this.powerAmount = amount;
        return this;
    }

    @Info("Set both affinity and amount for the power output")
    public FumeFilterBuilder powerProvided(String affinity, Number amount) {
        return affinity(affinity).powerAmount(amount.floatValue());
    }

    @HideFromJS
    public FumeFilterBuilder powerProvided(Affinity affinity, Number amount) {
        return powerProvided(String.valueOf(affinity), amount);
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:eldrin-fume");

        if (itemOrTag == null) {
            throw new IllegalStateException("Item or tag ID cannot be null");
        }
        json.addProperty("item", itemOrTag.scriptValue());

        if (affinity == null || affinity.isEmpty()) {
            throw new IllegalStateException("Affinity cannot be null or empty");
        }

        if (powerAmount == null || powerAmount <= 0) {
            throw new IllegalStateException("Power amount must be greater than 0");
        }

        JsonObject powerProvided = new JsonObject();
        powerProvided.addProperty("affinity", affinity);
        powerProvided.addProperty("amount", powerAmount);
        json.add("power_provided", powerProvided);

        return json;
    }
}
