package com.pickaid.mnajs.kubejs.events.server.runforge;

import com.mna.api.events.RunicAnvilShouldActivateEvent;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.item.ItemStack;

public class RunicAnvilShouldActiveEventJS extends EventJS {
    RunicAnvilShouldActivateEvent event;

    public RunicAnvilShouldActiveEventJS(RunicAnvilShouldActivateEvent event) {
        this.event = event;
    }

    public ItemStack getPattern() {
        return event.pattern;
    }

    public ItemStack getMaterial() {
        return event.material;
    }
}
