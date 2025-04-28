package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class FumeFilterBuilder extends MABaseBuilder {
    private ResourceLocation itemOrTagID;
    private String affinity;
    private float powerAmount;

    @Info("Set the item or tag ID for the Fume Filter recipe")
    public FumeFilterBuilder item(ResourceLocation itemOrTag) {
        if (MATags.smartLookupItem(itemOrTag).size() <= 1) throw new IllegalStateException("Item or tag ID cannot be invalid");
        this.itemOrTagID = itemOrTag;
        return this;
    }

    @Info("Set the item for the Fume Filter recipe using an Item")
    public FumeFilterBuilder item(Item item) {
        if (MATags.smartLookupItem(item.kjs$getIdLocation()).size() <= 1) throw new IllegalStateException("Item or tag ID cannot be invalid");
        this.itemOrTagID = ForgeRegistries.ITEMS.getKey(item);
        return this;
    }

    @Info("Set the item or tag for the Fume Filter recipe using a string")
    public FumeFilterBuilder item(String itemOrTag) {
        if (MATags.smartLookupItem(ResourceLocation.tryParse(itemOrTag)).size() <= 1) throw new IllegalStateException("Item or tag ID cannot be invalid");
        this.itemOrTagID = new ResourceLocation(itemOrTag);
        return this;
    }

    @Info("Set the affinity type for power generation")
    public FumeFilterBuilder affinity(String affinity) {
        this.affinity = affinity;
        return this;
    }

    @Info("Set the amount of power generated")
    public FumeFilterBuilder powerAmount(float amount) {
        this.powerAmount = amount;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:eldrin-fume");

        if (itemOrTagID == null) {
            throw new IllegalStateException("Item or tag ID cannot be null");
        } else {
            json.addProperty("item", itemOrTagID.toString());
        }

        if (affinity == null || affinity.isEmpty()) {
            throw new IllegalStateException("Affinity cannot be null or empty");
        }

        if (powerAmount <= 0) {
            throw new IllegalStateException("Power amount must be greater than 0");
        }

        JsonObject powerProvided = new JsonObject();
        powerProvided.addProperty("affinity", affinity);
        powerProvided.addProperty("amount", powerAmount);
        json.add("power_provided", powerProvided);

        return json;
    }
}