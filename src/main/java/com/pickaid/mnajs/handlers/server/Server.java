package com.pickaid.mnajs.handlers.server;

import com.mna.api.events.AffinityChangedEvent;
import com.mna.api.events.CalculatingManaCostEvent;
import com.mna.api.events.RitualCompleteEvent;
import com.pickaid.mnajs.MnaJS;
import com.pickaid.mnajs.kubejs.MnaJSEvents;
import com.pickaid.mnajs.kubejs.events.server.AffinityChangedEventJS;
import com.pickaid.mnajs.kubejs.events.server.RitualCompleteEventJS;
import com.pickaid.mnajs.kubejs.events.server.SellCostManaEventJS;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MnaJS.ID)
public class Server {
    @SubscribeEvent
    public static void onManaCalculate(CalculatingManaCostEvent event) {
        if (event.getCaster() instanceof  Player) {
            MnaJSEvents.MANA_CHANGED.post(new SellCostManaEventJS(event));
        }
    }

    @SubscribeEvent
    public static void onRitualComplete(RitualCompleteEvent event) {
        if (event.getCaster() instanceof Player) {
            MnaJSEvents.RITUAL_COMPLETED.post(new RitualCompleteEventJS(event));
        }
    }

    @SubscribeEvent
    public static void onAffinityChangedEvent(AffinityChangedEvent event) {
        var affinityChangedEvent = MnaJSEvents.AFFINITY_CHANGED.post(new AffinityChangedEventJS(event));
        if (affinityChangedEvent.interruptFalse()) {
            event.setCanceled(true);
        }
    }
}
