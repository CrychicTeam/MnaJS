package com.pickaid.mnajs.kubejs.recipe;

import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

public final class EldrinAltarRecipeJS extends MnaBaseRecipeJS<EldrinAltarRecipeJS> {
    public EldrinAltarRecipeJS output(MnaItemId value) {
        return setKey("output", value.id());
    }

    @HideFromJS
    public EldrinAltarRecipeJS output(String value) {
        return output(MnaItemId.parse(value));
    }

    @HideFromJS
    public EldrinAltarRecipeJS output(Item value) {
        return output(MnaItemId.parse(value));
    }

    public EldrinAltarRecipeJS outputStack(ItemStack value) {
        return setKey("output", value);
    }

    public EldrinAltarRecipeJS inputs(MnaItemOrTag... values) {
        return setKey("items", values);
    }

    @HideFromJS
    public EldrinAltarRecipeJS inputs(String... values) {
        return inputs(Arrays.stream(values)
                .map(MnaItemOrTag::parse)
                .toArray(MnaItemOrTag[]::new));
    }

    public EldrinAltarRecipeJS addInput(MnaItemOrTag value) {
        var inputs = editableList("items");
        inputs.add(value);
        return setKey("items", inputs);
    }

    @HideFromJS
    public EldrinAltarRecipeJS addInput(String value) {
        return addInput(MnaItemOrTag.parse(value));
    }

    @HideFromJS
    public EldrinAltarRecipeJS inputs(Collection<?> values) {
        return setKey("items", values);
    }

    @HideFromJS
    public EldrinAltarRecipeJS powerRequirement(Affinity affinity, Number amount) {
        return powerRequirement(String.valueOf(affinity), amount);
    }

    public EldrinAltarRecipeJS powerRequirement(String affinity, Number amount) {
        var requirements = editableList("power_requirements");
        requirements.add(powerRequirementObject(affinity, amount));
        return setKey("power_requirements", requirements);
    }

    @HideFromJS
    public EldrinAltarRecipeJS addPowerRequirement(Affinity affinity, Number amount) {
        return powerRequirement(affinity, amount);
    }

    public EldrinAltarRecipeJS addPowerRequirement(String affinity, Number amount) {
        return powerRequirement(affinity, amount);
    }

    public EldrinAltarRecipeJS colors(int primary, int secondary) {
        return setKey("colors", new int[]{primary, secondary});
    }

    public EldrinAltarRecipeJS primaryColor(int value) {
        return colors(value, existingColor(1));
    }

    public EldrinAltarRecipeJS secondaryColor(int value) {
        return colors(existingColor(0), value);
    }

    public EldrinAltarRecipeJS count(int value) {
        return setKey("count", value);
    }

    public EldrinAltarRecipeJS quantity(int value) {
        return count(value);
    }

    public EldrinAltarRecipeJS outputQuantity(int value) {
        return count(value);
    }

    @Override
    protected void validateRecipe() {
        ItemStack output = getValue(com.pickaid.mnajs.recipes.schema.EldrinAltarSchema.OUTPUT);
        MnaItemOrTag[] inputs = getValue(com.pickaid.mnajs.recipes.schema.EldrinAltarSchema.INPUTS);
        require(output != null && !output.isEmpty(), "Eldrin altar recipe output must be set");
        require(inputs != null && inputs.length > 0, "Eldrin altar recipe must define at least one input item");
        Integer count = (Integer) currentValue("count");
        require(count == null || count > 0, "Eldrin altar recipe count must be greater than 0");
    }

    private int existingColor(int index) {
        var colors = editableList("colors");
        if (colors.size() > index && colors.get(index) instanceof Number number) {
            return number.intValue();
        }
        return 0;
    }

    private static LinkedHashMap<String, Object> powerRequirementObject(Object affinity, Number amount) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("affinity", affinity);
        result.put("amount", amount.floatValue());
        return result;
    }
}
