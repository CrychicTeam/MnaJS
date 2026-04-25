package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.recipe.ManaweavingAltarRecipeJS;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.recipes.schema.base.ItemsPatternSchema;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;

public interface ManaweavingAltarSchema extends ItemsPatternSchema {
    RecipeKey<net.minecraft.world.item.ItemStack> OUTPUT = ItemsPatternSchema.OUTPUT;
    RecipeKey<MnaItemOrTag[]> INPUTS = ItemsPatternSchema.INPUTS;
    RecipeKey<MnaManaweavePatternId[]> PATTERNS = ItemsPatternSchema.PATTERNS;
    RecipeKey<String> ENCHANT = StringComponent.ID.key("enchant").optional(ResourceLocation.fromNamespaceAndPath("mna", "none").toString()).allowEmpty();
    RecipeKey<Integer> MAGNITUDE = NumberComponent.INT.key("magnitude").optional(1);
    RecipeKey<Boolean> COPY_NBT = BooleanComponent.BOOLEAN.key("copy_nbt").optional(false);

    RecipeSchema SCHEMA = new RecipeSchema(ManaweavingAltarRecipeJS.class, ManaweavingAltarRecipeJS::new, OUTPUT, INPUTS, PATTERNS, QUANTITY, ENCHANT, MAGNITUDE, COPY_NBT, TIER, FACTION)
            .constructor()
            .constructor((recipe, schemaType, keys, from) -> {
                recipe.setValue(OUTPUT, from.getValue(recipe, OUTPUT));
                recipe.setValue(INPUTS, from.getValue(recipe, INPUTS));
            }, OUTPUT, INPUTS);
}
