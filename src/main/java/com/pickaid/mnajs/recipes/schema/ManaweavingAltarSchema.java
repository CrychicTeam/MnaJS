package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.mna.LimitedStringsComponent;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;

public interface ManaweavingAltarSchema extends ItemsPatternSchema {
    RecipeKey<String[]> PATTERNS = LimitedStringsComponent.MAX_6_STRINGS.key("patterns");
    RecipeKey<String> ENCHANT = StringComponent.ID.key("enchant").optional(new ResourceLocation("mna", "none").toString()).allowEmpty();
    RecipeKey<Integer> MAGNITUDE = NumberComponent.INT.key("magnitude").optional(1);
    RecipeKey<Boolean> COPY_NBT = BooleanComponent.BOOLEAN.key("copy_nbt").optional(false);

    RecipeSchema SCHEMA = new RecipeSchema(PATTERNS, INPUTS, OUTPUT, QUANTITY, ENCHANT, MAGNITUDE, COPY_NBT, TIER, FACTION);
}