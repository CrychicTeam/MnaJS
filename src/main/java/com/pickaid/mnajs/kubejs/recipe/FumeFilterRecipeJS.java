package com.pickaid.mnajs.kubejs.recipe;

import com.mna.api.affinity.Affinity;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.recipes.schema.FumerFliterSchema;
import dev.latvian.mods.rhino.util.HideFromJS;

import java.util.LinkedHashMap;

public final class FumeFilterRecipeJS extends MnaBaseRecipeJS<FumeFilterRecipeJS> {
    public FumeFilterRecipeJS item(MnaItemOrTag value) {
        return setKey("item", value);
    }

    @HideFromJS
    public FumeFilterRecipeJS item(String value) {
        return item(MnaItemOrTag.parse(value));
    }

    public FumeFilterRecipeJS input(MnaItemOrTag value) {
        return item(value);
    }

    @HideFromJS
    public FumeFilterRecipeJS input(String value) {
        return item(value);
    }

    public FumeFilterRecipeJS powerProvided(String affinity, Number amount) {
        return setKey("power_provided", powerProvidedObject(affinity, amount));
    }

    public FumeFilterRecipeJS affinity(String value) {
        LinkedHashMap<String, Object> powerProvided = editableObject("power_provided");
        powerProvided.put("affinity", value);
        return setKey("power_provided", powerProvided);
    }

    public FumeFilterRecipeJS powerAmount(Number value) {
        LinkedHashMap<String, Object> powerProvided = editableObject("power_provided");
        powerProvided.put("amount", value.floatValue());
        return setKey("power_provided", powerProvided);
    }

    @HideFromJS
    public FumeFilterRecipeJS powerProvided(Affinity affinity, Number amount) {
        return powerProvided(String.valueOf(affinity), amount);
    }

    @HideFromJS
    public FumeFilterRecipeJS affinity(Affinity value) {
        return affinity(String.valueOf(value));
    }

    @Override
    protected void validateRecipe() {
        MnaItemOrTag item = getValue(FumerFliterSchema.ITEM_OR_TAG_ID);
        require(item != null, "Eldrin fume recipe item must be set");
        LinkedHashMap<String, Object> powerProvided = editableObject("power_provided");
        Object affinity = powerProvided.get("affinity");
        Object amount = powerProvided.get("amount");
        require(affinity != null && !String.valueOf(affinity).isBlank(), "Eldrin fume recipe affinity must be set");
        require(amount instanceof Number number && number.floatValue() > 0F, "Eldrin fume recipe amount must be greater than 0");
    }

    private static LinkedHashMap<String, Object> powerProvidedObject(Object affinity, Number amount) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("affinity", affinity);
        map.put("amount", amount.floatValue());
        return map;
    }
}
