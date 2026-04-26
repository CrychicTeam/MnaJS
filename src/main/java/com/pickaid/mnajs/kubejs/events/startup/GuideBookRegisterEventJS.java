package com.pickaid.mnajs.kubejs.events.startup;

import com.mna.api.guidebook.IGuideBookRegistry;
import com.mna.api.guidebook.RegisterGuidebooksEvent;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Info;

public class GuideBookRegisterEventJS extends StartupEventJS {
    RegisterGuidebooksEvent event;

    public GuideBookRegisterEventJS (RegisterGuidebooksEvent event) {
        this.event = event;
    }

    @Info("Access the underlying Mana and Artifice guidebook registry for advanced guidebook registration.")
    public IGuideBookRegistry getRegistry() {
        return this.event.getRegistry();
    }
}
