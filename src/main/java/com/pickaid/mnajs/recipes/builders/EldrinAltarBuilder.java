package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class EldrinAltarBuilder extends ItemAndPatternBuilder {
    private String data;
    private final Map<String, Float> powerRequirements = new HashMap<>();
    private final int[] colors = new int[]{0, 0};

    @Info("Add an input item / TagKey for the Eldrin Altar recipe using an ResourceLocation")
    @Override
    public EldrinAltarBuilder addItem(ResourceLocation input) {
        super.addItem(input);
        return this;
    }

    @Info("Add an input item / TagKey for the Eldrin Altar recipe using an Item")
    @Override
    public EldrinAltarBuilder addItem(Item input) {
        super.addItem(input);
        return this;
    }

    @Info("Add an input item / TagKey for the Eldrin Altar recipe using an String")
    @Override
    public EldrinAltarBuilder addItem(String input) {
        super.addItem(input);
        return this;
    }

    @Info("Add a pattern for the Eldrin Altar recipe")
    @Override
    public EldrinAltarBuilder addPattern(ResourceLocation pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Add a pattern for the Eldrin Altar recipe using a string")
    @Override
    public EldrinAltarBuilder addPattern(String pattern) {
        super.addPattern(pattern);
        return this;
    }

    @Info("Set the output item for the Eldrin Altar recipe")
    @Override
    public EldrinAltarBuilder output(ResourceLocation output) {
        super.output(output);
        return this;
    }

    @Info("Set the output item for the Eldrin Altar recipe using an Item")
    public EldrinAltarBuilder output(ItemStack output) {
        this.output = ForgeRegistries.ITEMS.getKey(output.getItem());
        if (output.getTag() != null) {
            this.data = output.getTag().getAsString();
        }
        return this;
    }

    @Info("Set the output item for the Eldrin Altar recipe using an Item")
    public EldrinAltarBuilder output(Item output) {
        this.output = ForgeRegistries.ITEMS.getKey(output);
        return this;
    }

    @Info("Set the output item for the Eldrin Altar recipe using a string")
    @Override
    public EldrinAltarBuilder output(String output) {
        super.output(output);
        return this;
    }

    public EldrinAltarBuilder outputData(String data) {
        this.data = data;
        return this;
    }

    @Info("Set the output quantity for the Eldrin Altar recipe")
    @Override
    public EldrinAltarBuilder outputQuantity(int quantity) {
        super.outputQuantity(quantity);
        return this;
    }

    @Info("Set the NBT data for the output item")
    @Override
    public EldrinAltarBuilder outputNBT(JsonObject nbt) {
        super.outputNBT(nbt);
        return this;
    }

    @Info("Add a power requirement for the Eldrin Altar recipe")
    public EldrinAltarBuilder addPowerRequirement(String affinity, float amount) {
        this.powerRequirements.put(affinity, amount);
        return this;
    }

    @Info("Set the primary color for the Eldrin Altar recipe")
    public EldrinAltarBuilder primaryColor(int color) {
        this.colors[0] = color;
        return this;
    }

    @Info("Set the secondary color for the Eldrin Altar recipe")
    public EldrinAltarBuilder secondaryColor(int color) {
        this.colors[1] = color;
        return this;
    }

    @Override
    protected int maxItems() {
        return 9;
    }

    @Override
    protected int maxPatterns() {
        return 0;
    }

    @Override
    protected String getRecipeType() {
        return "mna:eldrin_altar";
    }

    @Info("get the JsonObject for event.custom()")
    @Override
    public JsonObject build() {
        JsonObject json = new JsonObject();
        json.addProperty("type", getRecipeType());

        if (requiredItems.isEmpty()) {
            throw new IllegalStateException("At least one input item must be specified");
        }

        if (output == null) {
            throw new IllegalStateException("Output item cannot be null");
        }

        JsonArray inputs = new JsonArray();
        for (ResourceLocation input : requiredItems) {
            inputs.add(input.toString());
        }
        json.add("inputs", inputs);

        if (outputNBT != null) {
            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", output.toString());
            outputObject.add("data", outputNBT);
            json.add("output", outputObject);
        } else {
            json.addProperty("output", output.toString());
        }

        if (outputQuantity > 1) {
            json.addProperty("count", outputQuantity);
        }

        if (!powerRequirements.isEmpty()) {
            JsonArray powerReqs = new JsonArray();
            for (Map.Entry<String, Float> entry : powerRequirements.entrySet()) {
                JsonObject requirement = new JsonObject();
                requirement.addProperty("affinity", entry.getKey());
                requirement.addProperty("amount", entry.getValue());
                powerReqs.add(requirement);
            }
            json.add("power_requirements", powerReqs);
        }

        if (colors[0] != 0 || colors[1] != 0) {
            JsonArray colorsArray = new JsonArray();
            colorsArray.add(colors[0]);
            colorsArray.add(colors[1]);
            json.add("colors", colorsArray);
        }

        json.addProperty("tier", this.tier);
        json.addProperty("requiredFaction", this.faction.toString());
        return json;
    }
}