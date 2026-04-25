package com.pickaid.mnajs.kubejs;

import com.pickaid.mnajs.kubejs.events.server.WanderingWizardSelectingTradesEventJS;
import com.pickaid.mnajs.kubejs.events.server.player.*;
import com.pickaid.mnajs.kubejs.events.server.runforge.RunicAnvilItemUsedEventJS;
import com.pickaid.mnajs.kubejs.events.server.runforge.RunicAnvilShouldActiveEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.ComponentApplyingEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CooldownCalculatingEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CostManaEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CastEventJS;
import com.pickaid.mnajs.kubejs.events.startup.CantripRegistrationEventJS;
import com.pickaid.mnajs.kubejs.events.startup.GuideBookRegisterEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MnaJSEvents {
    EventGroup GROUP = EventGroup.of("MnaEvent");
    EventGroup PLAYER_GROUP = EventGroup.of("MnaPlayerEvent");
    EventGroup SPELL_GROUP = EventGroup.of("SpellEvent");
    EventGroup RUNE_FORGE_GROUP = EventGroup.of("RuneForgeEvent");

    EventHandler REGISTER_GUIDE_BOOK = GROUP.startup("registerGuideBook", () -> GuideBookRegisterEventJS.class);
    EventHandler REGISTER_CANTRIP = GROUP.startup("registerCantrip", () -> CantripRegistrationEventJS.class);
    EventHandler WIZARD_SELECTING_TRADES = GROUP.server("wanderingWizardSelectingTrade", () -> WanderingWizardSelectingTradesEventJS.class);

    EventHandler RITUAL_COMPLETED = PLAYER_GROUP.server("ritualCompleteEvent", () -> RitualCompleteEventJS.class);
    EventHandler AFFINITY_CHANGED = PLAYER_GROUP.server("affinityChangedEvent", () -> AffinityChangedEventJS.class).hasResult();
    EventHandler GENERIC_PROGRESSION = PLAYER_GROUP.server("genericProgression", () -> GenericProgressionEventJS.class);
    EventHandler MAGIC_LEVEL_UP = PLAYER_GROUP.server("levelUp", () -> MagicLevelUpEventJS.class);
    EventHandler MAGIC_XP_GAINED = PLAYER_GROUP.server("magicXPGained", () -> MagicXPGainedEventJS.class).hasResult();
    EventHandler MASTERY_GAINED_EVENT = PLAYER_GROUP.server("masteryGained", () -> MasteryGainedEventJS.class).hasResult();
    EventHandler ROTE_PROGRESSION = PLAYER_GROUP.server("roteProgression", () -> RoteProgressionEventJS.class).hasResult();

    EventHandler MANA_CALCULATE = SPELL_GROUP.server("costingMana", () -> CostManaEventJS.class);
    EventHandler SPELL_CAST = SPELL_GROUP.server("casted", () -> CastEventJS.class);
    EventHandler SPELL_CALCULATING_COOLDOWN = SPELL_GROUP.server("calculatingCooldown", () -> CooldownCalculatingEventJS.class);
    EventHandler COMPONENT_APPLYING = SPELL_GROUP.server("componentApplying", () -> ComponentApplyingEventJS.class).hasResult();


    EventHandler ANVIL_SHOULD_ACTIVE = RUNE_FORGE_GROUP.server("shouldActivate", () -> RunicAnvilShouldActiveEventJS.class).hasResult();
    EventHandler ANVIL_ITEM_USED = RUNE_FORGE_GROUP.server("itemUsed", () -> RunicAnvilItemUsedEventJS.class).hasResult();
}
