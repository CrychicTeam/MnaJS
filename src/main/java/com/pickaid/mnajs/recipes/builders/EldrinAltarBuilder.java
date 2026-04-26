package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.recipes.builders.base.ItemAndPatternBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class EldrinAltarBuilder extends ItemAndPatternBuilder {
    private final Map<String, Float> powerRequirements = new LinkedHashMap<>();
    private final int[] colors = new int[]{0, 0};

    @Info("Add an input item or tag for the Eldrin Altar recipe")
    @Override
    public EldrinAltarBuilder addItem(MnaItemOrTag input) {
        super.addItem(input);
        return this;
    }

    @HideFromJS
    @Override
    public EldrinAltarBuilder addItem(Item input) {
        super.addItem(input);
        return this;
    }

    @HideFromJS
    @Override
    public EldrinAltarBuilder addItem(ResourceLocation input) {
        super.addItem(input);
        return this;
    }

    @HideFromJS
    @Override
    public EldrinAltarBuilder addItem(String input) {
        super.addItem(input);
        return this;
    }

    @Info("Set the output item for the Eldrin Altar recipe")
    public EldrinAltarBuilder output(MnaItemId output) {
        this.output = output.location();
        return this;
    }

    @HideFromJS
    @Override
    public EldrinAltarBuilder output(ResourceLocation output) {
        this.output = output;
        return this;
    }

    @HideFromJS
    public EldrinAltarBuilder output(Item output) {
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    public EldrinAltarBuilder output(ItemStack output) {
        if (output.getTag() != null && !output.getTag().isEmpty()) {
            throw new IllegalArgumentException("Tagged Eldrin altar outputs must use outputNBT(...)");
        }
        return output(MnaItemId.parse(output));
    }

    @HideFromJS
    @Override
    public EldrinAltarBuilder output(String output) {
        return output(MnaItemId.parse(output));
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

    @HideFromJS
    public EldrinAltarBuilder addPowerRequirement(Affinity affinity, float amount) {
        return addPowerRequirement(String.valueOf(affinity), amount);
    }

    public EldrinAltarBuilder powerRequirement(String affinity, float amount) {
        return addPowerRequirement(affinity, amount);
    }

    @HideFromJS
    public EldrinAltarBuilder powerRequirement(Affinity affinity, float amount) {
        return addPowerRequirement(affinity, amount);
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

    public EldrinAltarBuilder colors(int primary, int secondary) {
        this.colors[0] = primary;
        this.colors[1] = secondary;
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
        return "mna:eldrin-altar";
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
        for (MnaItemOrTag input : requiredItems) {
            inputs.add(input.recipeValue());
        }
        json.add("items", inputs);

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
