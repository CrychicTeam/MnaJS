package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import dev.latvian.mods.rhino.util.HideFromJS;

public final class ShapeRecipeJS extends MnaItemsPatternRecipeJS<ShapeRecipeJS> {
    public ShapeRecipeJS output(MnaShapeId value) {
        return setKey("output", value);
    }

    @HideFromJS
    public ShapeRecipeJS output(String value) {
        return output(MnaShapeId.parse(value));
    }

    @Override
    protected String recipeLabel() {
        return "Shape recipe";
    }
}
