package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaIds;

public final class ProgressionRecipeJS extends MnaBaseRecipeJS<ProgressionRecipeJS> {
    public ProgressionRecipeJS advancement(String value) {
        return setKey("advancement", MnaIds.parse(value, "advancement", "minecraft", null).toString());
    }

    public ProgressionRecipeJS desc(String value) {
        return setKey("desc", value);
    }

    public ProgressionRecipeJS description(String value) {
        return desc(value);
    }

    @Override
    protected void validateRecipe() {
        Object advancement = currentValue("advancement");
        require(advancement instanceof String string && !string.isBlank(), "Progression recipe advancement must be set");
    }
}
