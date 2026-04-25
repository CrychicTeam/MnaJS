package com.pickaid.mnajs.recipes.component.mna;

import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

public interface MnaRecipeComponents {
    RecipeComponent<MnaItemId> ITEM_ID = new MnaTypedIdRecipeComponent<>(MnaItemId.class, MnaItemId::parse);
    RecipeComponent<MnaBlockId> BLOCK_ID = new MnaTypedIdRecipeComponent<>(MnaBlockId.class, MnaBlockId::parse);
    RecipeComponent<MnaItemOrTag> ITEM_OR_TAG = new MnaItemOrTagRecipeComponent();
    RecipeComponent<MnaLootTableId> LOOT_TABLE_ID = new MnaTypedIdRecipeComponent<>(MnaLootTableId.class, MnaLootTableId::parse);
    RecipeComponent<MnaFactionId> FACTION_ID = new MnaTypedIdRecipeComponent<>(MnaFactionId.class, MnaFactionId::parse);
    RecipeComponent<MnaRitualEffectId> RITUAL_EFFECT_ID = new MnaTypedIdRecipeComponent<>(MnaRitualEffectId.class, MnaRitualEffectId::parse);
    RecipeComponent<MnaSpellEffectId> SPELL_EFFECT_ID = new MnaTypedIdRecipeComponent<>(MnaSpellEffectId.class, MnaSpellEffectId::parse);
    RecipeComponent<MnaShapeId> SHAPE_ID = new MnaTypedIdRecipeComponent<>(MnaShapeId.class, MnaShapeId::parse);
    RecipeComponent<MnaModifierId> MODIFIER_ID = new MnaTypedIdRecipeComponent<>(MnaModifierId.class, MnaModifierId::parse);
    RecipeComponent<MnaRitualId> RITUAL_ID = new MnaTypedIdRecipeComponent<>(MnaRitualId.class, MnaRitualId::parse);
    RecipeComponent<MnaManaweavePatternId> MANAWEAVE_PATTERN_ID = new MnaTypedIdRecipeComponent<>(MnaManaweavePatternId.class, MnaManaweavePatternId::parse);
    RecipeComponent<MnaCantripId> CANTRIP_ID = new MnaTypedIdRecipeComponent<>(MnaCantripId.class, MnaCantripId::parse);
}
