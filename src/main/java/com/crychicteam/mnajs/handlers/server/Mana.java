package com.crychicteam.mnajs.handlers.server;

import com.crychicteam.mnajs.MnaJS;
import com.mna.api.events.AffinityChangedEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MnaJS.ID)
public class Mana {
    public static void onManaCalculate(AffinityChangedEvent event) {

    }
}
