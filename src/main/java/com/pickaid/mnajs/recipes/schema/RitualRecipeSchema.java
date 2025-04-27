package com.pickaid.mnajs.recipes.schema;

import com.pickaid.mnajs.recipes.component.IRitualKeyComponent;
import com.pickaid.mnajs.recipes.component.RitualKey;
import com.pickaid.mnajs.recipes.schema.Basic.TierBaseSchema;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TinyMap;

/**
 * Ritual Recipe Schema for Mana and Artifice
 *
 * @author M1hono
 */
public interface RitualRecipeSchema extends TierBaseSchema {
    RecipeKey<Integer[][]> PATTERN = NumberComponent.ANY_INT.asArray().asArray().key("pattern");
    RecipeKey<String[]> REAGENTS = StringComponent.ANY.asArray().key("reagents");
    RecipeKey<TinyMap<Character, RitualKey>> KEYS = IRitualKeyComponent.RITUAL_PATTERN_KEY.key("keys");
    RecipeKey<Integer[][]> DISPLAY_PATTERN = NumberComponent.ANY_INT.asArray().asArray().key("displayPattern").optional((Integer[][]) null);
    RecipeKey<String[]> MANAWEAVE = StringComponent.NON_BLANK.asArray().key("manaweave").optional((String[]) null);
    RecipeKey<Long> INNER_COLOR = NumberComponent.LONG.key("innerColor").optional(16777215L);
    RecipeKey<Long> OUTER_COLOR = NumberComponent.LONG.key("outerColor").optional(65280L);
    RecipeKey<Long> BEAM_COLOR = NumberComponent.LONG.key("beamColor").optional(16777215L);
    RecipeKey<Boolean> CONNECT_BEAM = BooleanComponent.BOOLEAN.key("connectBeam").optional(true);
    RecipeKey<Boolean> DISPLAY_INDEXES = BooleanComponent.BOOLEAN.key("displayIndexes").optional(true);
    RecipeKey<Boolean> KITTABLE = BooleanComponent.BOOLEAN.key("kittable").optional(true);
    RecipeKey<OutputItem> CREATES_ITEM = ItemComponents.OUTPUT.key("createsItem").optional(OutputItem.EMPTY);
    RecipeKey<String> COMMAND = StringComponent.ANY.key("command").optional((String) null);

    RecipeSchema SCHEMA = new RecipeSchema(
            PATTERN, REAGENTS, IRitualKeyComponent.RITUAL_PATTERN_KEY.key("keys"), DISPLAY_PATTERN, MANAWEAVE,
            INNER_COLOR, OUTER_COLOR, BEAM_COLOR, CONNECT_BEAM,
            DISPLAY_INDEXES, KITTABLE, CREATES_ITEM, COMMAND, TIER, FACTION
    );
}