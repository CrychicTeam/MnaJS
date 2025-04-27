package com.pickaid.mnajs.recipes.schema.Basic;

import com.pickaid.mnajs.recipes.component.InputItemsComponent;
import com.pickaid.mnajs.recipes.component.PowerProvided;
import com.pickaid.mnajs.recipes.component.PowerProvidedComponent;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import net.minecraft.world.item.Item;

public interface MnaJSRecipeKeys extends TierBaseSchema{
    RecipeKey<Item[]> INPUTS = InputItemsComponent.INPUT_ITEMS.key("items");
    RecipeKey<PowerProvided> POWER_PROVIDED_KEY = PowerProvidedComponent.POWER_PROVIDED_COMPONENT.key("power_provided");
}
