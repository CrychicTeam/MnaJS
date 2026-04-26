package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.Arrays;
import java.util.Collection;

public abstract class MnaItemsPatternRecipeJS<T extends MnaItemsPatternRecipeJS<T>> extends MnaBaseRecipeJS<T> {
    public T inputs(MnaItemOrTag... values) {
        return setKey("items", values);
    }

    @HideFromJS
    public T inputs(String... values) {
        return inputs(Arrays.stream(values)
                .map(MnaItemOrTag::parse)
                .toArray(MnaItemOrTag[]::new));
    }

    public T input(MnaItemOrTag value) {
        return addInput(value);
    }

    @HideFromJS
    public T input(String value) {
        return addInput(MnaItemOrTag.parse(value));
    }

    public T addInput(MnaItemOrTag value) {
        var inputs = editableList("items");
        inputs.add(value);
        return setKey("items", inputs);
    }

    @HideFromJS
    public T addInput(String value) {
        return addInput(MnaItemOrTag.parse(value));
    }

    public T patterns(MnaManaweavePatternId... values) {
        return setKey("patterns", values);
    }

    @HideFromJS
    public T patterns(String... values) {
        return patterns(Arrays.stream(values)
                .map(MnaManaweavePatternId::parse)
                .toArray(MnaManaweavePatternId[]::new));
    }

    public T pattern(MnaManaweavePatternId value) {
        return addPattern(value);
    }

    @HideFromJS
    public T pattern(String value) {
        return addPattern(value);
    }

    public T addPattern(MnaManaweavePatternId value) {
        var patterns = editableList("patterns");
        patterns.add(value.id());
        return setKey("patterns", patterns);
    }

    @HideFromJS
    public T addPattern(String value) {
        return addPattern(MnaManaweavePatternId.parse(value));
    }

    @HideFromJS
    public T inputs(Collection<?> values) {
        return setKey("items", values);
    }

    public T outputQuantity(int value) {
        return setKey("quantity", value);
    }

    public T quantity(int value) {
        return outputQuantity(value);
    }

    @Override
    protected void validateRecipe() {
        require(currentValue("output") != null, recipeLabel() + " must define an output");
        require(!editableList("items").isEmpty(), recipeLabel() + " must define at least one input item");

        Integer quantity = (Integer) currentValue("quantity");
        require(quantity == null || quantity > 0, recipeLabel() + " quantity must be greater than 0");
    }

    protected abstract String recipeLabel();
}
