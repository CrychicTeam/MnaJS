package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.recipes.schema.Basic.MnaJSRecipeKeys;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface ManaweavingAltarSchema extends TierBaseSchema, ItemBaseSchema {
    RecipeKey<Item[]> INPUTS = MnaJSRecipeKeys.INPUTS;
    RecipeKey<String[]> PATTERNS = StringComponent.ID.asArray().key("patterns");
    RecipeKey<String> ENCHANT = StringComponent.ID.key("enchant").optional("").allowEmpty();
    RecipeKey<Integer> MAGNITUDE = NumberComponent.INT.key("magnitude").optional(1);
    RecipeKey<Boolean> COPY_NBT = BooleanComponent.BOOLEAN.key("copy_nbt").optional(false);

    RecipeSchema SCHEMA = new RecipeSchema(INPUTS, OUTPUT, PATTERNS, QUANTITY, ENCHANT, MAGNITUDE, COPY_NBT);
}
