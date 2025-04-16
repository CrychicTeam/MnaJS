package com.crychicteam.mnajs.handlers.server;

import com.crychicteam.mnajs.MnaJS;
import com.crychicteam.mnajs.kubejs.MnaJSEvents;
import com.crychicteam.mnajs.kubejs.events.server.ManaChangedEventJS;
import com.mna.api.events.AffinityChangedEvent;
import com.mna.api.events.CalculatingManaCostEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MnaJS.ID)
public class Mana {
    public static void onManaCalculate(CalculatingManaCostEvent event) {
        if (event.getCaster() instanceof  Player) {
            var manaChangedEvent = MnaJSEvents.MANA_CHANGED.post(new ManaChangedEventJS((Player) event.getCaster(), event.getManaCost()));
        }
    }
}
