package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.kubejs.events.server.ManaChangedEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MnaJSEvents {
    EventGroup GROUP = EventGroup.of("MnaJSEvents");

    EventHandler MANA_CHANGED = GROUP.server("manaChanged", () -> ManaChangedEventJS.class).hasResult();
}
