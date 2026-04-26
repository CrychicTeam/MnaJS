package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;

public final class ProgressionRecipeJS extends MnaBaseRecipeJS<ProgressionRecipeJS> {
    @Info(value = "Set the advancement required by this progression recipe.", params = {
            @Param(name = "value", value = "Advancement id such as mna:tier_1/cast_a_spell.")
    })
    public ProgressionRecipeJS advancement(MnaAdvancementId value) {
        return setKey("advancement", value);
    }

    @Info(value = "Set the short description shown for this progression requirement.", params = {
            @Param(name = "value", value = "Human-readable text shown to the player.")
    })
    public ProgressionRecipeJS desc(String value) {
        return setKey("desc", value);
    }

    @Info(value = "Alias for desc(...).", params = {
            @Param(name = "value", value = "Human-readable text shown to the player.")
    })
    public ProgressionRecipeJS description(String value) {
        return desc(value);
    }

    @Override
    protected void validateRecipe() {
        MnaAdvancementId advancement = getValue(com.pickaid.mnajs.recipes.schema.ProgressionSchema.ADVANCEMENT);
        require(advancement != null, "Progression recipe advancement must be set");
    }
}
