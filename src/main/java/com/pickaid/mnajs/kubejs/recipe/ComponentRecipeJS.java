package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import dev.latvian.mods.rhino.util.HideFromJS;

public final class ComponentRecipeJS extends MnaItemsPatternRecipeJS<ComponentRecipeJS> {
    public ComponentRecipeJS output(MnaSpellEffectId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public ComponentRecipeJS output(String value) {
        return output(MnaSpellEffectId.parse(value));
    }

    @Override
    protected String recipeLabel() {
        return "Component recipe";
    }
}
