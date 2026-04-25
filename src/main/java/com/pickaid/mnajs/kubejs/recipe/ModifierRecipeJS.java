package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import dev.latvian.mods.rhino.util.HideFromJS;

public final class ModifierRecipeJS extends MnaItemsPatternRecipeJS<ModifierRecipeJS> {
    public ModifierRecipeJS output(MnaModifierId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public ModifierRecipeJS output(String value) {
        return output(MnaModifierId.parse(value));
    }

    @Override
    protected String recipeLabel() {
        return "Modifier recipe";
    }
}
