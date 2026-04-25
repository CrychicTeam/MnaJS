package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.recipe.FumeFilterRecipeJS;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FumerFliterSchema extends TierBaseSchema {
    RecipeKey<MnaItemOrTag> ITEM_OR_TAG_ID = MnaRecipeComponents.ITEM_OR_TAG.key("item");
    RecipeKey<RecipeComponentBuilderMap> POWER_PROVIDED = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.outputRole().key("power_provided");
    RecipeKey<Float> AMOUNT = NumberComponent.FLOAT.key("amount");
    RecipeSchema SCHEMA = new RecipeSchema(FumeFilterRecipeJS.class, FumeFilterRecipeJS::new, ITEM_OR_TAG_ID, POWER_PROVIDED, TIER, FACTION)
            .constructor()
            .constructor((recipe, schemaType, keys, from) -> {
                recipe.setValue(ITEM_OR_TAG_ID, from.getValue(recipe, ITEM_OR_TAG_ID));
                recipe.set("power_provided", java.util.Map.of(
                        "affinity", from.getValue(recipe, PowerProvidedComponent.AFFINITY),
                        "amount", from.getValue(recipe, AMOUNT)
                ));
            }, ITEM_OR_TAG_ID, PowerProvidedComponent.AFFINITY, AMOUNT);
}
