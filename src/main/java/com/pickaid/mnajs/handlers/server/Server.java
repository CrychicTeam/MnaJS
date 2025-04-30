package com.pickaid.mnajs.handlers.server;

import com.mna.api.events.*;
import com.pickaid.mnajs.MnaJS;
import com.pickaid.mnajs.kubejs.MnaJSEvents;
import com.pickaid.mnajs.kubejs.events.server.WanderingWizardSelectingTradesEventJS;
import com.pickaid.mnajs.kubejs.events.server.player.*;
import com.pickaid.mnajs.kubejs.events.server.runforge.RunicAnvilItemUsedEventJS;
import com.pickaid.mnajs.kubejs.events.server.runforge.RunicAnvilShouldActiveEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.ComponentApplyingEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CooldownCalculatingEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CostManaEventJS;
import com.pickaid.mnajs.kubejs.events.server.spell.CastEventJS;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MnaJS.ID)
public class Server {
    @SubscribeEvent
    public static void onManaCalculate(CalculatingManaCostEvent event) {
        if (event.getCaster() instanceof  Player) {
            MnaJSEvents.MANA_CALCULATE.post(new CostManaEventJS(event));
        }
    }

    @SubscribeEvent
    public static void onRitualComplete(RitualCompleteEvent event) {
        if (event.getCaster() instanceof Player) {
            MnaJSEvents.RITUAL_COMPLETED.post(new RitualCompleteEventJS(event));
        }
    }
    @SubscribeEvent
    public static void onProgression(GenericProgressionEvent event) {
        MnaJSEvents.GENERIC_PROGRESSION.post(new GenericProgressionEventJS(event));
    }
    @SubscribeEvent
    public static void onMagicLevelUp(PlayerMagicLevelUpEvent event) {
        MnaJSEvents.MAGIC_LEVEL_UP.post(new MagicLevelUpEventJS(event));
    }

    @SubscribeEvent
    public static void onMagicXPGained(MagicXPGainedEvent event) {
        var magicXPGainedEvent = MnaJSEvents.MAGIC_XP_GAINED.post(new MagicXPGainedEventJS(event));
        if (magicXPGainedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMasteryGained(MasteryGainedEvent event) {
        var masteryGainedEvent = MnaJSEvents.MASTERY_GAINED_EVENT.post(new MasteryGainedEventJS(event));
        if (masteryGainedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRoteProgression(RoteProgressGainedEvent event) {
        var roteProgressionGainedEvent = MnaJSEvents.ROTE_PROGRESSION.post(new RoteProgressionEventJS(event));
        if (roteProgressionGainedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAffinityChangedEvent(AffinityChangedEvent event) {
        var affinityChangedEvent = MnaJSEvents.AFFINITY_CHANGED.post(new AffinityChangedEventJS(event));
        if (affinityChangedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onSpellCastEvent(SpellCastEvent event) {
        MnaJSEvents.SPELL_CAST.post(new CastEventJS(event));
    }

    @SubscribeEvent
    public static void onSellCalculatingCooldown(SpellCooldownCalculatingEvent event) {
        MnaJSEvents.SPELL_CALCULATING_COOLDOWN.post(new CooldownCalculatingEventJS(event));
    }

    @SubscribeEvent
    public static void onComponentApplying(ComponentApplyingEvent event) {
        var componentApplyingEvent = MnaJSEvents.COMPONENT_APPLYING.post(new ComponentApplyingEventJS(event));
        if (componentApplyingEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAnvilItemUsed(RunicAnvilItemUsedEvent event) {
        var itemUsedEvent = MnaJSEvents.ANVIL_ITEM_USED.post(new RunicAnvilItemUsedEventJS(event));
        if (itemUsedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAnvilShouldActivate(RunicAnvilShouldActivateEvent event) {
        var shouldActivateEvent = MnaJSEvents.ANVIL_SHOULD_ACTIVE.post(new RunicAnvilShouldActiveEventJS(event));
        if (shouldActivateEvent.interruptTrue()) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onWanderingWizardTrades(WanderingWizardSelectingTradesEvent event) {
        MnaJSEvents.WIZARD_SELECTING_TRADES.post(new WanderingWizardSelectingTradesEventJS(event));
    }
}
