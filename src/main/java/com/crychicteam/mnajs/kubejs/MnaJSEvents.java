package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.kubejs.events.server.SellCostManaEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MnaJSEvents {
    EventGroup GROUP = EventGroup.of("MnaJSEvents");

    EventHandler MANA_CHANGED = GROUP.server("spellCostingMana", () -> SellCostManaEventJS.class).hasResult();
}
