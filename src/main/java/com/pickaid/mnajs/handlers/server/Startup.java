package com.pickaid.mnajs.handlers.server;

import com.mna.api.guidebook.RegisterGuidebooksEvent;
import com.pickaid.mnajs.MnaJS;
import com.pickaid.mnajs.kubejs.MnaJSEvents;
import com.pickaid.mnajs.kubejs.events.startup.CantripRegistrationEventJS;
import com.pickaid.mnajs.kubejs.events.startup.GuideBookRegisterEventJS;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MnaJS.ID)
public class Startup {
    @SubscribeEvent
    public static void onGuideBookRegister(RegisterGuidebooksEvent event) {
        MnaJSEvents.REGISTER_GUIDE_BOOK.post(new GuideBookRegisterEventJS(event));
    }

    @SubscribeEvent
    public static void onServerStart(ServerStartedEvent event) {
        MnaJS.LOGGER.info("MnaJS - Server started, firing MnaJS events");
        try {
            MnaJSEvents.REGISTER_CANTRIP.post(new CantripRegistrationEventJS());
            MnaJS.LOGGER.info("MnaJS - Cantrip registration event fired");
        } catch (Exception e) {
            MnaJS.LOGGER.error("MnaJS - Error firing events", e);
        }

    }
}
