package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.component.ItemOrTagComponent;
import com.pickaid.mnajs.recipes.schema.base.TierBaseSchema;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TinyMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Ritual Recipe Schema for Mana and Artifice
 *
 * @author M1hono
 */
public interface RitualRecipeSchema extends TierBaseSchema {
    RecipeComponent<TinyMap<Character, RecipeComponentBuilderMap>> RITUAL_PATTERN_KEY = new RecipeComponentBuilder(6)
            .add(ItemOrTagComponent.ITEM_OR_TAG_COMPONENT.key("item"))
            .add(BooleanComponent.BOOLEAN.key("optional").optional(false))
            .add(BooleanComponent.BOOLEAN.key("manualReturn").optional(false))
            .add(BooleanComponent.BOOLEAN.key("isDynamic").optional(false))
            .add(BooleanComponent.BOOLEAN.key("dynamicSource").optional(false))
            .add(BooleanComponent.BOOLEAN.key("consume").optional(true)).asPatternKey();
    RecipeKey<Integer[][]> PATTERN = NumberComponent.ANY_INT.asArray().asArray().key("pattern");
    RecipeKey<String[]> REAGENTS = StringComponent.ANY.asArray().key("reagents");
    RecipeKey<TinyMap<Character, RecipeComponentBuilderMap>> KEYS = RITUAL_PATTERN_KEY.key("keys");
    RecipeKey<Integer[][]> DISPLAY_PATTERN = NumberComponent.ANY_INT.asArray().asArray().key("displayPattern").optional((Integer[][]) null);
    RecipeKey<String[]> MANAWEAVE = StringComponent.NON_BLANK.asArray().key("manaweave").optional((String[]) null);
    RecipeKey<String> INNER_COLOR = StringComponent.NON_BLANK.key("innerColor").defaultOptional().allowEmpty();
    RecipeKey<String> OUTER_COLOR = StringComponent.NON_BLANK.key("outerColor").defaultOptional().allowEmpty();
    RecipeKey<String> BEAM_COLOR = StringComponent.NON_BLANK.key("beamColor").defaultOptional().allowEmpty();
    RecipeKey<Boolean> CONNECT_BEAM = BooleanComponent.BOOLEAN.key("connectBeam").optional(true);
    RecipeKey<Boolean> DISPLAY_INDEXES = BooleanComponent.BOOLEAN.key("displayIndexes").optional(true);
    RecipeKey<Boolean> KITTABLE = BooleanComponent.BOOLEAN.key("kittable").optional(true);
    RecipeKey<Item> CREATES_ITEM = ItemComponent.ITEM.key("createsItem").optional(ItemStack.EMPTY.getItem());
    RecipeKey<String> COMMAND = StringComponent.ANY.key("command").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            PATTERN, REAGENTS, KEYS, DISPLAY_PATTERN, TIER, FACTION, CREATES_ITEM, MANAWEAVE,
            INNER_COLOR, OUTER_COLOR, BEAM_COLOR, CONNECT_BEAM,
            DISPLAY_INDEXES, KITTABLE, COMMAND
    );
}