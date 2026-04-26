package com.pickaid.mnajs.recipes.component.mna;

import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSoundId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaStructureId;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;

public interface MnaRecipeComponents {
    RecipeComponent<MnaAdvancementId> ADVANCEMENT_ID = new MnaTypedIdRecipeComponent<>(MnaAdvancementId.class, MnaAdvancementId::parse);
    RecipeComponent<MnaItemId> ITEM_ID = new MnaTypedIdRecipeComponent<>(MnaItemId.class, MnaItemId::parse);
    RecipeComponent<MnaBlockId> BLOCK_ID = new MnaTypedIdRecipeComponent<>(MnaBlockId.class, MnaBlockId::parse);
    RecipeComponent<MnaItemOrTag> ITEM_OR_TAG = new MnaItemOrTagRecipeComponent();
    RecipeComponent<MnaLootTableId> LOOT_TABLE_ID = new MnaTypedIdRecipeComponent<>(MnaLootTableId.class, MnaLootTableId::parse);
    RecipeComponent<MnaFactionId> FACTION_ID = new MnaTypedIdRecipeComponent<>(MnaFactionId.class, MnaFactionId::parse);
    RecipeComponent<MnaCastingResourceId> CASTING_RESOURCE_ID = new MnaTypedIdRecipeComponent<>(MnaCastingResourceId.class, MnaCastingResourceId::parse);
    RecipeComponent<MnaMobEffectId> MOB_EFFECT_ID = new MnaTypedIdRecipeComponent<>(MnaMobEffectId.class, MnaMobEffectId::parse);
    RecipeComponent<MnaRitualEffectId> RITUAL_EFFECT_ID = new MnaTypedIdRecipeComponent<>(MnaRitualEffectId.class, MnaRitualEffectId::parse);
    RecipeComponent<MnaSpellEffectId> SPELL_EFFECT_ID = new MnaTypedIdRecipeComponent<>(MnaSpellEffectId.class, MnaSpellEffectId::parse);
    RecipeComponent<MnaShapeId> SHAPE_ID = new MnaTypedIdRecipeComponent<>(MnaShapeId.class, MnaShapeId::parse);
    RecipeComponent<MnaModifierId> MODIFIER_ID = new MnaTypedIdRecipeComponent<>(MnaModifierId.class, MnaModifierId::parse);
    RecipeComponent<MnaRitualId> RITUAL_ID = new MnaTypedIdRecipeComponent<>(MnaRitualId.class, MnaRitualId::parse);
    RecipeComponent<MnaManaweavePatternId> MANAWEAVE_PATTERN_ID = new MnaTypedIdRecipeComponent<>(MnaManaweavePatternId.class, MnaManaweavePatternId::parse);
    RecipeComponent<MnaCantripId> CANTRIP_ID = new MnaTypedIdRecipeComponent<>(MnaCantripId.class, MnaCantripId::parse);
    RecipeComponent<MnaSoundId> SOUND_ID = new MnaTypedIdRecipeComponent<>(MnaSoundId.class, MnaSoundId::parse);
    RecipeComponent<MnaStructureId> STRUCTURE_ID = new MnaTypedIdRecipeComponent<>(MnaStructureId.class, MnaStructureId::parse);
}
