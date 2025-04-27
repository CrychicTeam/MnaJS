package com.pickaid.mnajs.kubejs;

import com.pickaid.mnajs.kubejs.events.server.AffinityChangedEventJS;
import com.pickaid.mnajs.kubejs.events.server.RitualCompleteEventJS;
import com.pickaid.mnajs.kubejs.events.server.SellCostManaEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MnaJSEvents {
    EventGroup GROUP = EventGroup.of("MnaJSEvents");

    EventHandler MANA_CHANGED = GROUP.server("spellCostingMana", () -> SellCostManaEventJS.class).hasResult();
    EventHandler RITUAL_COMPLETED = GROUP.server("spellCostingMana", () -> RitualCompleteEventJS.class);
    EventHandler AFFINITY_CHANGED = GROUP.server("spellCostingMana", () -> AffinityChangedEventJS.class).hasResult();
}
