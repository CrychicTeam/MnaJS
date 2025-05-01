package com.pickaid.mnajs.kubejs.events.startup;

import com.mna.api.guidebook.IGuideBookRegistry;
import com.mna.api.guidebook.RegisterGuidebooksEvent;
import dev.latvian.mods.kubejs.event.StartupEventJS;

public class GuideBookRegisterEventJS extends StartupEventJS {
    RegisterGuidebooksEvent event;

    public GuideBookRegisterEventJS (RegisterGuidebooksEvent event) {
        this.event = event;
    }

    public IGuideBookRegistry getRegistry() {
        return this.event.getRegistry();
    }
}
